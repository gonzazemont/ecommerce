package com.api.ecommerce.service.impl;

import com.api.ecommerce.dto.supplier.SupplierCreateDTO;
import com.api.ecommerce.dto.supplier.SupplierResponseDTO;
import com.api.ecommerce.dto.supplier.SupplierUpdateDTO;
import com.api.ecommerce.exception.DuplicateResourceExecption;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.mapper.SupplierMapper;
import com.api.ecommerce.model.entity.Supplier;
import com.api.ecommerce.repository.SupplierRepository;
import com.api.ecommerce.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository repository;
    private final SupplierMapper mapper;

    @Override
    public SupplierResponseDTO create(SupplierCreateDTO dto) {
        // verify if supplier with the same name exists
        if (repository.existsByName(dto.getName())) {
            throw new DuplicateResourceExecption("Supplier with name " + dto.getName() + " already exists");
        }
        // verufy if supplier with the same email exists
        if (repository.existsByContactEmail(dto.getContactEmail())) {
            throw new DuplicateResourceExecption("Supplier with email " + dto.getContactEmail() + " already exists");
        }

        Supplier supplier = mapper.toEntity(dto);
        Supplier saved = repository.save(supplier);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponseDTO getById(Long id) {
        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));
        return mapper.toResponseDTO(supplier);
    }

    @Override
    public SupplierResponseDTO update(Long id, SupplierUpdateDTO dto) {
        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Supplier with id " + id + " not found"
                ));

        if (repository.existsByName(dto.getName()) &&
                !supplier.getName().equals(dto.getName())) {
            throw new DuplicateResourceExecption("Supplier with name " + dto.getName() + " already exists");
        }

        if (repository.existsByContactEmail(dto.getContactEmail()) &&
                !supplier.getContactEmail().equals(dto.getContactEmail())) {
            throw new DuplicateResourceExecption(
            "Supplier with email " + dto.getContactEmail() + " already exists");
        }

        mapper.updateEntityFromDTO(supplier, dto);
        Supplier updated = repository.save(supplier);
        return mapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
