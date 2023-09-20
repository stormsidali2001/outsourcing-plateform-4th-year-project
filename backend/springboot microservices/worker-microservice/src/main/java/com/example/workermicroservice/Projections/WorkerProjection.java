package com.example.workermicroservice.Projections;


import com.example.workermicroservice.types.WorkerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class WorkerProjection {

    private String userId;
    private String firstName;

    private String lastName;
    private Date signUpDate;
    private WorkerStatus status;
    @Transient
    private String email;

}