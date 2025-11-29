package com.Jesus.Ecommerce.DTOs.Pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record PedidoRegistroDTO(
         @NotBlank(message = "El estado es obligatorio")
         String estado,

         @NotNull(message = "El monto total es obligatorio")
         @Positive(message = "El monto tiene que ser mayor a 0")
         BigDecimal montoTotal,

         @NotNull(message = "El usuario es obligatorio")
         Integer UsuarioId) {
}
