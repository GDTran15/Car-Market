package com.duong.RestaurantManagement.repo;

import com.duong.RestaurantManagement.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long> {

    boolean existsByFoodName(String foodName);


}
