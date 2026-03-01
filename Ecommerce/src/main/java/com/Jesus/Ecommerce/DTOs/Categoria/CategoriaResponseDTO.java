package com.Jesus.Ecommerce.DTOs.Categoria;


import java.io.Serializable;

public record CategoriaResponseDTO(
        Integer id,
        String nombre,
        String descripcion,
        CategoriaResponseSimpleDTO categoriaPadre)implements Serializable { }