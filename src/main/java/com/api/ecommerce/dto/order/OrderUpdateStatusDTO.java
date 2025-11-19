package com.api.ecommerce.dto.order;

import com.api.ecommerce.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateStatusDTO {

    @NotNull(message = "Order status is required")
    private OrderStatus status;
}
