package com.Jesus.Ecommerce.DTOs.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UsuarioRegistroDTO(

         @NotBlank(message = "El nombre de usuario es obligatorio")
         String nombreUsuario,

         @NotBlank(message = "La contraseña es obligatorio")
         String contrasena,

         @NotBlank
         @Email
         String correoElectronico,

         @NotBlank(message = "El nombre es obligatorio")
         String nombreCompleto,

         @NotBlank(message = "El numero de telefono es obligatorio")
         String telefono,

         @NotBlank(message = "El rol es obligatorio")
         String rol) {
}
