package com.Jesus.Ecommerce.DTOs.Carrito;

import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseSimpleDTO;

public record CarritoResponseDTO(
        Integer id,
        UsuarioResponseSimpleDTO Usuario) {
}
