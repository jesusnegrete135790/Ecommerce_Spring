package com.Jesus.Ecommerce.DTOs.Producto;

import java.math.BigDecimal;

public record ProductoResponseSimpleDTO(
        Integer id,
        String nombre,
        BigDecimal precio) {
}
