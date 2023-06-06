package com.example.authmicroservice.Entity;


import com.example.authmicroservice.types.Role;
import com.example.authmicroservice.types.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name="users")
@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class UserCredentials implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    private String password;


    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private EmailToken emailToken;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
