package com.api.ecommerce.controller;

import com.api.ecommerce.dto.category.CategoryCreateDTO;
import com.api.ecommerce.dto.category.CategoryResponseDTO;
import com.api.ecommerce.dto.category.CategoryUpdateDTO;
import com.api.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    //create
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryCreateDTO dto){
       CategoryResponseDTO created =  service.create(dto);
       return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //read all
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll(){
        List<CategoryResponseDTO> categories = service.getAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //read by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        CategoryResponseDTO category = service.getById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO dto){
        CategoryResponseDTO updated = service.update(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}






