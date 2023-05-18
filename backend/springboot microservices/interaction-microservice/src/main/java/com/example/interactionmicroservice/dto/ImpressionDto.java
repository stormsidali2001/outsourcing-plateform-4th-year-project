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
@Data  @AllArgsConstructor@NoArgsConstructor
public class ImpressionDto {

        private String idImpression;
//    @EmbeddedId
//    private InteractionId idImpression;
      @NotBlank(message = "worker  is required")
         private String idWorker;
       @NotBlank(message = "company  is required")
       private String idCompany;

//     private Date createdAt;

}
