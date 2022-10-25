package com.example.WithRun.repository;

import com.example.WithRun.domain.RecruitPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitPostRepository extends JpaRepository<RecruitPost,Long> {
}
