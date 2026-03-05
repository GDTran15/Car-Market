package com.duong.RestaurantManagement.service;

import com.duong.RestaurantManagement.exception.DuplicateResourceException;
import com.duong.RestaurantManagement.model.FoodCategory;
import com.duong.RestaurantManagement.repo.FoodCategoryRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodCategoryService {

    private final FoodCategoryRepo foodCategoryRepo;


    public void createNewFoodCategory(String foodCategoryName) {
        boolean categoryExist = foodCategoryRepo.existsByFoodCategoryName(foodCategoryName);
        if (categoryExist) {
            throw new DuplicateResourceException("Food category already exist");
        }
        FoodCategory foodCategory = FoodCategory.builder()
                .foodCategoryName(foodCategoryName).build();


        foodCategoryRepo.save(foodCategory);

    }
}
