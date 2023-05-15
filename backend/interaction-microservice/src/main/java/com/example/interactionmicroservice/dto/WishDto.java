package com.example.interactionmicroservice.dto;

import com.example.interactionmicroservice.types.InteractionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishDto {


    @EmbeddedId
    private InteractionId idWish;

    @NotBlank(message = "Date of creation  is required")
    private Date createdAt;
}
