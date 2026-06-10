package com.duong.RestaurantManagement.serviceImp;


import com.duong.RestaurantManagement.dto.invoice.response.InvoiceResponseDTO;
import com.duong.RestaurantManagement.dto.invoice.response.UpdateInvoiceMemberResponse;
import com.duong.RestaurantManagement.dto.order.response.GetOrderItemDTO;
import com.duong.RestaurantManagement.exception.InvalidOrderStateException;
import com.duong.RestaurantManagement.exception.InvoiceHasBeenPaidException;
import com.duong.RestaurantManagement.exception.ResourceNotFoundException;
import com.duong.RestaurantManagement.mapper.InvoiceMapper;
import com.duong.RestaurantManagement.model.*;
import com.duong.RestaurantManagement.repo.*;
import com.duong.RestaurantManagement.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImp implements InvoiceService {

    private final DiningSessionRepo diningSessionRepo;

    private final DiningSessionService   diningSessionService;

    private final MemberRepo memberRepo;

    private final MembershipRepo membershipRepo;

    private final InvoiceRepo invoiceRepo;

    private final MemberService memberService;

    private final RestaurantTableRepo restaurantTableRepo;

    private final RestaurantTableService restaurantTableService;
    private final OrderService orderService;


    @Transactional
    @Override
    public InvoiceResponseDTO createNewInvoice(Long tableId) {
        DiningSession diningSession = diningSessionRepo.findByDiningStatusAndRestaurantTable_RestaurantTableId(DiningStatus.ACTIVE,tableId)
                .orElseThrow(() -> new ResourceNotFoundException("No dining session found for this table"));
        if (orderService.hasActiveOrderByDiningSession(diningSession.getDiningSessionId())){
            throw new InvalidOrderStateException("There still active order in dining session");
        }

        List<GetOrderItemDTO> orderItems = diningSessionService.getDiningSessionOrderItems(diningSession.getDiningSessionId());

        double diningSessionPrice = diningSessionService.getDiningSessionTotalOrderPrice(diningSession.getDiningSessionId());
        Invoice invoice = Invoice.builder()
                .payBeforeDiscount(diningSessionPrice)
                .discountAmount(0)
                .totalPay(diningSessionPrice - 0)
                .diningSession(diningSession)
                .createdAt(LocalDateTime.now())
                .invoiceStatus(InvoiceStatus.UNPAID)
                .build();
        invoiceRepo.save(invoice);
        // the diningsession and table status must not change if the invoice is not paid yet
       // diningSessionService.deactiveDinningSession(diningSession.getDiningSessionId());
        //restaurantTableService.changeTableStatus(diningSession.getRestaurantTable());
        return InvoiceMapper.invoiceToCreateInvoiceResponse(invoice,  orderItems);
    }

    @Override
    @Transactional
    public UpdateInvoiceMemberResponse invoiceChangeAfterMember(Long invoiceId, String memberPhone) {
        Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow(
                () -> new ResourceNotFoundException("No invoice found ")
        );
        checkIfInvoiceIsPaid(invoice.getInvoiceStatus(), "Cannot add member after invoice been paid");


        Member member = memberRepo.findByMemberPhone(memberPhone).orElseThrow(
                () -> new ResourceNotFoundException("No member found ")
        );
        invoice.setMember(member);
        applyMemberDiscount(invoice,member);

        invoiceRepo.save(invoice);

        return InvoiceMapper.mapToUpdateInvoiceMemberResponse(invoice,member);
    }

    private void applyMemberDiscount(Invoice invoice, Member member) {
        double discountRate = membershipRepo
                .findMembershipDiscountRateByMembershipRank(member.getMemberRank());

        double discountAmount = invoice.getPayBeforeDiscount() * discountRate;

        invoice.setDiscountAmount(discountAmount);
        invoice.setTotalPay(invoice.getPayBeforeDiscount() - discountAmount);
    }

    @Override
    @Transactional
    public void markInvoiceAsPaid(Invoice invoice)  {
        checkIfInvoiceIsPaid(invoice.getInvoiceStatus(), "Invoice has been paid");
        invoice.setInvoiceStatus(InvoiceStatus.PAID);
        if (invoice.getMember() != null) {

            memberService.updateMemberAfterPayment(invoice.getMember(), invoice.getTotalPay());

        }
        diningSessionService.deactiveDinningSession(invoice.getDiningSession().getDiningSessionId());
        restaurantTableService.changeTableStatus(invoice.getDiningSession().getRestaurantTable());
        invoiceRepo.save(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> getTodayInvoice() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return invoiceRepo.findListOfTodayInvoice(startOfDay, endOfDay);
    }

    @Override
    public InvoiceResponseDTO getInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow(
                () -> new ResourceNotFoundException("No invoice found ")
        );

        List<GetOrderItemDTO> invoiceItems = diningSessionService.getDiningSessionOrderItems(invoice.getDiningSession().getDiningSessionId());
        return InvoiceMapper.invoiceToCreateInvoiceResponse(
                invoice, invoiceItems
        );
    }

    private void checkIfInvoiceIsPaid( InvoiceStatus  invoiceStatus, String message) {
        if (invoiceStatus == InvoiceStatus.PAID){
            throw new InvoiceHasBeenPaidException(message);
        }
    }
}
