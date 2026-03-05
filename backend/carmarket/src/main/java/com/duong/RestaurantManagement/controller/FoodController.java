package com.duong.RestaurantManagement.controller;

import com.duong.RestaurantManagement.dto.food.request.AddFoodRequestDTO;
import com.duong.RestaurantManagement.model.Food;
import com.duong.RestaurantManagement.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping()
    public ResponseEntity<String> addFood(@RequestBody AddFoodRequestDTO addFoodRequestDTO) {
        foodService.createNewFood(addFoodRequestDTO);
        return ResponseEntity.ok("Successfully added food");
    }
}
