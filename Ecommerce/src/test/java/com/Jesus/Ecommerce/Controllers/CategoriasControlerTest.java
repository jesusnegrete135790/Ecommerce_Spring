package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaNoEncontradaExeption;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaPadreInvalida;
import com.Jesus.Ecommerce.Servicios.Impl.CategoriaServiceImpl;
import com.Jesus.Ecommerce.Seguridad.JWT.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriasControler.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoriasControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaServiceImpl categoriaService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;
    @Nested
    @DisplayName("Pruebas para GET /categoria (Listar)")
    class PruebasListar {

        @Test
        @DisplayName("Éxito: Retorna 200 OK y lista de categorías")
        void obtenerCategorias_Exito() throws Exception {
            // Arrange
            List<CategoriaResponseSimpleDTO> listaMock = Arrays.asList(
                    new CategoriaResponseSimpleDTO(1, "Tecnología"),
                    new CategoriaResponseSimpleDTO(2, "Hogar")
            );

            when(categoriaService.obtenerCategorias()).thenReturn(listaMock);

            // Act & Assert
            mockMvc.perform(get("/categoria")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].nombre").value("Tecnología"))
                    .andExpect(jsonPath("$[1].nombre").value("Hogar"));
        }
    }



    @Nested
    @DisplayName("Pruebas para GET /categoria/{id}")
    class PruebasObtenerPorId {

        @Test
        @DisplayName("Fallo: Retorna 404 si el ID no existe")
        void obtenerCategoria_NoEncontrada() throws Exception {
            Integer idNoExiste = 99;
            when(categoriaService.obtenerCategoriasId(idNoExiste))
                    .thenThrow(new CategoriaNoEncontradaExeption("Categoría no encontrada con id: " + idNoExiste));

            mockMvc.perform(get("/categoria/{id}", idNoExiste)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensaje").value("Categoría no encontrada con id: " + idNoExiste));
        }
    }


    @Nested
    @DisplayName("Pruebas para POST /categoria (Crear)")
    class PruebasCrear {

        @Test
        @DisplayName("Éxito: Retorna 201 Created y el objeto creado")
        void anadirCategoria_Exito() throws Exception {
            CategoriaRegistroDTO registroDTO = new CategoriaRegistroDTO("Ropa", "Moda 2026", null);
            CategoriaResponseDTO responseDTO = new CategoriaResponseDTO(1, "Ropa", "Moda 2026", null);

            when(categoriaService.crearCategoria(any(CategoriaRegistroDTO.class))).thenReturn(responseDTO);

            mockMvc.perform(post("/categoria")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registroDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.nombre").value("Ropa"))
                    .andExpect(jsonPath("$.id").value(1));
        }

        @Test
        @DisplayName("Fallo: Retorna 400 Bad Request si faltan datos obligatorios")
        void crearCategoria_DatosInvalidos() throws Exception {
            // DTO inválido: nombre vacío (asumiendo @NotBlank en tu DTO)
            CategoriaRegistroDTO dtoInvalido = new CategoriaRegistroDTO("", "", null);

            mockMvc.perform(post("/categoria")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dtoInvalido)))
                    .andExpect(status().isBadRequest())
                    // Verificamos que GlobalExceptionHandler capturó el error de validación
                    .andExpect(jsonPath("$.mensaje").exists());

            verify(categoriaService, never()).crearCategoria(any());
        }
    }

    @Nested
    @DisplayName("Pruebas para PUT /categoria/{id} (Modificar)")
    class PruebasModificar {

        @Test
        @DisplayName("Éxito: Retorna 200 OK y objeto modificado")
        void modificarCategoria_Exito() throws Exception {
            Integer id = 1;
            CategoriaRegistroDTO updateDTO = new CategoriaRegistroDTO("Ropa Editada", "Desc Editada", null);
            CategoriaResponseDTO responseDTO = new CategoriaResponseDTO(id, "Ropa Editada", "Desc Editada", null);

            when(categoriaService.modificarCategoria(eq(id), any(CategoriaRegistroDTO.class))).thenReturn(responseDTO);

            mockMvc.perform(put("/categoria/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombre").value("Ropa Editada"));
        }

        @Test
        @DisplayName("Fallo: Retorna 400 si la categoría padre es inválida")
        void modificarCategoria_PadreInvalido() throws Exception {
            Integer id = 5;
            // Intentamos asignar el mismo ID como padre
            CategoriaRegistroDTO dto = new CategoriaRegistroDTO("Loop", "Desc", id);

            when(categoriaService.modificarCategoria(eq(id), any()))
                    .thenThrow(new CategoriaPadreInvalida("Una categoría no puede ser su propio padre"));

            mockMvc.perform(put("/categoria/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensaje").value("Una categoría no puede ser su propio padre"));
        }

        @Test
        @DisplayName("Fallo: Retorna 404 si la categoría a editar no existe")
        void modificarCategoria_NoExiste() throws Exception {
            Integer id = 99;
            CategoriaRegistroDTO dto = new CategoriaRegistroDTO("X", "Y", null);

            when(categoriaService.modificarCategoria(eq(id), any()))
                    .thenThrow(new CategoriaNoEncontradaExeption("Categoría no encontrada"));

            mockMvc.perform(put("/categoria/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Pruebas para DELETE /categoria/{id} (Eliminar)")
    class PruebasEliminar {

        @Test
        @DisplayName("Éxito: Retorna 204 No Content")
        void borrarCategoria_Exito() throws Exception {
            Integer id = 1;
            doNothing().when(categoriaService).eliminarCategoria(id);

            mockMvc.perform(delete("/categoria/{id}", id))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Fallo: Retorna 404 si el ID no existe")
        void borrarCategoria_NoExiste() throws Exception {
            Integer id = 99;
            doThrow(new CategoriaNoEncontradaExeption("Categoría no encontrada"))
                    .when(categoriaService).eliminarCategoria(id);

            mockMvc.perform(delete("/categoria/{id}", id))
                    .andExpect(status().isNotFound());
        }
    }
}