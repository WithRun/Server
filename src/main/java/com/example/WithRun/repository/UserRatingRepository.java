package com.example.WithRun.repository;

import com.example.WithRun.domain.User;
import com.example.WithRun.domain.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating,Long> {

    UserRating findByRatingUserIdAndAndRatedUser(Long ratingUserId, User ratedUser);
}
