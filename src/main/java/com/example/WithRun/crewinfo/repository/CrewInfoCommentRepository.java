package com.example.WithRun.crewinfo.repository;

import com.example.WithRun.crewinfo.domain.CrewInfoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewInfoCommentRepository extends JpaRepository<CrewInfoComment,Long> {
}
