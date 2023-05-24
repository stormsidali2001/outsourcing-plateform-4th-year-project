package com.example.storagemicroservice.Repositories;

import com.example.storagemicroservice.Entities.media;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MediaRepo extends JpaRepository<media,Long> {


    @Query("select m from media m where m.filename= :filename")
    media findMediaByFilename(@Param("filename") String filename);


}
