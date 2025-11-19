package com.api.ecommerce.mapper;

import com.api.ecommerce.dto.category.CategoryCreateDTO;
import com.api.ecommerce.dto.category.CategoryResponseDTO;
import com.api.ecommerce.dto.category.CategoryUpdateDTO;
import com.api.ecommerce.model.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // dto to entity (create)
    public Category toEntity(CategoryCreateDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    // dto to entity (update)
    public void updateEntityFromDTO (Category category, CategoryUpdateDTO dto) {
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
    }

    // entity to dto (response)
    public CategoryResponseDTO toResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
