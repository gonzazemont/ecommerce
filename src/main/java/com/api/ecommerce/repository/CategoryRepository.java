package com.api.ecommerce.repository;

import com.api.ecommerce.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    //find category by name
    Optional<Category> findByName(String name);

    //check if category exists by name
    boolean existsByName(String name);
}
