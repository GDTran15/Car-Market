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
@Table(name = "restaurant_tables")
public class RestaurantTable {
    @Id
    private int restaurantTableId;

    private int restaurantTableNumber;

    private int capacity;

    private RestaurantTableStatus restaurantTableStatus;

    @OneToMany(mappedBy = "restaurantTable")
    private List<DiningSession> diningSessions;
}
