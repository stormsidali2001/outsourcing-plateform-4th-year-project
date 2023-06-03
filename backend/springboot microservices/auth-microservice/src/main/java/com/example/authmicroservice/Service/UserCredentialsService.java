package com.example.authmicroservice.Service;

import com.example.authmicroservice.Entity.EmailToken;
import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.Repository.EmailTokenRepository;
import com.example.authmicroservice.Repository.UserCredentialsRepository;
import com.example.authmicroservice.dto.NewAccountDto;
import com.example.authmicroservice.dto.RegisterUserDto;
import com.example.authmicroservice.dto.UserCredentialsDto;
import com.example.authmicroservice.dto.ValidateTokenResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.SecureRandom;
import java.util.*;


@Service
public class UserCredentialsService {
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

  @Autowired
  private JwtService jwtService;

    public String save(RegisterUserDto userData, Role role) throws BadRequestException {
        System.out.println("PasswordEncoder: " + passwordEncoder.getClass().getName());
        Optional<UserCredentials> userOp = userCredentialsRepository.findByEmail(userData.getEmail());
        if(userOp.isPresent()) throw new BadRequestException("Email already exist");
       return  userCredentialsRepository.save(
                UserCredentials.builder()
                        .email(userData.getEmail())
                        .password(passwordEncoder.encode(userData.getPassword()))
                        .role(role)
                        .build()
        ).getId();
    }

    public String generateToken(UserCredentialsDto data){

        return jwtService.generateToken(data.getEmail());
    }


    public ResponseEntity<Map<String,Object>> registerUser( NewAccountDto user){
        Optional<UserCredentials> userDbOp = this.userCredentialsRepository.findByEmail(user.getEmail());
        if(userDbOp.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String token = generateToken();
        emailService.sendEmail(user.getEmail(),"Email Verifications","Your Otp code is :"+token);
        String tokenId = createUserAndSaveToken(user,token);
        Map<String,Object> object = new HashMap<>();
        object.put("tokenId",tokenId);
        return ResponseEntity.ok(object);
    }
    @Transactional
    public ResponseEntity<String> validateEmail(String tokenId,String otp){
        Optional<EmailToken> tokenDbOp = emailTokenRepository.findByTokenId(tokenId);
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

        return ResponseEntity.ok("user created succesfullly");
    }
    @Transactional
    private String createUserAndSaveToken(NewAccountDto user,String token  ){
        UserCredentials userDb = userCredentialsRepository.save(
                UserCredentials.builder()
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .status(UserStatus.EMAIL_NOT_VERIFIED)
                        .build()
        );
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

        return ValidateTokenResponse.builder()
                .email(userOp.get().getEmail())
                .role(userOp.get().getRole())
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
}
