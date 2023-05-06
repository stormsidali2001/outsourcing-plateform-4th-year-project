package com.example.authmicroservice.config;

import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.Repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserCredentials> user =  userCredentialsRepository.findByEmail(email);
        System.out.println(user);
        if(user.isEmpty()) throw new RuntimeException("user not  found");
        return user.get();

    }
}
