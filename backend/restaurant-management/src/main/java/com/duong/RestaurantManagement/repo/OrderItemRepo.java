package com.duong.RestaurantManagement.repo;


import com.duong.RestaurantManagement.model.OrderItem;
import com.duong.RestaurantManagement.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder_DiningSession_DiningSessionIdAndOrder_OrderStatus(
            Long diningSessionId,
            OrderStatus orderStatus
    );



}
