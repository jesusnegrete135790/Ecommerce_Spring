package com.Jesus.Ecommerce.DTOs.ItemPedido;

import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseSimpleDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Integer id,
        Integer cantidad,
        BigDecimal precio,
        PedidoResponseSimpleDTO pedidos,
        ProductoResponseSimpleDTO Producto) {
}



