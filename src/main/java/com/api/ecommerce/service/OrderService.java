package com.api.ecommerce.service;

import com.api.ecommerce.dto.order.OrderCreateDTO;
import com.api.ecommerce.dto.order.OrderResponseDTO;
import com.api.ecommerce.dto.order.OrderUpdateStatusDTO;
import com.api.ecommerce.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponseDTO create(OrderCreateDTO dto);
    List<OrderResponseDTO> getAll();
    OrderResponseDTO getById(Long id);
    OrderResponseDTO updateStatus(Long id, OrderUpdateStatusDTO dto);
    void delete(Long id);

    // Additional methods

    List<OrderResponseDTO> getByCustomer(Long customerId);
    List<OrderResponseDTO> getByStatus(OrderStatus status);

}
