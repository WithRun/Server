package com.example.WithRun.food.dto;

import com.example.WithRun.food.domain.Nutrition;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionDTO {

    private String name;
    private String weight_g;
    private String calorie_kcal;
    private String Carbohydrate_g;
    private String sugars_g;
    private String fat_g;
    private String protein_g;
    private String calcium_mg;
    private String phosphorus_g;
    private String sodium_mg;
    private String potassium_mg;
    private String magnesium_mg;
    private String iron_mg;
    private String zinc_mg;
    private String cholesterol_mg;
    private String transFat_g;

    public Nutrition toEntity(){
        return Nutrition.builder().name(name).weight_g(weight_g).calorie_kcal(calorie_kcal)
                .Carbohydrate_g(Carbohydrate_g).sugars_g(sugars_g).fat_g(fat_g).protein_g(protein_g)
                .calcium_mg(calcium_mg).phosphorus_g(phosphorus_g).sodium_mg(sodium_mg)
                .potassium_mg(potassium_mg).magnesium_mg(magnesium_mg).iron_mg(iron_mg)
                .zinc_mg(zinc_mg).cholesterol_mg(cholesterol_mg).transFat_g(transFat_g)
                .build();
    }
}
