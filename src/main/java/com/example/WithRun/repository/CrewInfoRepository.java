package com.example.WithRun.repository;

import com.example.WithRun.domain.CrewInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewInfoRepository extends JpaRepository<CrewInfo, Long> {

}
