package com.example.WithRun.food.repository;

import com.example.WithRun.food.domain.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionRepository extends JpaRepository<Nutrition,Long> {


}
