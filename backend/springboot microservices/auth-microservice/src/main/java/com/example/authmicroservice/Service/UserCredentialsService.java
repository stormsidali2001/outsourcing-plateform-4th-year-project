package com.example.authmicroservice.Service;

import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.Repository.UserCredentialsRepository;
import com.example.authmicroservice.dto.RegisterUserDto;
import com.example.authmicroservice.dto.UserCredentialsDto;
import com.example.authmicroservice.types.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserCredentialsService {
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

    public String save(RegisterUserDto userData, Role role) throws HttpException {
        System.out.println("PasswordEncoder: " + passwordEncoder.getClass().getName());
        Optional<UserCredentials> userOp = userCredentialsRepository.findByEmail(userData.getEmail());
        if(userOp.isPresent()) throw new HttpException("Email already exist");
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
    public Jws<Claims> validateToken(final String token){
        return jwtService.validateToken(token);
    }

    public Object[] getUsers(){
        return new List[]{this.userCredentialsRepository.findAll()};
    }
}
