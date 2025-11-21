package com.api.ecommerce.mapper;

import com.api.ecommerce.dto.product.ProductCreateDTO;
import com.api.ecommerce.dto.product.ProductResponseDTO;
import com.api.ecommerce.dto.product.ProductUpdateDTO;
import com.api.ecommerce.model.entity.Category;
import com.api.ecommerce.model.entity.Product;
import com.api.ecommerce.model.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // dto to entity (create)
    public Product toEntity(ProductCreateDTO dto, Category category, Supplier supplier) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);
        product.setSupplier(supplier);
        return product;
    }

    // dto to entity (update)
    public void updateEntityFromDTO (Product product, ProductUpdateDTO dto, Category category, Supplier supplier) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);
        product.setSupplier(supplier);
    }

    // entity to dto (response)
    public ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory().getId());
        dto.setSupplierId(product.getSupplier().getId());
        return dto;
    }
}
