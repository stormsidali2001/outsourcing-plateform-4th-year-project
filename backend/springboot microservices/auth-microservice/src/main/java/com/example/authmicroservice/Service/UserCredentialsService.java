package com.example.authmicroservice.Service;

import com.example.authmicroservice.Entity.EmailDetails;
import com.example.authmicroservice.Entity.EmailToken;
import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.Repository.EmailTokenRepository;
import com.example.authmicroservice.Repository.UserCredentialsRepository;
import com.example.authmicroservice.config.EmailServiceGeeks;
import com.example.authmicroservice.dto.*;
import com.example.authmicroservice.exception.BadRequestException;
import com.example.authmicroservice.types.Role;
import com.example.authmicroservice.types.UserStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.SecureRandom;
import java.util.*;


@Service
public class UserCredentialsService {
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private EmailService emailService;

    @Autowired
    private EmailTokenRepository emailTokenRepository;


  @Autowired
  private JwtService jwtService;
    @Autowired
    private EmailServiceGeeks emailServiceGeeks;

    @Autowired
    KafkaTemplate<Object,Object> kafkaTemplate;

    public String save(RegisterUserDto userData, Role role) throws BadRequestException {
        System.out.println("PasswordEncoder: " + passwordEncoder.getClass().getName());
        Optional<UserCredentials> userOp = userCredentialsRepository.findByEmail(userData.getEmail());
        if(userOp.isPresent()) throw new BadRequestException("Email already exist");
       return  userCredentialsRepository.save(
                UserCredentials.builder()
                        .email(userData.getEmail())
                        .password(passwordEncoder.encode(userData.getPassword()))
                        .status(UserStatus.ACTIVE)
                        .role(role)
                        .build()
        ).getId();
    }

    public String generateToken(String email){
        Optional<UserCredentials> userOp = this.userCredentialsRepository.findByEmail(email);
        if(userOp.isEmpty()) throw new RuntimeException("not found");
        UserCredentials user = userOp.get();
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",user.getRole());
        claims.put("status",user.getStatus());
        return jwtService.generateToken(user.getEmail(),claims);
    }
    public String generateToken(UserCredentials user){

        Map<String,Object> claims = new HashMap<>();
        claims.put("role",user.getRole());
        claims.put("status",user.getStatus());
        return jwtService.generateToken(user.getEmail(),claims);
    }
    public ResponseEntity<String> registerCompany( CompanySignUpDto data) throws HttpException {
        try{
            String id = this.save(data.getUser(), Role.COMPANY);
            CompanyDto company = data.getCompany();
            company.setUserId(id);
            company.setNotAdmin(false);
            kafkaTemplate.send("company-user-signed-up",company);
            return  ResponseEntity.ok("worker registered succesfully");

        }catch(BadRequestException e){
            return ResponseEntity.badRequest().body("User email already exist");
        }


    }
    public Object[] getTokens(){
        return this.emailTokenRepository.findAll().toArray();
    }
    public ResponseEntity<String> registerWorker( WorkerSignUpRequestDto data) throws HttpException {
        try{
            String id = this.save(data.getUser(), Role.WORKER);
            WorkerDto worker = data.getWorker();
            worker.setUserId(id);
            worker.setNotAdmin(false);
            kafkaTemplate.send("worker-user-signed-up",worker);
            return  ResponseEntity.ok("worker registered succesfully");

        }catch(BadRequestException e){
            return ResponseEntity.badRequest().body("User email already exist");
        }

    }


    public ResponseEntity<Map<String,Object>> registerUser( NewAccountDto user){
        Map<String,Object> object = new HashMap<>();
        Optional<UserCredentials> userDbOp = this.userCredentialsRepository.findByEmailWithToken(user.getEmail());
//        System.out.println("iam>>>>>>>>."+userDbOp);
        if(userDbOp.isPresent()){
            UserCredentials userDb = userDbOp.get();

                object.put("message","email already exist");
               return  ResponseEntity.ok(object);
        }
        String token = generateToken();
        emailServiceGeeks.sendSimpleMail(new EmailDetails(user.getEmail(),"Your Otp code is :"+token,"Email Verifications",null) );
        String tokenId = createUserAndSaveToken(user,token);

        object.put("tokenId",tokenId);

        return ResponseEntity.ok(object);
    }
    public ResponseEntity<String> registerWorkerStep2(String userId, WorkerDto data) throws HttpException {
        Optional<UserCredentials> userDbOp = userCredentialsRepository.findById(userId);
        if(userDbOp.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserCredentials userDb = userDbOp.get();
        if(userDb.getRole() != Role.WORKER){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not a worker");
        }
        if(userDb.getStatus().equals(UserStatus.EMAIL_NOT_VERIFIED)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("email not verified ");
        }
        data.setUserId(userDb.getId());
        data.setNotAdmin(true);
        kafkaTemplate.send("worker-user-signed-up",data);

        return  ResponseEntity.ok("worker registered succesfully");
    }
    public ResponseEntity<String> registerCompanyStep2(String userId, CompanyDto data) throws HttpException {
        Optional<UserCredentials> userDbOp = userCredentialsRepository.findById(userId);
        if(userDbOp.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserCredentials userDb = userDbOp.get();
        if(userDb.getRole() != Role.COMPANY){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not a company");
        }
        if(userDb.getStatus().equals(UserStatus.EMAIL_NOT_VERIFIED)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("email not verified ");
        }
        data.setUserId(userDb.getId());
        data.setNotAdmin(true);
        kafkaTemplate.send("company-user-signed-up",data);
        return  ResponseEntity.ok("worker registered succesfully");
    }
    @Transactional
    public ResponseEntity<String> validateEmail(String tokenId,String otp){
        Optional<EmailToken> tokenDbOp = emailTokenRepository.findById(tokenId);
        if(tokenDbOp.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("can't find token");
        }
        EmailToken tokenDb = tokenDbOp.get();
        Date tokenExpirationDate = new Date(tokenDb.getCreatedAt().getTime() +3*60*1000);

        if(tokenExpirationDate.before(new Date())){
            this.emailTokenRepository.delete(tokenDb);
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token expired");
        }
        if(!otp.equals(tokenDb.getToken())){

            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("wrong otp");
        }

        UserCredentials userDb = tokenDb.getUser();
        userDb.setStatus(UserStatus.EMAIL_VERIFIED);
        this.emailTokenRepository.delete(tokenDb);

        userCredentialsRepository.save(userDb);

        return ResponseEntity.ok(this.generateToken(userDb));
    }
    @Transactional
    private String createUserAndSaveToken(NewAccountDto user,String token  ){
        System.out.println("user creation ..........");
        Optional<UserCredentials> userOp = userCredentialsRepository.findByEmail(user.getEmail());
        System.out.println("user creation >>>>>>>>>>>"+userOp.toString());

        UserCredentials userDb = userCredentialsRepository.save(
                UserCredentials.builder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .email(user.getEmail())
                        .status(UserStatus.EMAIL_NOT_VERIFIED)
                        .role(user.getRole())
                        .build()
        );
        System.out.println("user created  .........."+userDb);
        EmailToken tokenDb = emailTokenRepository.save(
                EmailToken.builder()
                        .token(token)
                        .user(userDb)
                        .createdAt(new Date())
                        .build()
        );

        return tokenDb.getId();
    }
    public ValidateTokenResponse validateToken(final String token){
        Jws<Claims> claims =  jwtService.validateToken(token);
        String email = (String) claims.getBody().get("sub");
        System.out.println("email "+email);
        Optional<UserCredentials> userOp = userCredentialsRepository.findByEmail(email);
        if(userOp.isEmpty()) throw new BadRequestException("user not found");
        UserCredentials userDb = userOp.get();
        return ValidateTokenResponse.builder()
                .email(userDb.getEmail())
                .role(userDb.getRole())
                .userId(userDb.getId())
                .build();
    }

    public Object[] getUsers(){
        return new List[]{this.userCredentialsRepository.findAll()};
    }

    public static String generateToken() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            otp.append(digit);
        }

        return otp.toString();
    }


    public List<Email> getEmails(String ids){
        List<String> workers= Arrays.stream(ids.split(",")).toList();
        System.out.println("<<<"+workers);
             List<Email> emails=new ArrayList<>();

        for (String id:workers) {
            String em=userCredentialsRepository.findById(id).get().getEmail();
            System.out.println(id+"||||"+em);

           emails.add(new Email(id,em));

        }
        return emails;
    }

    public Statistics getStatistics(){
        Statistics statistics=new Statistics();
        statistics.setNb_clients(userCredentialsRepository.getCompanyCount());
        statistics.setNb_admins(userCredentialsRepository.getAdminCount());
        statistics.setNb_workers(userCredentialsRepository.getWorkerCount());
        statistics.setNb_workers_baned(userCredentialsRepository.getBannedCount());
        return statistics;
    }
}
