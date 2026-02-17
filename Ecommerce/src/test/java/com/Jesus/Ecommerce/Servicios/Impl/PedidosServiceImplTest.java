package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoVacioExeption;
import com.Jesus.Ecommerce.Exepciones.PagoExeptions.PagoRechazadoException;
import com.Jesus.Ecommerce.Exepciones.PedidoExeptions.PedidoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.StockMenorACero;
import com.Jesus.Ecommerce.Exepciones.UsuarioExeptions.UsuarioNoEncontradoExepcion;
import com.Jesus.Ecommerce.Mappers.ItemPedidosMapper;
import com.Jesus.Ecommerce.Mappers.ItemPedidosPersonalizada;
import com.Jesus.Ecommerce.Mappers.PedidoMapper;
import com.Jesus.Ecommerce.Modelos.*;
import com.Jesus.Ecommerce.Repositorios.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidosServiceImplTest {

    @Mock
    private PedidosRepository pedidosRepository;
    @Mock
    private ItemsPedidoRepository itemsPedidoRepository;
    @Mock
    private ItemsCarritoRepository itemsCarritoRepository;
    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private CarritoRepository carritoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ItemPedidosPersonalizada itemPedidosPersonalizada;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private PagoServiceImpl pagoServiceImpl;

    @InjectMocks
    private PedidosServiceImpl pedidosService;

    // Variables auxiliares
    private final Integer ID_USUARIO = 1;
    private final Integer ID_PEDIDO = 100;
    private final Integer ID_CARRITO = 50;
    private final Usuario usuarioMock = new Usuario(ID_USUARIO, "User", "Pass", "mail", "Name", "123", null, null, "USER");
    private final Carrito carritoMock = new Carrito(ID_CARRITO, null, usuarioMock);

    @Nested
    @DisplayName("Pruebas para realizarPedido()")
    class PruebasRealizarPedido {

        @Test
        @DisplayName("Éxito: Realiza pedido, descuenta stock y limpia carrito")
        void realizarPedido_Exito() {
            // Arrange
            // 1. Preparar Productos e Items
            Producto prod1 = new Producto(10, "Prod1", "Desc", BigDecimal.valueOf(100), 50, null, null, null, null);
            ItemsCarrito item1 = new ItemsCarrito(1, 2, carritoMock, prod1); // 2 unidades * 100 = 200
            List<ItemsCarrito> itemsCarrito = List.of(item1);
            BigDecimal totalEsperado = BigDecimal.valueOf(200);

            Pedidos pedidoGuardado = new Pedidos();
            pedidoGuardado.setId(ID_PEDIDO);
            PedidoResponseDTO responseDTO = new PedidoResponseDTO(ID_PEDIDO, "Iniciado", totalEsperado, null);

            // 2. Mocks de Búsqueda
            when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.of(usuarioMock));
            when(carritoRepository.findByUsuarioId(ID_USUARIO)).thenReturn(Optional.of(carritoMock));
            when(itemsCarritoRepository.findByCarrito(carritoMock)).thenReturn(itemsCarrito);

            // 3. Mock Pago y Stock
            when(pagoServiceImpl.PagoBBVA(any(BigDecimal.class))).thenReturn(true);
            when(productoRepository.save(any(Producto.class))).thenReturn(prod1);

            // 4. Mock Guardado Pedido
            when(pedidosRepository.save(any(Pedidos.class))).thenReturn(pedidoGuardado);

            // 5. Mock Conversión Items y Mapper Final
            when(itemPedidosPersonalizada.toEntities(itemsCarrito, ID_PEDIDO)).thenReturn(Collections.emptyList());
            when(pedidoMapper.toDto(pedidoGuardado)).thenReturn(responseDTO);

            // Act
            PedidoResponseDTO resultado = pedidosService.realizarPedido(ID_USUARIO);

            // Assert
            assertNotNull(resultado);
            assertEquals(ID_PEDIDO, resultado.id());

            // Verificaciones Clave
            verify(pagoServiceImpl).PagoBBVA(any(BigDecimal.class)); // Se intentó cobrar
            verify(itemsCarritoRepository).deleteByCarrito(carritoMock); // Se vació el carrito
            verify(pedidosRepository).save(any(Pedidos.class)); // Se guardó el pedido

            // Verificar descuento de stock: Tenía 50, compró 2 -> debe quedar en 48
            // (Como usamos el mismo objeto prod1, verificamos que su estado cambió)
            assertEquals(48, prod1.getCantidadStock());
        }

        @Test
        @DisplayName("Fallo: Usuario no encontrado")
        void realizarPedido_Fallo_Usuario() {
            when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.empty());

            assertThrows(UsuarioNoEncontradoExepcion.class, () -> pedidosService.realizarPedido(ID_USUARIO));
        }

        @Test
        @DisplayName("Fallo: Carrito no encontrado")
        void realizarPedido_Fallo_Carrito() {
            when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.of(usuarioMock));
            when(carritoRepository.findByUsuarioId(ID_USUARIO)).thenReturn(Optional.empty());

            assertThrows(CarritoNoEncontrado.class, () -> pedidosService.realizarPedido(ID_USUARIO));
        }

        @Test
        @DisplayName("Fallo: Carrito vacío")
        void realizarPedido_Fallo_Vacio() {
            when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.of(usuarioMock));
            when(carritoRepository.findByUsuarioId(ID_USUARIO)).thenReturn(Optional.of(carritoMock));
            when(itemsCarritoRepository.findByCarrito(carritoMock)).thenReturn(Collections.emptyList());

            assertThrows(CarritoVacioExeption.class, () -> pedidosService.realizarPedido(ID_USUARIO));
        }

        @Test
        @DisplayName("Fallo: Pago rechazado")
        void realizarPedido_Fallo_Pago() {
            Producto prod1 = new Producto(10, "Prod1", "Desc", BigDecimal.valueOf(100), 50, null, null, null, null);
            ItemsCarrito item1 = new ItemsCarrito(1, 1, carritoMock, prod1);
            List<ItemsCarrito> itemsCarrito = List.of(item1);

            when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.of(usuarioMock));
            when(carritoRepository.findByUsuarioId(ID_USUARIO)).thenReturn(Optional.of(carritoMock));
            when(itemsCarritoRepository.findByCarrito(carritoMock)).thenReturn(itemsCarrito);

            // Simulamos pago rechazado
            when(pagoServiceImpl.PagoBBVA(any(BigDecimal.class))).thenReturn(false);

            assertThrows(PagoRechazadoException.class, () -> pedidosService.realizarPedido(ID_USUARIO));

            verify(pedidosRepository, never()).save(any()); // No se debe guardar pedido
        }

        @Test
        @DisplayName("Fallo: Stock insuficiente")
        void realizarPedido_Fallo_Stock() {
            Producto prod1 = new Producto(10, "Prod1", "Desc", BigDecimal.valueOf(100), 5, null, null, null, null);
            // Intenta comprar 10, pero hay 5
            ItemsCarrito item1 = new ItemsCarrito(1, 10, carritoMock, prod1);
            List<ItemsCarrito> itemsCarrito = List.of(item1);

            when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.of(usuarioMock));
            when(carritoRepository.findByUsuarioId(ID_USUARIO)).thenReturn(Optional.of(carritoMock));
            when(itemsCarritoRepository.findByCarrito(carritoMock)).thenReturn(itemsCarrito);
            when(pagoServiceImpl.PagoBBVA(any(BigDecimal.class))).thenReturn(true);

            assertThrows(StockMenorACero.class, () -> pedidosService.realizarPedido(ID_USUARIO));

            verify(pedidosRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Pruebas para Consultas y Modificaciones")
    class PruebasConsultas {

        @Test
        void obtenerPedidosPorUsuario() {
            when(pedidosRepository.findByUsuarioId(ID_USUARIO)).thenReturn(List.of(new Pedidos()));
            when(pedidoMapper.toSimpleListDto(anyList())).thenReturn(List.of(new PedidoResponseSimpleDTO(1, "A", BigDecimal.TEN)));

            List<PedidoResponseSimpleDTO> res = pedidosService.obtenerPedidosPorUsuario(ID_USUARIO);
            assertFalse(res.isEmpty());
        }

        @Test
        void obtenerPedidoPorId_Exito() {
            Pedidos pedido = new Pedidos();
            when(pedidosRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            when(pedidoMapper.toDto(pedido)).thenReturn(new PedidoResponseDTO(ID_PEDIDO, "A", BigDecimal.TEN, null));

            PedidoResponseDTO res = pedidosService.obtenerPedidoPorId(ID_PEDIDO);
            assertNotNull(res);
        }

        @Test
        void obtenerPedidoPorId_Fallo() {
            when(pedidosRepository.findById(ID_PEDIDO)).thenReturn(Optional.empty());
            assertThrows(PedidoNoEncontrado.class, () -> pedidosService.obtenerPedidoPorId(ID_PEDIDO));
        }

        @Test
        void actualizarEstado_Exito() {
            Pedidos pedido = new Pedidos();
            when(pedidosRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            when(pedidosRepository.save(pedido)).thenReturn(pedido);
            when(pedidoMapper.toDto(pedido)).thenReturn(new PedidoResponseDTO(ID_PEDIDO, "Enviado", BigDecimal.TEN, null));

            PedidoResponseDTO res = pedidosService.actualizarEstado(ID_PEDIDO, "Enviado");

            assertEquals("Enviado", pedido.getEstado());
            verify(pedidosRepository).save(pedido);
        }
    }

    @Nested
    @DisplayName("Pruebas para cancelarPedido()")
    class PruebasCancelar {

        @Test
        @DisplayName("Éxito: Cancela y devuelve stock")
        void cancelarPedido_Exito() {
            // Arrange
            Pedidos pedido = new Pedidos();
            pedido.setId(ID_PEDIDO);

            // Producto con stock 10
            Producto prod = new Producto();
            prod.setId(1);
            prod.setCantidadStock(10);

            // ItemPedido que compró 5 unidades
            ItemsPedido itemPedido = new ItemsPedido();
            itemPedido.setProducto(prod);
            itemPedido.setCantidad(5);

            when(pedidosRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            when(itemsPedidoRepository.findByPedidosId(ID_PEDIDO)).thenReturn(List.of(itemPedido));

            // Act
            pedidosService.cancelarPedido(ID_PEDIDO);

            // Assert
            verify(pedidosRepository).delete(pedido);
            verify(productoRepository).save(prod);

            // Verificamos que el stock se restauró: 10 + 5 = 15
            assertEquals(15, prod.getCantidadStock());
        }

        @Test
        @DisplayName("Fallo: Pedido no existe")
        void cancelarPedido_Fallo() {
            when(pedidosRepository.findById(ID_PEDIDO)).thenReturn(Optional.empty());
            assertThrows(PedidoNoEncontrado.class, () -> pedidosService.cancelarPedido(ID_PEDIDO));
        }
    }
}