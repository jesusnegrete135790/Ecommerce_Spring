package com.Jesus.Ecommerce.DTOs.Pagos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagosResponseDTO(
        Integer id,
        String metodoPago,
        BigDecimal monto,
        String estadoPago,
        LocalDateTime creado,
        Integer pedidoId
) {
}
