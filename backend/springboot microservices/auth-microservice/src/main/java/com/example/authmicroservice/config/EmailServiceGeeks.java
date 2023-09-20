package com.example.authmicroservice.config;

// Java Program to Illustrate Creation Of
// Service Interface


// Importing required classes


import com.example.authmicroservice.Entity.EmailDetails;

// Interface
public interface EmailServiceGeeks {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
