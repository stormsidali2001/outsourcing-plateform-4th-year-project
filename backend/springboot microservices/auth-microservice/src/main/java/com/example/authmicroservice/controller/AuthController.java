package com.example.authmicroservice.controller;

import com.example.authmicroservice.Entity.EmailDetails;
import com.example.authmicroservice.Service.UserCredentialsService;
import com.example.authmicroservice.config.EmailServiceGeeks;
import com.example.authmicroservice.dto.*;
import com.example.authmicroservice.exception.BadRequestException;
import com.example.authmicroservice.types.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.v3.oas.annotations.headers.Header;
import jakarta.validation.Valid;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

public class AuthController {




    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserCredentialsService userCredentialsService;


//    @PostMapping("register")
//    public String registerUser(@RequestBody RegisterUserDto user){
//        userCredentialsService.save(user);
//        kafkaTemplate.send("worker-user-signed-up","hiiiiiiiiiiiiiiiiiiii worker");
//        return "user registered succesfully";
//    }
@Autowired private EmailServiceGeeks emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }
    @PostMapping("registration/worker")
    public ResponseEntity<String> registerWorker( @RequestBody @Valid WorkerSignUpRequestDto data) throws HttpException {
      return this.userCredentialsService.registerWorker(data);

    }
    @PostMapping("registration/company")
    public ResponseEntity<String> registerCompany( @RequestBody @Valid CompanySignUpDto data) throws HttpException {
       return this.userCredentialsService.registerCompany(data);

    }

    @PostMapping("signin")
    public ResponseEntity<String> signinUser(@RequestBody @Valid UserCredentialsDto user){
        System.out.println("login with "+user);
        Authentication authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        if(!authentication.isAuthenticated())   {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("wrong credentials");
        }
        return ResponseEntity.ok(userCredentialsService.generateToken(user));
    }
    @PostMapping("registration/user")
    public ResponseEntity<Map<String,Object>> registerUser(@RequestBody @Valid NewAccountDto user){
        return this.userCredentialsService.registerUser(user);
    }
    @PostMapping("registration/user/worker")
    public ResponseEntity<Object> registerWorkerStep2(@RequestHeader("X-userId") String userId , @RequestBody @Valid WorkerDto data) throws HttpException {
return ResponseEntity.ok(userId);
       //        return userCredentialsService.registerWorkerStep2(headers.userId,data);
    }
    @PostMapping("registration/user/company")
    public ResponseEntity<String> registerCompanyStep2(@RequestHeader("X-userId") String userId ,  @RequestBody @Valid CompanyDto data) throws HttpException {
        return userCredentialsService.registerCompanyStep2(userId,data);
    }
    @GetMapping("validate-token")
    public ValidateTokenResponse validateToken(@RequestParam("token") String token ){
        return    this.userCredentialsService.validateToken(token);
    }

    @PostMapping("validate-email")
    public ResponseEntity<String> validateEmail(@RequestBody @Valid ValidateEmailDto data ){
        return  userCredentialsService.validateEmail(data.getTokenId(),data.getOtp());
    }
//    @PostMapping("resend-otp")
//    public ResponseEntity<String> resendOtp(){
//
//    }
    @GetMapping("users")
    public Object[] getUsers(){
        return this.userCredentialsService.getUsers();
    }

     @GetMapping("workers/emails")
    public List<Email> getEmails(@RequestParam("ids") String ids){
        return userCredentialsService.getEmails(ids);
     }

    @GetMapping("tokens")
    public Object[] getTokens(){
        return this.userCredentialsService.getTokens();
    }


     @GetMapping("admin/statistics")
    public Statistics getStatistics(){
        return userCredentialsService.getStatistics();
     }

}
