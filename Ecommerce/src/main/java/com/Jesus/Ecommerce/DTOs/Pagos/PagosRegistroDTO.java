package com.Jesus.Ecommerce.DTOs.Pagos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagosRegistroDTO(
         @NotBlank(message = "Debes seleccionar un metodo de pago")
         String metodoPago,

         @NotNull(message = "El precio es obligatorio")
         @Positive(message = "El precio debe ser mayor a 0")
         BigDecimal monto,

         @NotBlank(message = "El estado es obligatorio")
         String estadoPago,

         @NotNull(message = "El pago es obligatorio")
         Integer pedidoId
) {
}
