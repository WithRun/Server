package com.example.WithRun.user.repository;

import com.example.WithRun.user.domain.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage,Long> {

    UserImage findByName(String filename);
}
