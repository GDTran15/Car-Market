package com.duong.RestaurantManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    private String foodName;

    private boolean isAvailable;

    private String description;

    private int quantity;

    @OneToMany(mappedBy = "food")
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "food")
    private List<FoodCategoryMap> foodCategoryMaps;
}
