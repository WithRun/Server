package com.example.WithRun.crewinfo.repository;

import com.example.WithRun.crewinfo.domain.CrewInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewInfoRepository extends JpaRepository<CrewInfo, Long> {
    List<CrewInfo> findByTitleContainingIgnoreCase(String keyword);

}
