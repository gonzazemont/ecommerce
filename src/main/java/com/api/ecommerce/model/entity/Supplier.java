package com.api.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Supplier name is required")
    @Column(nullable = false, unique = true)
    @Size(max = 100, message = "Supplier name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Contact email is required")
    @Column(nullable = false, unique = true)
    @Size(max = 100, message = "Contact email must be at most 100 characters")
    private String contactEmail;

    @Column(length = 20)
    @Size(max = 20, message = "Phone number must be at most 20 characters")
    private String phone;

    @NotBlank(message = "Country is required")
    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "Country must be at most 50 characters")
    private String country;
}
