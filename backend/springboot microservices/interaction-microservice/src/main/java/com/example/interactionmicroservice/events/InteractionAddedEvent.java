package com.example.interactionmicroservice.events;

import com.example.interactionmicroservice.types.InteractionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class InteractionAddedEvent {
    private String companyId;

    private String workerId;

    private InteractionType interactionType;
}
