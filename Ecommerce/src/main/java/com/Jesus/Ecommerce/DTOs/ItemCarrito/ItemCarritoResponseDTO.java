package com.Jesus.Ecommerce.DTOs.ItemCarrito;

import com.Jesus.Ecommerce.DTOs.Carrito.CarritoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;

public record ItemCarritoResponseDTO(
        Integer id,
        int cantidad,
        CarritoResponseDTO carritoResponseDTO,
        ProductoResponseSimpleDTO productoResponseSimpleDTO) {
}
