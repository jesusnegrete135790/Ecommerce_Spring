package com.Jesus.Ecommerce.DTOs.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoriaRegistroDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "La descripcion es obligatorio")
        String descripcion,


        Integer categoriaPadreId
) {

}
