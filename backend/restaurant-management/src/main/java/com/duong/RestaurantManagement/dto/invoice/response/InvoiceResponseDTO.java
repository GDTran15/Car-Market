package com.duong.RestaurantManagement.dto.invoice.response;

import com.duong.RestaurantManagement.dto.order.response.GetOrderItemDTO;
import com.duong.RestaurantManagement.model.InvoiceStatus;
import com.duong.RestaurantManagement.model.MembershipRank;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record InvoiceResponseDTO(
        Long invoiceId,
        double payBeforeDiscount,
        double discountAmount,
        double totalPay,
        InvoiceStatus invoiceStatus,
        Long diningSessionId,
        List<GetOrderItemDTO> orderItems


) {
}
