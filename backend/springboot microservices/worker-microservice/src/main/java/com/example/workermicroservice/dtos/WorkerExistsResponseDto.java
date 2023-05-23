package com.example.workermicroservice.dtos;

import com.example.workermicroservice.types.WorkerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor @NoArgsConstructor @Data
public class WorkerExistsResponseDto {
    private String workerId;
    private boolean exists;
    private WorkerStatus status;
    private Integer publicPrice;
}
