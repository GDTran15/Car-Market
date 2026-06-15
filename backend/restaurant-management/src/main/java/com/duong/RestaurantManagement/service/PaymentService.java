package com.duong.RestaurantManagement.service;


import com.duong.RestaurantManagement.dto.payment.request.CashPaymentRequest;
import com.duong.RestaurantManagement.dto.payment.request.CreateOrderPaypalRequest;
import com.duong.RestaurantManagement.model.Payment;
import com.paypal.sdk.models.Order;

public interface PaymentService {


    Order createOrder(CreateOrderPaypalRequest request, String method, String intent, String description);

     Order captureOrder(String orderId);

     Payment createCashPayment(CashPaymentRequest request);
}
