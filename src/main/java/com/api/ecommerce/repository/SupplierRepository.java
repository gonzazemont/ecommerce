package com.api.ecommerce.repository;

import com.api.ecommerce.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    //find supplier by name
    Optional<Supplier> findByName(String name);

    //check if supplier exists by name
    boolean existsByName(String name);

    //find supplier by contact email
    Optional<Supplier> findByContactEmail(String email);

    //check if supplier exists by contact email
    boolean existsByContactEmail(String email);

    //find suppliers by country
    List<Supplier> findByCountry(String country);
}
