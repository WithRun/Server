package com.example.WithRun.food.service;


import com.example.WithRun.common.service.ImageService;
import com.example.WithRun.food.domain.FoodImage;
import com.example.WithRun.food.domain.Nutrition;
import com.example.WithRun.food.dto.FoodImageDTO;
import com.example.WithRun.food.dto.NutritionDTO;
import com.example.WithRun.food.repository.FoodImageRepository;
import com.example.WithRun.food.repository.NutritionRepository;
import com.example.WithRun.user.domain.User;
import com.example.WithRun.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FoodImageService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FoodImageRepository foodImageRepository;

    @Autowired
    NutritionRepository nutritionRepository;

    @Autowired
    ImageService imageService;

    public FoodImageDTO createFoodImage(User user, MultipartFile image, NutritionDTO nutritionDTO, String localOrServer) throws IOException {

        FoodImage foodImage= imageService.uploadFoodImage(image,user.getId().toString(),localOrServer);
        foodImageRepository.save(foodImage);

        FoodImageDTO foodImageDTO = foodImage.toDto(nutritionDTO);   //id가 foodimage에 저장이 되지않으면 imageservice에서 저장해보자.

        Nutrition nutrition = nutritionDTO.toEntity();
        foodImage.addNutrition(nutrition);
        foodImageRepository.save(foodImage); /// 영속성과 id생성될때를 알아야 함
        nutritionRepository.save(nutrition);

        return foodImageDTO;
    }

    public List<FoodImageDTO> getAllMyFoodImageDto(User user){
        List<FoodImage> foodImageList =  foodImageRepository.findByFoodImageUser(user);
        List<FoodImageDTO> foodImageDTOList = new ArrayList<>();

        for(FoodImage tmp : foodImageList){
            foodImageDTOList.add(tmp.toDto());
        }

        return foodImageDTOList;
    }





}
