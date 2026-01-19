package com.Jesus.Ecommerce.DTOs.Login;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "El usuario es obligatorio")
        String nombreUsuario,
        @NotBlank(message = "La contraseña es obligatoria")
        String contrasena
) {}
