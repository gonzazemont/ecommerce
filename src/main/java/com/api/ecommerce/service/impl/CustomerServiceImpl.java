package com.api.ecommerce.service.impl;

import com.api.ecommerce.dto.customer.CustomerCreateDTO;
import com.api.ecommerce.dto.customer.CustomerResponseDTO;
import com.api.ecommerce.dto.customer.CustomerUpdateDTO;
import com.api.ecommerce.exception.DuplicateResourceExecption;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.mapper.CustomerMapper;
import com.api.ecommerce.model.entity.Customer;
import com.api.ecommerce.repository.CustomerRepository;
import com.api.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    public final CustomerRepository repository;
    public final CustomerMapper mapper;

    @Override
    public CustomerResponseDTO create(CustomerCreateDTO dto) {
        if(repository.existsByEmail(dto.getEmail())){
            throw new DuplicateResourceExecption("Customer with email " + dto.getEmail() + " already exists");
        }

        Customer customer = mapper.toEntity(dto);
        Customer saved = repository.save(customer);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));
        return mapper.toResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO update(Long id, CustomerUpdateDTO dto) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));

        if(!customer.getEmail().equals(dto.getEmail()) && repository.existsByEmail(dto.getEmail())){
            throw new DuplicateResourceExecption("Customer with email " + dto.getEmail() + " already exists");
        }

        mapper.updateEntityFromDTO(customer, dto);
        Customer updated = repository.save(customer);
        return mapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Customer with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
