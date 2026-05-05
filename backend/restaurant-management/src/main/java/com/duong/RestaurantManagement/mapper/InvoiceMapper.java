package com.duong.RestaurantManagement.mapper;


import com.duong.RestaurantManagement.dto.invoice.response.InvoiceResponseDTO;
import com.duong.RestaurantManagement.dto.invoice.response.UpdateInvoiceMemberResponse;
import com.duong.RestaurantManagement.dto.order.response.GetOrderItemDTO;
import com.duong.RestaurantManagement.model.Invoice;
import com.duong.RestaurantManagement.model.Member;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class InvoiceMapper {

    public static InvoiceResponseDTO invoiceToCreateInvoiceResponse(Invoice invoice, List<GetOrderItemDTO> orderItems) {
        return new InvoiceResponseDTO(
                invoice.getInvoiceId(),
                invoice.getPayBeforeDiscount(),
                invoice.getDiscountAmount() ,
                invoice.getTotalPay(),
                invoice.getInvoiceStatus(),
                invoice.getDiningSession().getDiningSessionId(),
                orderItems

        );
    }

    public static UpdateInvoiceMemberResponse mapToUpdateInvoiceMemberResponse(Invoice invoice, Member member) {
        return new UpdateInvoiceMemberResponse(
                invoice.getInvoiceId(),
                invoice.getDiscountAmount(),
                invoice.getTotalPay(),
                member.getFirstName(),
                member.getLastName(),
                member.getMemberRank()
        );
    }
}
