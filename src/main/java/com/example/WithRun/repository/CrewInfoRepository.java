package com.example.WithRun.repository;

import com.example.WithRun.domain.CrewInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewInfoRepository extends JpaRepository<CrewInfo, Long> {
    List<CrewInfo> findByTitleContainingIgnoreCase(String keyword);

}
