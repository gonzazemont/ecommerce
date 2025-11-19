package com.api.ecommerce.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierUpdateDTO {

    @NotBlank(message = "Supplier name is required")
    @Size(max = 100, message = "Supplier name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Contact email is required")
    @Size(max = 100, message = "Contact email must be at most 100 characters")
    @Email(message = "Contact email must be a valid email address")
    private String contactEmail;

    @Size(max = 20, message = "Phone number must be at most 20 characters")
    private String phone;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country must be at most 50 characters")
    private String country;
}
