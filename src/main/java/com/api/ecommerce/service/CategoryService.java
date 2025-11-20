package com.api.ecommerce.service;

import com.api.ecommerce.dto.category.CategoryCreateDTO;
import com.api.ecommerce.dto.category.CategoryResponseDTO;
import com.api.ecommerce.dto.category.CategoryUpdateDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO create(CategoryCreateDTO dto);

    List<CategoryResponseDTO> getAll();

    CategoryResponseDTO getById(Long id);

    CategoryResponseDTO update(Long id, CategoryUpdateDTO dto);

    void delete(Long id);
}
