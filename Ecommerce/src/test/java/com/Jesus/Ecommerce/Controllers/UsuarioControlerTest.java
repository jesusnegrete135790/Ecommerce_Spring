package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.UsuarioExeptions.UsuarioNoEncontradoExepcion;
import com.Jesus.Ecommerce.Seguridad.JWT.JwtService;
import com.Jesus.Ecommerce.Servicios.Impl.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioControler.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioServiceImpl usuarioService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;


    private final Integer ID_TEST = 1;
    private final UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(ID_TEST, "jesusDev", "mail@test.com", "Jesus", "555", "ADMIN");
    private final UsuarioRegistroDTO registroDTO = new UsuarioRegistroDTO("jesusDev", "12345", "mail@test.com", "Jesus", "555", "ADMIN");

    @Nested
    @DisplayName("Pruebas para GET /usuario (Listar)")
    class PruebasListar {

        @Test
        @DisplayName("Éxito: Retorna 200 OK y lista de usuarios")
        void obtenerUsuarios_Exito() throws Exception {

            List<UsuarioResponseSimpleDTO> listaMock = Arrays.asList(
                    new UsuarioResponseSimpleDTO(1, "Usuario1"),
                    new UsuarioResponseSimpleDTO(2, "Usuario2")
            );

            when(usuarioService.listarUsuarios()).thenReturn(listaMock);


            mockMvc.perform(get("/usuario")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].nombreUsuario").value("Usuario1"))
                    .andExpect(jsonPath("$[1].nombreUsuario").value("Usuario2"));
        }
    }

    @Nested
    @DisplayName("Pruebas para GET /usuario/{id} (Obtener por ID)")
    class PruebasObtenerId {

        @Test
        @DisplayName("Éxito: Retorna 200 OK y el usuario encontrado")
        void obtenerUsuarioPorId_Exito() throws Exception {
            when(usuarioService.obtenerUsuarioPorId(ID_TEST)).thenReturn(responseDTO);

            mockMvc.perform(get("/usuario/{id}", ID_TEST)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombreUsuario").value("jesusDev"))
                    .andExpect(jsonPath("$.correoElectronico").value("mail@test.com"));
        }

        @Test
        @DisplayName("Fallo: Retorna 404 Not Found si el usuario no existe")
        void obtenerUsuarioPorId_NoEncontrado() throws Exception {
            Integer idNoExiste = 99;
            when(usuarioService.obtenerUsuarioPorId(idNoExiste))
                    .thenThrow(new UsuarioNoEncontradoExepcion("Usuario no encontrado"));

            mockMvc.perform(get("/usuario/{id}", idNoExiste)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Pruebas para POST /usuario (Crear)")
    class PruebasCrear {

        @Test
        @DisplayName("Éxito: Retorna 201 Created y el usuario creado")
        void anadirUsuario_Exito() throws Exception {
            when(usuarioService.registrarUsuario(any(UsuarioRegistroDTO.class))).thenReturn(responseDTO);

            mockMvc.perform(post("/usuario")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registroDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(ID_TEST))
                    .andExpect(jsonPath("$.nombreUsuario").value("jesusDev"));
        }

        @Test
        @DisplayName("Fallo: Retorna 400 Bad Request si faltan datos obligatorios")
        void anadirUsuario_Invalido() throws Exception {
            // DTO inválido (campos vacíos)
            UsuarioRegistroDTO dtoInvalido = new UsuarioRegistroDTO("", "", "", "", "", "");

            mockMvc.perform(post("/usuario")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dtoInvalido)))
                    .andExpect(status().isBadRequest());

            verify(usuarioService, never()).registrarUsuario(any());
        }
    }

    @Nested
    @DisplayName("Pruebas para PUT /usuario/{id} (Modificar)")
    class PruebasModificar {

        @Test
        @DisplayName("Éxito: Retorna 200 OK y usuario modificado")
        void modificarUsuario_Exito() throws Exception {
            when(usuarioService.modificarUsuario(eq(ID_TEST), any(UsuarioRegistroDTO.class))).thenReturn(responseDTO);

            mockMvc.perform(put("/usuario/{id}", ID_TEST)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registroDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombreUsuario").value("jesusDev"));
        }

        @Test
        @DisplayName("Fallo: Retorna 404 Not Found si el usuario a modificar no existe")
        void modificarUsuario_NoEncontrado() throws Exception {
            Integer idNoExiste = 99;
            when(usuarioService.modificarUsuario(eq(idNoExiste), any(UsuarioRegistroDTO.class)))
                    .thenThrow(new UsuarioNoEncontradoExepcion("Usuario no encontrado"));

            mockMvc.perform(put("/usuario/{id}", idNoExiste)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registroDTO)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Pruebas para DELETE /usuario/{id} (Eliminar)")
    class PruebasEliminar {

        @Test
        @DisplayName("Éxito: Retorna 204 No Content")
        void borrarUsuario_Exito() throws Exception {
            doNothing().when(usuarioService).eliminarUsuario(ID_TEST);

            mockMvc.perform(delete("/usuario/{id}", ID_TEST))
                    .andExpect(status().isNoContent());

            verify(usuarioService).eliminarUsuario(ID_TEST);
        }

        @Test
        @DisplayName("Fallo: Retorna 404 Not Found si el usuario a eliminar no existe")
        void borrarUsuario_NoEncontrado() throws Exception {
            Integer idNoExiste = 99;
            doThrow(new UsuarioNoEncontradoExepcion("Usuario no encontrado"))
                    .when(usuarioService).eliminarUsuario(idNoExiste);

            mockMvc.perform(delete("/usuario/{id}", idNoExiste))
                    .andExpect(status().isNotFound());
        }
    }
}