package com.api.ecommerce.service;

import com.api.ecommerce.dto.product.ProductCreateDTO;
import com.api.ecommerce.dto.product.ProductResponseDTO;
import com.api.ecommerce.dto.product.ProductUpdateDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO create(ProductCreateDTO dto);
    List<ProductResponseDTO> getAll();
    ProductResponseDTO getById(Long id);
    ProductResponseDTO update(Long id, ProductUpdateDTO dto);
    void delete(Long id);

    // additional methods
    List<ProductResponseDTO> getByCategory(Long categoryId);
    List<ProductResponseDTO> getBySupplier(Long supplierId);
    List<ProductResponseDTO> getLowStock(Integer threshold);
}
