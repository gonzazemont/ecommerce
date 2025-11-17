package com.api.ecommerce.repository;

import com.api.ecommerce.model.entity.Order;
import com.api.ecommerce.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // search for orders by customer id
    List<Order> findByCustomerId(Long customerId);

    //search for orders by status
    List<Order> findByStatus(OrderStatus status);

    //search for orders from a customer with a specific status
    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);

    //search for orders within a range date
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Search for a customer's recent orders (sorted by descending date)
    List<Order> findByCustomerIdOrderByOrderDateDesc(Long customerId);

    //count orders by status
    long countByStatus(OrderStatus status);

    // Custom query: orders with more than X items
    @Query("SELECT o FROM Order o WHERE SIZE(o.orderItems) > :minItems")
    List<Order> findOrdersWithMoreThanItems(int minItems);
}
