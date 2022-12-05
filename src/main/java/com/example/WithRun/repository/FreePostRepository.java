package com.example.WithRun.repository;

import com.example.WithRun.domain.FreePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreePostRepository extends JpaRepository<FreePost,Long> {

    List<FreePost> findByTitleContainingIgnoreCase(String keyword);
}
