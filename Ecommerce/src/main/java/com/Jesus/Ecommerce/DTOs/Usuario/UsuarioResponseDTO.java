package com.Jesus.Ecommerce.DTOs.Usuario;

public record UsuarioResponseDTO(
                                 Integer id,
                                 String nombreUsuario,
                                 String correoElectronico,
                                 String nombreCompleto,
                                 String telefono,
                                 String rol) {
}
