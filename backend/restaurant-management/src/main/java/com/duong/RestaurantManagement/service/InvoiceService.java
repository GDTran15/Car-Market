package com.duong.RestaurantManagement.service;

import com.duong.RestaurantManagement.dto.invoice.response.InvoiceResponseDTO;
import com.duong.RestaurantManagement.dto.invoice.response.UpdateInvoiceMemberResponse;
import com.duong.RestaurantManagement.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    InvoiceResponseDTO createNewInvoice(Long dinningSessionId);

    UpdateInvoiceMemberResponse invoiceChangeAfterMember(Long invoiceId, String memberPhone);

    void markInvoiceAsPaid(Invoice invoice);

     List<InvoiceResponseDTO> getTodayInvoice();

     InvoiceResponseDTO getInvoice(Long invoiceId);

     Optional<InvoiceResponseDTO> getActiveUnpaidInvoice(Long diningSessionId);

     InvoiceResponseDTO cancelInvoice(Long invoiceId);
}
