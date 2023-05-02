package com.example.authmicroservice.Service;

import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.Repository.UserCredentialsRepository;
import com.example.authmicroservice.dto.UserCredentialsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserCredentialsService {
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

    public void save(UserCredentialsDto userData){
        userCredentialsRepository.save(
                UserCredentials.builder()
                        .username(userData.getUsername())
                        .email(userData.getEmail())
                        .password(passwordEncoder.encode(userData.getPassword()))
                        .build()
        );
    }

    public String generateToken(UserCredentialsDto data){

        return jwtService.generateToken(data.getUsername());
    }
    public Jws<Claims> validateToken(final String token){
        return jwtService.validateToken(token);
    }
}
