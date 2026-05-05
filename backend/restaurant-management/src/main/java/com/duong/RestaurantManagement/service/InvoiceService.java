package com.duong.RestaurantManagement.service;

import com.duong.RestaurantManagement.dto.invoice.response.InvoiceResponseDTO;
import com.duong.RestaurantManagement.dto.invoice.response.UpdateInvoiceMemberResponse;
import com.duong.RestaurantManagement.model.Invoice;
import org.apache.coyote.BadRequestException;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO createNewInvoice(Long dinningSessionId);

    UpdateInvoiceMemberResponse invoiceChangeAfterMember(Long invoiceId, String memberPhone);

    void markInvoiceAsPaid(Invoice invoice) throws BadRequestException;

     List<InvoiceResponseDTO> getTodayInvoice();

     InvoiceResponseDTO getInvoice(Long invoiceId);
}
