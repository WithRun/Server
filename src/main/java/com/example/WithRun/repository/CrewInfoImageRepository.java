package com.example.WithRun.repository;

import com.example.WithRun.domain.CrewInfoImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewInfoImageRepository extends JpaRepository<CrewInfoImage, Long> {

}
