package com.api.ecommerce.repository;

import com.api.ecommerce.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    //seacrh for items by specific order id
    List<OrderItem> findByOrder_Id(Long orderId);

    //search for items by product id
    List<OrderItem> findByProduct_Id(Long productId);

    //best-selling products
    @Query("SELECT oi.product.id, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product.id, oi.product.name " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findBestSellingProducts();

}
