package com.duong.RestaurantManagement.service;

import com.duong.RestaurantManagement.dto.food.request.AddFoodRequestDTO;
import com.duong.RestaurantManagement.exception.DuplicateResourceException;
import com.duong.RestaurantManagement.model.Food;
import com.duong.RestaurantManagement.repo.FoodRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepo foodRepo;
    private final FoodCategoryMapService foodCategoryMapService;

    @Transactional
    public void createNewFood(AddFoodRequestDTO addFoodRequestDTO) {
        if (foodRepo.existsByFoodName(addFoodRequestDTO.foodName())){
            throw new DuplicateResourceException("This food already existed");
        }
        Food newFood = Food.builder()
                .foodName(addFoodRequestDTO.foodName())
                .isAvailable(addFoodRequestDTO.isAvailable())
                .price(addFoodRequestDTO.price())
                .description(addFoodRequestDTO.description())
                .foodImageUrl(addFoodRequestDTO.foodImageUrl())
                .quantity(addFoodRequestDTO.quantity())
                .build();
        foodRepo.save(newFood);
        foodCategoryMapService.mapFoodToCategory(newFood,addFoodRequestDTO.foodCategoryId());
    }
}
