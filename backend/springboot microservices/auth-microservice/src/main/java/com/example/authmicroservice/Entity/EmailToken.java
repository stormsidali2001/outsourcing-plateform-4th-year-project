package com.example.authmicroservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name="email_token") @Table(name = "email_token") @Builder @NoArgsConstructor @AllArgsConstructor @Data
public class EmailToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(fetch = FetchType.EAGER)  @JoinColumn(name = "userId")
    UserCredentials user;

    @Column(insertable=false, updatable=false,name = "userId")
    private String userId;
}
