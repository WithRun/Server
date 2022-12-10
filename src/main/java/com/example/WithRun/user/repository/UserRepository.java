package com.example.WithRun.user.repository;

import com.example.WithRun.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserID(String userId);
    User findByUsername(String username);
    User findByEmail(String email);

    Boolean existsByUserID(String userId);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
