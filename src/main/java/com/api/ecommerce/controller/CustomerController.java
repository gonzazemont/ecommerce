package com.api.ecommerce.controller;

import com.api.ecommerce.dto.customer.CustomerCreateDTO;
import com.api.ecommerce.dto.customer.CustomerResponseDTO;
import com.api.ecommerce.dto.customer.CustomerUpdateDTO;
import com.api.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    public final CustomerService service;

    //create
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerCreateDTO dto){
         CustomerResponseDTO created =  service.create(dto);
         return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //read all
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAll(){
        List<CustomerResponseDTO> customers = service.getAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //read by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        CustomerResponseDTO customer = service.getById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    //update
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDTO dto) {
        CustomerResponseDTO updated = service.update(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }














}
