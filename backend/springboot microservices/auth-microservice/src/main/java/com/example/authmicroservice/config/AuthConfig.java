package com.example.authmicroservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig  {

    @Bean
    public UserDetailsService userDetailsService(){
        return  new CustomUserDetailsService();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        return http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/**").permitAll()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider d = new DaoAuthenticationProvider();
        d.setPasswordEncoder(passwordEncoder());
        d.setUserDetailsService(userDetailsService());
        return d;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception{
        return config.getAuthenticationManager();
    }




}

