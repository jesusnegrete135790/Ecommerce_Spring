package com.Jesus.Ecommerce.DTOs.Categoria;

public record CategoriaResponseDTO(
        Integer id,
        String nombre,
        String descripcion,
        CategoriaResponseSimpleDTO categoriaPadre) {
}
