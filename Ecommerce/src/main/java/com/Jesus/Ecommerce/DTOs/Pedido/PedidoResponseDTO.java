package com.Jesus.Ecommerce.DTOs.Pedido;

import com.Jesus.Ecommerce.Modelos.Usuario;

import java.math.BigDecimal;

public record PedidoResponseDTO(
        Integer id,
        String estado,
        BigDecimal montoTotal,
        Usuario Usuario) {

}
