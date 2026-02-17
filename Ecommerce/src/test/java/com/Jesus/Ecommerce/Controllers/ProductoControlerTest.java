package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Producto.ProductoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.ProductoNoEncontradoExeption;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.StockMenorACero;
import com.Jesus.Ecommerce.Seguridad.JWT.JwtService;
import com.Jesus.Ecommerce.Servicios.Impl.ProductoServiceImpl;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoControler.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductoControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoServiceImpl productoService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    // Datos de prueba
    private final Integer ID = 1;
    private final ProductoResponseDTO responseDTO = new ProductoResponseDTO(ID, "Laptop", "Gamer", BigDecimal.valueOf(1500.00), 10, null);
    private final ProductoRegistroDTO registroDTO = new ProductoRegistroDTO("Laptop", "Gamer", BigDecimal.valueOf(1500.00), 10, 1);
    private final ProductoResponseSimpleDTO simpleDTO = new ProductoResponseSimpleDTO(ID, "Laptop", BigDecimal.valueOf(1500.00));

    @Nested
    @DisplayName("Pruebas GET (Listados y Búsquedas)")
    class PruebasGet {

        @Test
        @DisplayName("GET /producto: Retorna lista completa")
        void obtenerProductos_Exito() throws Exception {
            when(productoService.getAllProducts()).thenReturn(Arrays.asList(responseDTO));

            mockMvc.perform(get("/producto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(1))
                    .andExpect(jsonPath("$[0].nombre").value("Laptop"));
        }

        @Test
        @DisplayName("GET /producto/{id}: Retorna producto específico")
        void obtenerProductoPorId_Exito() throws Exception {
            when(productoService.obtenerProductoPorId(ID)).thenReturn(responseDTO);

            mockMvc.perform(get("/producto/{id}", ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombre").value("Laptop"));
        }

        @Test
        @DisplayName("GET /producto/{id}: 404 si no existe")
        void obtenerProductoPorId_NoEncontrado() throws Exception {
            when(productoService.obtenerProductoPorId(99))
                    .thenThrow(new ProductoNoEncontradoExeption("Producto no encontrado"));

            mockMvc.perform(get("/producto/{id}", 99))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("GET /producto/usuario/{id}: Productos por usuario")
        void obtenerProductosPorUsuario() throws Exception {
            when(productoService.obtenerProductosPorUsuario(ID)).thenReturn(Arrays.asList(responseDTO));

            mockMvc.perform(get("/producto/usuario/{id}", ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(1));
        }

        @Test
        @DisplayName("GET /producto/nombre/{nombre}: Búsqueda por nombre")
        void obtenerProductosLikeNombre() throws Exception {
            String termino = "Lap";
            when(productoService.ordenarNombre(termino)).thenReturn(Arrays.asList(simpleDTO));

            mockMvc.perform(get("/producto/nombre/{nombre}", termino))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nombre").value("Laptop"));
        }

        @Test
        @DisplayName("GET /producto/descripcion/{descripcion}: Búsqueda por descripción")
        void obtenerProductosLikeDescripcion() throws Exception {
            String termino = "Gamer";
            when(productoService.ordenarDescripcion(termino)).thenReturn(Arrays.asList(simpleDTO));

            mockMvc.perform(get("/producto/descripcion/{descripcion}", termino))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nombre").value("Laptop"));
        }

        @Test
        @DisplayName("GET /producto/categoria/{categoria}: Búsqueda por ID categoría")
        void obtenerProductosLikeCategoria() throws Exception {
            int catId = 5;
            when(productoService.ordenarCategoria(catId)).thenReturn(Arrays.asList(simpleDTO));

            mockMvc.perform(get("/producto/categoria/{categoria}", catId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nombre").value("Laptop"));
        }
    }

    @Nested
    @DisplayName("Pruebas POST (Crear)")
    class PruebasPost {

        @Test
        @DisplayName("POST /producto: Crea producto exitosamente")
        void anadirProducto_Exito() throws Exception {
            when(productoService.crearProducto(any(ProductoRegistroDTO.class))).thenReturn(responseDTO);

            mockMvc.perform(post("/producto")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registroDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.nombre").value("Laptop"));
        }

        @Test
        @DisplayName("POST /producto: 400 si faltan datos")
        void anadirProducto_Invalido() throws Exception {
            ProductoRegistroDTO invalido = new ProductoRegistroDTO("", "", null, -1, null);

            mockMvc.perform(post("/producto")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalido)))
                    .andExpect(status().isBadRequest());

            verify(productoService, never()).crearProducto(any());
        }
    }

    @Nested
    @DisplayName("Pruebas PUT (Modificar)")
    class PruebasPut {

        @Test
        @DisplayName("PUT /producto/{id}: Modifica producto")
        void modificarProducto_Exito() throws Exception {
            when(productoService.actualizarProducto(eq(ID), any(ProductoRegistroDTO.class))).thenReturn(responseDTO);

            mockMvc.perform(put("/producto/{id}", ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registroDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombre").value("Laptop"));
        }

        @Test
        @DisplayName("PUT /producto/stock/{id}/{stock}: Actualiza stock")
        void modificarProductoStock_Exito() throws Exception {
            int nuevoStock = 50;
            // Simulamos que devuelve el DTO con el stock actualizado
            ProductoResponseDTO dtoStock = new ProductoResponseDTO(ID, "Laptop", "Gamer", BigDecimal.valueOf(1500), nuevoStock, null);

            when(productoService.actualizarStock(ID, nuevoStock)).thenReturn(dtoStock);

            mockMvc.perform(put("/producto/stock/{id}/{stock}", ID, nuevoStock))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cantidadStock").value(nuevoStock));
        }

        @Test
        @DisplayName("PUT /producto/stock/{id}/{stock}: 400 si stock negativo")
        void modificarProductoStock_Error() throws Exception {
            int stockNegativo = -5;

            when(productoService.actualizarStock(ID, stockNegativo))
                    .thenThrow(new StockMenorACero("El stock no puede ser negativo"));

            mockMvc.perform(put("/producto/stock/{id}/{stock}", ID, stockNegativo))
                    // Dependiendo de tu GlobalExceptionHandler, esto podría ser 400 o 409.
                    // Asumimos 400 Bad Request o el error que devuelva tu handler.
                    // Si falla, cambia a .isConflict() o lo que corresponda.
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensaje").value("El stock no puede ser negativo"));
        }
    }

    @Nested
    @DisplayName("Pruebas DELETE (Eliminar)")
    class PruebasDelete {

        @Test
        @DisplayName("DELETE /producto/{id}: Elimina producto")
        void borrarProducto_Exito() throws Exception {
            doNothing().when(productoService).eliminarProducto(ID);

            mockMvc.perform(delete("/producto/{id}", ID))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("DELETE /producto/{id}: 404 si no existe")
        void borrarProducto_NoEncontrado() throws Exception {
            doThrow(new ProductoNoEncontradoExeption("No existe"))
                    .when(productoService).eliminarProducto(99);

            mockMvc.perform(delete("/producto/{id}", 99))
                    .andExpect(status().isNotFound());
        }
    }
}