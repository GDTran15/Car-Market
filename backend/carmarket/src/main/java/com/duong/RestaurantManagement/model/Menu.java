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
@Table(name = "menus")
public class Menu {

    @Id
    private Long menuId;

    private String menuName;

    private boolean isActivated;

    @OneToMany(mappedBy = "menu")
    private List<MenuItem> menuItems;
}
