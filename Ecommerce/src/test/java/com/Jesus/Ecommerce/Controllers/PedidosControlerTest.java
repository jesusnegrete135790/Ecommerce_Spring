package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.PedidoExeptions.PedidoNoEncontrado;
import com.Jesus.Ecommerce.Seguridad.JWT.JwtService;
import com.Jesus.Ecommerce.Servicios.Impl.PedidosServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidosControler.class)
@AutoConfigureMockMvc(addFilters = false)
class PedidosControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidosServiceImpl pedidosService;

    @MockitoBean
    private JwtService jwtService;

    private final Integer ID_PEDIDO = 1;
    private final PedidoResponseDTO pedidoDTO = new PedidoResponseDTO(ID_PEDIDO, "Pendiente", BigDecimal.valueOf(100.0), null);
    private final PedidoResponseSimpleDTO simpleDTO = new PedidoResponseSimpleDTO(ID_PEDIDO, "Pendiente", BigDecimal.valueOf(100.0));

    @Nested
    class PruebasGet {

        @Test
        void obtenerPedidos_Exito() throws Exception {
            List<PedidoResponseSimpleDTO> lista = Arrays.asList(simpleDTO);
            when(pedidosService.listarTodosLosPedidos()).thenReturn(lista);

            mockMvc.perform(get("/pedidos"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(1))
                    .andExpect(jsonPath("$[0].estado").value("Pendiente"));
        }

        @Test
        void obtenerPedidosID_Exito() throws Exception {
            when(pedidosService.obtenerPedidoPorId(ID_PEDIDO)).thenReturn(pedidoDTO);

            mockMvc.perform(get("/pedidos/{id}", ID_PEDIDO))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(ID_PEDIDO));
        }

        @Test
        void obtenerPedidosID_NoEncontrado() throws Exception {
            when(pedidosService.obtenerPedidoPorId(99))
                    .thenThrow(new PedidoNoEncontrado("Pedido no encontrado"));

            mockMvc.perform(get("/pedidos/{id}", 99))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class PruebasPost {

        @Test
        void AñadirPedido_Exito() throws Exception {
            Integer usuarioId = 1;
            when(pedidosService.realizarPedido(usuarioId)).thenReturn(pedidoDTO);

            mockMvc.perform(post("/pedidos/{UsuarioId}", usuarioId))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(ID_PEDIDO));
        }
    }

    @Nested
    class PruebasPut {

        @Test
        void ModificarPedidos_Exito() throws Exception {
            String nuevoEstado = "Enviado";
            PedidoResponseDTO actualizado = new PedidoResponseDTO(ID_PEDIDO, nuevoEstado, BigDecimal.valueOf(100.0), null);

            when(pedidosService.actualizarEstado(ID_PEDIDO, nuevoEstado)).thenReturn(actualizado);

            mockMvc.perform(put("/pedidos/{id}/{estado}", ID_PEDIDO, nuevoEstado))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.estado").value(nuevoEstado));
        }
    }

    @Nested
    class PruebasDelete {

        @Test
        void borrarPedido_Exito() throws Exception {
            doNothing().when(pedidosService).cancelarPedido(ID_PEDIDO);

            mockMvc.perform(delete("/pedidos/{id}", ID_PEDIDO))
                    .andExpect(status().isNoContent());
        }

        @Test
        void borrarPedido_NoEncontrado() throws Exception {
            doThrow(new PedidoNoEncontrado("No existe")).when(pedidosService).cancelarPedido(99);

            mockMvc.perform(delete("/pedidos/{id}", 99))
                    .andExpect(status().isNotFound());
        }
    }
}