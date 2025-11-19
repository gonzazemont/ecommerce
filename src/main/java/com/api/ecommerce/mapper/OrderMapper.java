package com.api.ecommerce.mapper;

import com.api.ecommerce.dto.order.OrderCreateDTO;
import com.api.ecommerce.dto.order.OrderItemResponseDTO;
import com.api.ecommerce.dto.order.OrderResponseDTO;
import com.api.ecommerce.model.entity.Order;
import com.api.ecommerce.model.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDTO toOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());

        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());

        List<OrderItemResponseDTO> itemDTOs = order.getOrderItems()
                .stream()
                .map(this::toOrderItemResponseDTO)
                .collect(Collectors.toList());

        dto.setItems(itemDTOs);

        return dto;
    }

    private OrderItemResponseDTO toOrderItemResponseDTO(OrderItem orderItem) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setSubtotal(orderItem.getSubtotal());
        return dto;
    }
}
