package com.Jesus.Ecommerce.DTOs.Producto;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;

import java.math.BigDecimal;

public record ProductoResponseDTO(
        Integer id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer cantidadStock,
        CategoriaResponseDTO categoria
) {
}
