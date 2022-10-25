package com.example.WithRun.repository;

import com.example.WithRun.domain.FreePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreePostRepository extends JpaRepository<FreePost,Long> {
}
