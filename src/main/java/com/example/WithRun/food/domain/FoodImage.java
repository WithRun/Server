package com.example.WithRun.food.domain;


import com.example.WithRun.food.dto.FoodImageDTO;
import com.example.WithRun.food.dto.NutritionDTO;
import com.example.WithRun.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foodimage_id")
    private Long id;

    private String filename;
    private String imageUrl;

    @OneToOne(mappedBy = "nutritionFoodImage")
    private Nutrition foodImageNutrition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User foodImageUser;


    public FoodImageDTO toDto(NutritionDTO nutritionDTO){
        return FoodImageDTO.builder()
                .id(id).filename(filename).imageUrl(imageUrl)
                .nutritionDTO(nutritionDTO).build();
    }


    public FoodImageDTO toDto(){
        return FoodImageDTO.builder()
                .id(id).filename(filename).imageUrl(imageUrl)
                .nutritionDTO(foodImageNutrition.toDto()).build();
    }


    public void addNutrition(Nutrition nutrition){
        nutrition.setNutritionFoodImage(this);
        this.setFoodImageNutrition(nutrition);
    }
}
