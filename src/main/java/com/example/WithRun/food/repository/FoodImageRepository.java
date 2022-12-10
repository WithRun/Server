package com.example.WithRun.food.repository;

import com.example.WithRun.food.domain.FoodImage;
import com.example.WithRun.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodImageRepository extends JpaRepository<FoodImage,Long> {
    List<FoodImage> findByFoodImageUser(User user);

}
