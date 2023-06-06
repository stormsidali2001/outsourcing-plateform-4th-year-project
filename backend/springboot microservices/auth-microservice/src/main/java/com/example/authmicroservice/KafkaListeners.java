package com.example.authmicroservice;

import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.Repository.UserCredentialsRepository;
import com.example.authmicroservice.exception.BadRequestException;
import com.example.authmicroservice.types.UserStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaListeners {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @KafkaListener(
            groupId = "group2",
            topics = {"worker-unvalid","company-unvalid"}
    )
    public void listener(ConsumerRecord data){
        String userId = (String) data.value();
        userId= userId.substring(1,userId.length()-1); // removing the "<userId>"
        userCredentialsRepository.deleteById(userId);
        System.out.println("user unvalid event ---> user : "+userId+" deleted sucessfully");
    }

    @KafkaListener(
            groupId = "group2",
            topics = {"worker-unvalid-not-admin","company-unvalid-not-admin"}
    )
    public void listenerNotAdmin(ConsumerRecord data){
        String userId = (String) data.value();
        userId= userId.substring(1,userId.length()-1); // removing the "<userId>"
        Optional<UserCredentials> userDbOp = userCredentialsRepository.findById(userId);
        if(userDbOp.isPresent()){
            UserCredentials user = userDbOp.get();
            user.setStatus(UserStatus.EMAIL_NOT_VERIFIED);
            userCredentialsRepository.save(user);
        }else{
            throw new BadRequestException("user not found");
        }
    }
}
