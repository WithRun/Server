package com.example.WithRun.freepost.repository;

import com.example.WithRun.freepost.domain.FreePostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreePostImageRepository extends JpaRepository<FreePostImage,Long> {

    FreePostImage findByUrl(String imageUrl);
}
