package com.Jesus.Ecommerce.DTOs.Producto;

import java.math.BigDecimal;
import java.io.Serializable;

public record ProductoResponseSimpleDTO(
        Integer id,
        String nombre,
        BigDecimal precio) implements Serializable { }