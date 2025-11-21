package com.api.ecommerce.controller;

import com.api.ecommerce.dto.supplier.SupplierCreateDTO;
import com.api.ecommerce.dto.supplier.SupplierResponseDTO;
import com.api.ecommerce.dto.supplier.SupplierUpdateDTO;
import com.api.ecommerce.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    public final SupplierService service;

    //create
    @PostMapping
    public ResponseEntity<SupplierResponseDTO> create(@Valid @RequestBody SupplierCreateDTO dto){
         SupplierResponseDTO created =  service.create(dto);
         return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //read all
    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAll(){
        List<SupplierResponseDTO> suppliers = service.getAll();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    //read by id
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getById(@PathVariable Long id) {
        SupplierResponseDTO supplier = service.getById(id);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SupplierUpdateDTO dto) {
        SupplierResponseDTO updated = service.update(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }














}
