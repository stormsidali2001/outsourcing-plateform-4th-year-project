package com.example.interactionmicroservice.dto;

import com.example.interactionmicroservice.types.InteractionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Builder@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClickDto {
    private String idClick;

    @NotBlank(message = "worker  is required")
    private String idWorker;

//    private Date createdAt;
}
