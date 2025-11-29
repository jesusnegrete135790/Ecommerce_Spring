package com.Jesus.Ecommerce.DTOs.Carrito;


import jakarta.validation.constraints.NotNull;

public record CarritoRegistroDTO(
        @NotNull(message = "El usuario es obligatoria")
        Integer usuarioID
) {
}
