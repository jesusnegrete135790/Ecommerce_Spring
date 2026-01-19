package com.Jesus.Ecommerce.DTOs.ItemCarrito;


import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;


public record ItemCarritoRegistroDTO(

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0)
        int cantidad,

        @NotNull(message = "El carrito es obligatorio")
        Integer carritoID,
        @NotNull(message = "El producto es obligatorio")
        Integer productoID
) {
}
