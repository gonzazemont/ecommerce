package com.api.ecommerce.dto.order;

import com.api.ecommerce.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;

    // customer details
    private Long customerId;
    private String customerName;

    // list of order items
    private java.util.List<OrderItemResponseDTO> items;
}
