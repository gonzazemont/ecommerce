package com.api.ecommerce.service.impl;

import com.api.ecommerce.dto.category.CategoryCreateDTO;
import com.api.ecommerce.dto.category.CategoryResponseDTO;
import com.api.ecommerce.dto.category.CategoryUpdateDTO;
import com.api.ecommerce.exception.DuplicateResourceExecption;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.mapper.CategoryMapper;
import com.api.ecommerce.model.entity.Category;
import com.api.ecommerce.repository.CategoryRepository;
import com.api.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    //create
    @Override
    public CategoryResponseDTO create(CategoryCreateDTO dto) {
        if (repository.existsByName(dto.getName())){
            throw new DuplicateResourceExecption("Category with name "+ dto.getName()+" already exists");
        }

        Category category = mapper.toEntity(dto);
        Category savedCategory = repository.save(category);
        return mapper.toResponseDTO(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id "+ id +" not found"));
        return mapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryUpdateDTO dto) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id "+ id +" not found"));

        if(repository.existsByName(dto.getName()) && !category.getName().equals(dto.getName())){
            throw new DuplicateResourceExecption("Category with name "+ dto.getName()+" already exists");
        }

        mapper.updateEntityFromDTO(category, dto);
        Category updatedCategory = repository.save(category);
        return mapper.toResponseDTO(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Category with id "+ id +" not found");
        }
        repository.deleteById(id);
    }
}
