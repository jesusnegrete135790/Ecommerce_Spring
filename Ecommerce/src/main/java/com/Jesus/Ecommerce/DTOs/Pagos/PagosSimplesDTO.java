package com.Jesus.Ecommerce.DTOs.Pagos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagosSimplesDTO(
        Integer id,
        BigDecimal monto,
        String estadoPago,
        Integer pedidoId
) {

}
