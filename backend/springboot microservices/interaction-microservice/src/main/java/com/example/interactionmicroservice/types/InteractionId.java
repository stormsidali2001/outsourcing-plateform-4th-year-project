package com.example.interactionmicroservice.types;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor@NoArgsConstructor@Data
public class InteractionId implements Serializable {

    private String idCompany;

    private String idWorker;


}
