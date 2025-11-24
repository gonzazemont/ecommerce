package com.api.ecommerce.controller;

import com.api.ecommerce.dto.order.OrderCreateDTO;
import com.api.ecommerce.dto.order.OrderResponseDTO;
import com.api.ecommerce.dto.order.OrderUpdateStatusDTO;
import com.api.ecommerce.model.enums.OrderStatus;
import com.api.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    //create
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderCreateDTO dto){
        OrderResponseDTO created = service.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //read all
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll(){
        List<OrderResponseDTO> orders = service.getAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    //read by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        OrderResponseDTO order = service.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //update status
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderUpdateStatusDTO dto) {
        OrderResponseDTO updated = service.updateStatus(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //additional endpoint: Get orders by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getByCustomerId(@PathVariable Long customerId) {
        List<OrderResponseDTO> orders = service.getByCustomer(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    //get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> getByStatus(@PathVariable OrderStatus status) {
        List<OrderResponseDTO> orders = service.getByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
