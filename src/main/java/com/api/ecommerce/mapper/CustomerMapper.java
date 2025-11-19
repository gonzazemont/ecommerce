package com.api.ecommerce.mapper;

import com.api.ecommerce.dto.customer.CustomerCreateDTO;
import com.api.ecommerce.dto.customer.CustomerResponseDTO;
import com.api.ecommerce.dto.customer.CustomerUpdateDTO;
import com.api.ecommerce.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    // dto to entity (create)
    public Customer toEntity(CustomerCreateDTO dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        return customer;
    }

    // dto to entity (update)
    public void updateEntityFromDTO (Customer customer, CustomerUpdateDTO dto) {
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
    }

    // entity to dto (response)
    public CustomerResponseDTO toResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        return dto;
    }
}
