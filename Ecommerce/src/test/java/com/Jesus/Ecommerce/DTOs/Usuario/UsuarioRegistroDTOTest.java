package com.Jesus.Ecommerce.DTOs.Usuario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;



class UsuarioRegistroDTOTest {

    @Test
    void testUsuarioRegistroDTO_DebeAlmacenarValoresCorrectamente() {
        // 1. ARRANGE (Prepara los datos)
        String nombreUsuario = "DevJesus";
        String contrasena = "12345";
        String correo = "jesus@email.com";
        String nombreCompleto = "Jesus Developer";
        String telefono = "55555555";
        String rol = "ADMIN";

        // 2. ACT (Crea el record usando el constructor, NO setters)
        UsuarioRegistroDTO dto = new UsuarioRegistroDTO(
                nombreUsuario,
                contrasena,
                correo,
                nombreCompleto,
                telefono,
                rol
        );


        assertEquals("DevJesus", dto.nombreUsuario());
        assertEquals("jesus@email.com", dto.correoElectronico());
        assertEquals("ADMIN", dto.rol());
    }
}
