package com.api.ecommerce.mapper;

import com.api.ecommerce.dto.supplier.SupplierCreateDTO;
import com.api.ecommerce.dto.supplier.SupplierResponseDTO;
import com.api.ecommerce.dto.supplier.SupplierUpdateDTO;
import com.api.ecommerce.model.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    // dto to entity (create)
    public Supplier toEntity(SupplierCreateDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setContactEmail(dto.getContactEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setCountry(dto.getCountry());
        return supplier;
    }

    // dto to entity (update)
    public void updateEntityFromDTO (Supplier supplier, SupplierUpdateDTO dto) {
        supplier.setName(dto.getName());
        supplier.setContactEmail(dto.getContactEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setCountry(dto.getCountry());
    }

    // entity to dto (response)
    public SupplierResponseDTO toResponseDTO(Supplier supplier) {
        SupplierResponseDTO dto = new SupplierResponseDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setContactEmail(supplier.getContactEmail());
        dto.setPhone(supplier.getPhone());
        dto.setCountry(supplier.getCountry());
        return dto;
    }
}
