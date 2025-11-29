package com.Jesus.Ecommerce.DTOs.Producto;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductoRegistroDTO(
         @NotBlank(message = "El nombre es obligatorio")
         String nombre,

         @NotBlank(message = "La descripcion es obligatoria")
         String descripcion,

         @NotNull(message = "El precio es obligatorio")
         @Positive(message = "El precio debe ser mayor a 0")
         BigDecimal precio,

         @NotNull(message = "El stock es obligatorio")
         @PositiveOrZero(message = "El stock debe ser igual o mayor que 0")
         Integer cantidadStock,

         @NotNull(message = "La categoria es obligatorio")
         Integer categoriaID
) {
}
