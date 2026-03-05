package com.duong.RestaurantManagement.dto.food.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddFoodRequestDTO(
        @NotBlank
        String foodName,
        @NotNull
        boolean isAvailable,
        @NotBlank
        String description,
        @NotNull
        int quantity,
        @NotBlank
        String foodImageUrl,
        @NotNull
        double price,
        @NotNull
        Long foodCategoryId


) {
}
