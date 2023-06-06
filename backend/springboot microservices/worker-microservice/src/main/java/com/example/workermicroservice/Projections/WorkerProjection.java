package com.example.workermicroservice.Projections;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data@AllArgsConstructor@NoArgsConstructor
public class WorkerProjection {

    private String userId;
    private String firstName;

    private String lastName;
    @Transient
    private String email;

}