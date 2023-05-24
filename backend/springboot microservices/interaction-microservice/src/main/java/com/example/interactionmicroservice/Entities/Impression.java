package com.example.interactionmicroservice.Entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder @Data
@Entity@AllArgsConstructor@NoArgsConstructor
public class Impression {

    @Id
    private String idImpression;

    private String idWorker;

    private String idCompany;

    private Date createdAt;


}
