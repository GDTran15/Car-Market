package com.duong.RestaurantManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    private int orderId;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @ManyToOne
    @JoinColumn(name = "dining_session_id")
    private DiningSession diningSession;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
