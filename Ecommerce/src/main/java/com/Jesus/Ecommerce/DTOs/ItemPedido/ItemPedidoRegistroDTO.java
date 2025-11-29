package com.Jesus.Ecommerce.DTOs.ItemPedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemPedidoRegistroDTO(

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0)
        Integer cantidad,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "El pedido es obligatorio")
        Integer pedidosId,

        @NotNull(message = "El producto es obligatorio")
        Integer ProductoID) {
}
