package com.example.WithRun.repository;

import com.example.WithRun.domain.FreePostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreePostImageRepository extends JpaRepository<FreePostImage,Long> {
}
