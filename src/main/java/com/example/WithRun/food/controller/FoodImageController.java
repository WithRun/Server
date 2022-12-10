package com.example.WithRun.food.controller;

import com.amazonaws.Response;
import com.example.WithRun.food.dto.NutritionDTO;
import com.example.WithRun.food.service.FoodImageService;
import com.example.WithRun.user.domain.User;
import com.example.WithRun.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FoodImageController {

    @Autowired
    FoodImageService foodImageService;

    @Autowired
    UserService userService;

    @GetMapping("/food")
    public ResponseEntity<?> getAllMyFoodImages(@AuthenticationPrincipal String id){
        User user = userService.getUserById(id);

        return ResponseEntity.ok().body(foodImageService.getAllMyFoodImageDto(user));



    }


    @PostMapping("/food/post")
    public ResponseEntity<?> createFoodImage(@AuthenticationPrincipal String id,
                                             @RequestPart MultipartFile image,
                                             @RequestPart NutritionDTO nutritionDTO) throws IOException {

        User user = userService.getUserById(id);

        return ResponseEntity.ok().body(foodImageService.createFoodImage(user,image,nutritionDTO,"Local"));
    }
}
