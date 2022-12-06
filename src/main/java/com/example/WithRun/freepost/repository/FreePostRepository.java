package com.example.WithRun.freepost.repository;

import com.example.WithRun.freepost.domain.FreePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreePostRepository extends JpaRepository<FreePost,Long> {

    List<FreePost> findByTitleContainingIgnoreCase(String keyword);
}
