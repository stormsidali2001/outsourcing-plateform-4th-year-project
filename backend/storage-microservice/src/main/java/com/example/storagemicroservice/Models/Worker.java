package com.example.storagemicroservice.Models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class Worker {

    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;


    private Integer publicPrice; //by hour


    private Date signUpDate;



}
