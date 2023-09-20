package com.example.workermicroservice.dtos;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Positive;

@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class PaginationFilterDto  {


    private Integer pageSize = 10;

    private Integer page = 1;


}
