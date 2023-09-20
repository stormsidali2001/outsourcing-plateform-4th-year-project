package com.example.authmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class Statistics {
 private int nb_clients;
 private int nb_workers;
 private int nb_workers_baned;
 private int nb_admins;

}
