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
@Table(name = "invoices")
public class Invoice {

    @Id
    private Long invoiceId;

    private double payBeforeDiscount;

    private double discountAmount;

    private double totalPay;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;


    @ManyToOne
    @JoinColumn(name = "dining_session_id")
    private DiningSession diningSession;

    @OneToMany(mappedBy = "invoice")
    private List<Payment> payments;
}
