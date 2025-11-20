package com.api.ecommerce.service;

import com.api.ecommerce.dto.customer.CustomerCreateDTO;
import com.api.ecommerce.dto.customer.CustomerResponseDTO;
import com.api.ecommerce.dto.customer.CustomerUpdateDTO;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO create(CustomerCreateDTO dto);

    List<CustomerResponseDTO> getAll();

    CustomerResponseDTO getById(Long id);

    CustomerResponseDTO update(Long id, CustomerUpdateDTO dto);

    void delete(Long id);
}
