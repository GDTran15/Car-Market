package com.duong.RestaurantManagement.repo;

import com.duong.RestaurantManagement.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCategoryRepo extends JpaRepository<FoodCategory, Long> {

    boolean existsByFoodCategoryName(String foodCategoryName);
}
