package com.example.WithRun.freepost.repository;

import com.example.WithRun.freepost.domain.FreePostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreePostCommentRepository extends JpaRepository<FreePostComment, Long> {
}
