package com.duong.RestaurantManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private Long userId;

    private String username;

    private String email;

    private String password;

    private int phone;

    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;


}
