package com.api.ecommerce.service;

import com.api.ecommerce.dto.supplier.SupplierCreateDTO;
import com.api.ecommerce.dto.supplier.SupplierResponseDTO;
import com.api.ecommerce.dto.supplier.SupplierUpdateDTO;

import java.util.List;

public interface SupplierService {
    SupplierResponseDTO create(SupplierCreateDTO dto);

    List<SupplierResponseDTO> getAll();

    SupplierResponseDTO getById(Long id);

    SupplierResponseDTO update(Long id, SupplierUpdateDTO dto);

    void delete(Long id);
}
