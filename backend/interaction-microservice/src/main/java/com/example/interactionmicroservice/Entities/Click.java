package com.example.interactionmicroservice.Entities;

import com.example.interactionmicroservice.types.InteractionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Builder @Data @Entity
@AllArgsConstructor
@NoArgsConstructor
public class Click {
    @EmbeddedId
    private InteractionId idClick;

    private Date createdAt;


}
