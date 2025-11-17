package com.api.ecommerce.repository;

import com.api.ecommerce.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //find product by category id
    List<Product> findByCategoryId(Long categoryId);

    //find products by supplier id
    List<Product> findBySupplierId(Long supplierId);

    //Search for products with low stock
    List<Product> findByStockLessThan(Integer stock);

    //search for products within a price range
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    //find products by name containing a given keyword
    List<Product> findByNameContainingIgnoreCase(String name);

    //@Query (JPQL)
    @Query("SELECT p FROM Product p WHERE p.stock > 0 ORDER BY p.name")
    List<Product> findAllInStock();

    //Query with named parameters
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.price <= :maxPrice")
    List<Product> findByCategoryNameAndMaxPrice(
            @Param("categoryName") String categoryName,
            @Param("maxPrice") BigDecimal maxPrice
    );
}
