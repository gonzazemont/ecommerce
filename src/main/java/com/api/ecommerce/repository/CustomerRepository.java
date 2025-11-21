package com.api.ecommerce.repository;

import com.api.ecommerce.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //find customer by email
    Optional<Customer> findByEmail(String email);

    //check if customer exists by email
    boolean existsByEmail(String email);

    //find customer by name and last name
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    //search for customers whose last name starts with a given prefix
    List<Customer> findByLastNameStartingWith(String prefix);
}
