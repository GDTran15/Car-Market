package com.duong.RestaurantManagement.repo;

import com.duong.RestaurantManagement.dto.invoice.response.InvoiceResponseDTO;
import com.duong.RestaurantManagement.model.Invoice;
import com.duong.RestaurantManagement.model.InvoiceStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {

    boolean existsById(@NotNull Long invoiceId);

    Optional<Invoice> findFirstByDiningSession_DiningSessionIdAndInvoiceStatusOrderByCreatedAtDesc(
            Long diningSessionId,
            InvoiceStatus invoiceStatus
    );

    @Query("""
    select new com.duong.RestaurantManagement.dto.invoice.response.InvoiceResponseDTO(
        i.invoiceId,
        i.payBeforeDiscount,
        i.discountAmount,
        i.totalPay,
        i.invoiceStatus,
        i.diningSession.diningSessionId,
        null
        )
        from Invoice i
            where i.createdAt between :start and :end
    """)
    List<InvoiceResponseDTO> findListOfTodayInvoice(@Param("start") LocalDateTime startOfDay,@Param("end") LocalDateTime endOfDay);
}
