package com.api.ecommerce.service.impl;

import com.api.ecommerce.dto.order.OrderCreateDTO;
import com.api.ecommerce.dto.order.OrderItemDTO;
import com.api.ecommerce.dto.order.OrderResponseDTO;
import com.api.ecommerce.dto.order.OrderUpdateStatusDTO;
import com.api.ecommerce.exception.BadRequestException;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.mapper.OrderMapper;
import com.api.ecommerce.model.entity.Customer;
import com.api.ecommerce.model.entity.Order;
import com.api.ecommerce.model.entity.OrderItem;
import com.api.ecommerce.model.entity.Product;
import com.api.ecommerce.model.enums.OrderStatus;
import com.api.ecommerce.repository.CustomerRepository;
import com.api.ecommerce.repository.OrderRepository;
import com.api.ecommerce.repository.ProductRepository;
import com.api.ecommerce.service.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseDTO create(OrderCreateDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found with id: " + dto.getCustomerId()));

        //Create the order
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.ZERO);

        //Process each item
        BigDecimal total = BigDecimal.ZERO;

        for(OrderItemDTO itemDTO : dto.getItems()){
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + itemDTO.getProductId()));

            if(product.getStock() < itemDTO.getQuantity()){
                throw new BadRequestException("Insufficient stock for product: " + product.getName() + ". Available: " + product.getStock() + ", Requested: " + itemDTO.getQuantity());
            }

            //Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setUnitPrice(product.getPrice());

            order.addOrderItem(orderItem);

            product.setStock(product.getStock()- itemDTO.getQuantity());
            productRepository.save(product);

            BigDecimal subtotal = orderItem.getUnitPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            total = total.add(subtotal);
        }

        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponseDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO updateStatus(Long id, OrderUpdateStatusDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found with id: " + id));

        validateStatusTransition(order.getStatus(), dto.getStatus());

        order.setStatus(dto.getStatus());
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponseDTO(updatedOrder);
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        switch (currentStatus){
            case PENDING:
                if (newStatus != OrderStatus.CONFIRMED && newStatus != OrderStatus.CANCELED) {
                    throw new BadRequestException(
                            "You can only change from PENDING to CONFIRMED or CANCELLED."
                    );
                }
                break;
            case CONFIRMED:
                if (newStatus != OrderStatus.SHIPPED && newStatus != OrderStatus.CANCELED) {
                    throw new BadRequestException(
                            "You can only change from CONFIRMED to SHIPPED or CANCELLED."
                    );
                }
                break;
            case SHIPPED:
                if (newStatus != OrderStatus.DELIVERED) {
                    throw new BadRequestException(
                            " You can only change from SHIPPED to DELIVERED."
                    );
                }
                break;
            case DELIVERED:
                throw new BadRequestException(
                        "You cannot change the status of an order that has already been DELIVERED."
                );
            case CANCELED:
                throw new BadRequestException(
                        " You cannot change the status of an order that has been CANCELED."
                );
        }
    }

    @Override
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found with id: " + id));

        if(order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CANCELED){
            throw new BadRequestException("Only orders with PENDING or CANCELED status can be deleted.");
        }

        for(OrderItem item : order.getOrderItems()){
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        orderRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getByCustomer(Long customerId) {
        if(!customerRepository.existsById(customerId)){
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }

        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(orderMapper::toOrderResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(orderMapper::toOrderResponseDTO)
                .toList();
    }
}
