package com.duong.RestaurantManagement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "dining_sessions")
public class DiningSession {

    @Id
    private Long DiningSessionId;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private DiningStatus diningStatus;


    @ManyToOne
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;

    @OneToMany(mappedBy = "diningSession")
    private List<Order> orders;

    @OneToMany(mappedBy = "diningSession")
    private List<Invoice> invoices;
}
