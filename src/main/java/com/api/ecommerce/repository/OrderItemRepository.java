package com.api.ecommerce.repository;

import com.api.ecommerce.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemRepository, Long> {

    //seacrh for items by specific order id
    List<OrderItem> findByOrderId(Long orderId);

    //search for items by product id
    List<OrderItem> findByProductId(Long productId);

    //best-selling products
    @Query("SELECT oi.productId, SUM(oi.quantity) as totalQuantity " +
           "FROM OrderItem oi " +
           "GROUP BY oi.productId, oi.product.name " +
           "ORDER BY totalQuantity DESC")
    List<Object[]> findBestSellingProducts();
}
