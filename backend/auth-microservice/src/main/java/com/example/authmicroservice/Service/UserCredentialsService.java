package com.example.authmicroservice.Service;

import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.Repository.UserCredentialsRepository;
import com.example.authmicroservice.dto.UserCredentialsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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
    public Jwt<Header, Claims> validateToken(final String token){
        return jwtService.validateToken(token);
    }
}
