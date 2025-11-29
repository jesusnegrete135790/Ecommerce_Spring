package com.Jesus.Ecommerce.DTOs.Pedido;

import java.math.BigDecimal;

public record PedidoResponseSimpleDTO(
        Integer id,
        String estado,
        BigDecimal montoTotal) {
}
