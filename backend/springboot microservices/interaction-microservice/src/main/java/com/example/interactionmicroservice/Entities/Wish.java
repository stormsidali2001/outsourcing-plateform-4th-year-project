package com.example.interactionmicroservice.Entities;


import com.example.interactionmicroservice.types.InteractionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder @Data @Entity
@AllArgsConstructor
@NoArgsConstructor
public class Wish {


    @EmbeddedId
    private InteractionId idWish;

    private Date createdAt;


}
