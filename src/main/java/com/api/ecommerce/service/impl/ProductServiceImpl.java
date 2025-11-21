package com.api.ecommerce.service.impl;

import com.api.ecommerce.dto.product.ProductCreateDTO;
import com.api.ecommerce.dto.product.ProductResponseDTO;
import com.api.ecommerce.dto.product.ProductUpdateDTO;
import com.api.ecommerce.exception.BadRequestException;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.mapper.ProductMapper;
import com.api.ecommerce.model.entity.Category;
import com.api.ecommerce.model.entity.Product;
import com.api.ecommerce.model.entity.Supplier;
import com.api.ecommerce.repository.CategoryRepository;
import com.api.ecommerce.repository.ProductRepository;
import com.api.ecommerce.repository.SupplierRepository;
import com.api.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public ProductResponseDTO create(ProductCreateDTO dto){
        //validate category and supplier existence
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(()-> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));

        //business validations
        validateProductData(dto.getPrice(), dto.getStock());

        //mapping DTO to entity
        Product product = mapper.toEntity(dto, category, supplier);
        Product savedProduct = productRepository.save(product);

        return mapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAll(){
        return productRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(()-> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));

        validateProductData(dto.getPrice(), dto.getStock());

        mapper.updateEntityFromDTO(product, dto, category, supplier);
        Product updatedProduct = productRepository.save(product);

        return mapper.toResponseDTO(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getByCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getBySupplier(Long supplierId) {
        if(!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + supplierId);
        }

        return productRepository.findBySupplierId(supplierId)
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getLowStock(Integer threshold) {
        if(threshold < 0) {
            throw new BadRequestException("Threshold cannot be negative.");
        }

        return productRepository.findByStockLessThan(threshold)
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    //Private method for reusable validations
    private void validateProductData(BigDecimal price, Integer stock) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("The price must be greater than 0.");
        }

        if (stock < 0) {
            throw new BadRequestException("Stock cannot be negative.");
        }
    }
}















