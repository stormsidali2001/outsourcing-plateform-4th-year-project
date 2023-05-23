package com.example.workermicroservice.dtos;


import com.example.workermicroservice.types.WorkerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PaginatedWorkerResponse {
    private String firstName;

    private String category;

    private String lastName;

    private String phoneNumber;


    private Integer publicPrice; //by hour


    private WorkerStatus status;

    private Date signUpDate;
}
