package com.example.interactionmicroservice.Entities;


import com.example.interactionmicroservice.types.InteractionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Builder @Data
@Entity@AllArgsConstructor@NoArgsConstructor
public class Impression {
    @EmbeddedId
    private InteractionId idImpression;


    private Date createdAt;



}
