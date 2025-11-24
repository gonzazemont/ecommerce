package com.api.ecommerce.controller;

import com.api.ecommerce.dto.product.ProductCreateDTO;
import com.api.ecommerce.dto.product.ProductResponseDTO;
import com.api.ecommerce.dto.product.ProductUpdateDTO;
import com.api.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    //create
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductCreateDTO dto){
        ProductResponseDTO created = service.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //read all
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        List<ProductResponseDTO> products = service.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //read by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        ProductResponseDTO product = service.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO dto) {
        ProductResponseDTO updated = service.update(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Additional endpoint: Get products by category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategoryId(@PathVariable Long categoryId) {
        List<ProductResponseDTO> products = service.getByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get products by supplier
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ProductResponseDTO>> getBySupplierId(@PathVariable Long supplierId) {
        List<ProductResponseDTO> products = service.getBySupplier(supplierId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //Get products with low stock
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponseDTO>> getLowStockProducts(@RequestParam(defaultValue = "10") Integer threshold) {
        List<ProductResponseDTO> products = service.getLowStock(threshold);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }










}
