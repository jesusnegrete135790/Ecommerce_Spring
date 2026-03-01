package com.Jesus.Ecommerce.DTOs.Categoria;
import java.io.Serializable;

public record CategoriaResponseSimpleDTO(
        Integer id,
        String nombre)implements Serializable { }