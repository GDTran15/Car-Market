package com.duong.RestaurantManagement.serviceImp;

import com.duong.RestaurantManagement.dto.dining_session.response.GetDiningSessionDTO;
import com.duong.RestaurantManagement.dto.order.response.GetOrderItemDTO;
import com.duong.RestaurantManagement.exception.DiningSessionNotActiveException;
import com.duong.RestaurantManagement.exception.ResourceNotFoundException;
import com.duong.RestaurantManagement.model.*;
import com.duong.RestaurantManagement.repo.DiningSessionRepo;
import com.duong.RestaurantManagement.repo.OrderItemRepo;
import com.duong.RestaurantManagement.repo.OrderRepo;
import com.duong.RestaurantManagement.repo.RestaurantTableRepo;
import com.duong.RestaurantManagement.service.DiningSessionService;
import com.duong.RestaurantManagement.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiningSessionServiceImp implements DiningSessionService {

    private final DiningSessionRepo diningSessionRepo;
    private final RestaurantTableService restaurantTableService;
    private final RestaurantTableRepo restaurantTableRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;

    @Override
    public boolean checkIfAnyDinningSessionActive() {
        return diningSessionRepo.existsByDiningStatus(DiningStatus.ACTIVE);
    }

    @Override
    public GetDiningSessionDTO getDiningSession(String tableQrToken) {
        RestaurantTable restaurantTable = restaurantTableRepo.findByTableQrCodeValue(tableQrToken)
                .orElseThrow(
                        () -> new ResourceNotFoundException("QrCode not found for table ")
                );
        Optional<DiningSession> diningSessionExist = diningSessionRepo.findByDiningStatusAndRestaurantTable_TableQrCodeValue(DiningStatus.ACTIVE, tableQrToken);
        if (diningSessionExist.isPresent()) {

            return new GetDiningSessionDTO( diningSessionExist.get().getDiningSessionId());
        }

            DiningSession diningSession = DiningSession
                    .builder()
                    .startAt(LocalDateTime.now())
                    .diningStatus(DiningStatus.ACTIVE)
                    .restaurantTable(restaurantTable)
                    .build();
            diningSessionRepo.save(diningSession);
            restaurantTableService.changeTableStatus(restaurantTable);
            return new GetDiningSessionDTO(diningSession.getDiningSessionId());

    }

    @Override
    public DiningSession validateDiningSessionActiveStatus(Long diningSessionId) {
        return diningSessionRepo.findByDiningStatusAndDiningSessionId(DiningStatus.ACTIVE,diningSessionId)
                .orElseThrow(
                        () -> new DiningSessionNotActiveException("Dining Session Not Active")
                );

    }

    @Override
    public void deactiveDinningSession(Long diningSessionId) {
       DiningSession diningSession =  diningSessionRepo.findById(diningSessionId).get();
       diningSession.setDiningStatus(DiningStatus.COMPLETED);
       diningSessionRepo.save(diningSession);
    }

    @Override
    public double getDiningSessionTotalOrderPrice(Long diningSessionId) {
        return orderRepo.getTotalOrderPriceByDiningSessionIdAndStatus(diningSessionId,OrderStatus.COMPLETED);
    }

    @Override
    public List<GetOrderItemDTO> getDiningSessionOrderItems(Long diningSessionId) {
        return orderItemRepo.findByOrder_DiningSession_DiningSessionIdAndOrder_OrderStatus(
                diningSessionId,OrderStatus.COMPLETED)
                .stream()
                .collect(Collectors.groupingBy(
                        orderItem -> orderItem.getFood().getFoodId()
                ))
                .values()
                .stream()
                .map(orderItems -> {
                    int totalQuantity = orderItems.stream()
                            .mapToInt(OrderItem::getQuantity)
                            .sum();
                    double totalPrice = orderItems.stream()
                            .mapToDouble(OrderItem::getTotalPrice)
                            .sum();

                    double price = orderItems.getFirst().getFood().getPrice();
                    return new GetOrderItemDTO(
                            orderItems.getFirst().getFood().getFoodName(),
                            totalQuantity,
                            totalPrice,
                            price
                    );
                }).toList();


    }



}
