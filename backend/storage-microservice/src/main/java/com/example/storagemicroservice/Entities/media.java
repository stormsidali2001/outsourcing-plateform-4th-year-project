package com.example.storagemicroservice.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor
public class media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedia;
    @Column(unique = true)
    private String filename;
    private String type;

//    private String idWorker;

//    @Transient
//    private List<Certification> certifications;

}
