package com.example.WithRun.food.dto;


import com.example.WithRun.food.domain.FoodImage;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodImageDTO {

    private long id;
    private String filename;
    private String imageUrl;

    private NutritionDTO nutritionDTO;

}
