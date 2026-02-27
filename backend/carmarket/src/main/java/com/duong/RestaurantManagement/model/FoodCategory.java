package com.duong.RestaurantManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "food_category")
public class FoodCategory {
    @Id
    private Long FoodCategoryId;
    private String FoodCategoryName;

    @OneToMany(mappedBy = "foodCategory")
    private List<FoodCategoryMap> foodCategoryMaps;
}
