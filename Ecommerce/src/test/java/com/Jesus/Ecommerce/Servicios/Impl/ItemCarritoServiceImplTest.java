package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoResponseDTO;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.ItemCarritoExeptions.ItemCarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.ProductoNoEncontradoExeption;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.StockMenorACero;
import com.Jesus.Ecommerce.Mappers.ItemCarritoMapper;
import com.Jesus.Ecommerce.Modelos.Carrito;
import com.Jesus.Ecommerce.Modelos.ItemsCarrito;
import com.Jesus.Ecommerce.Modelos.Producto;
import com.Jesus.Ecommerce.Repositorios.CarritoRepository;
import com.Jesus.Ecommerce.Repositorios.ItemsCarritoRepository;
import com.Jesus.Ecommerce.Repositorios.ProductoRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCarritoServiceImplTest {

    @Mock
    private ItemsCarritoRepository itemsCarritoRepository;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CarritoRepository carritoRepository;
    @Mock
    private ItemCarritoMapper itemCarritoMapper;

    @InjectMocks
    private ItemCarritoServiceImpl itemCarritoService;

    @Nested
    class PruebasAnadir {

        @Test
        void anadir_NuevoItem_Exito() {
            ItemCarritoRegistroDTO dto = new ItemCarritoRegistroDTO(2, 1, 10);
            Carrito carrito = new Carrito();
            carrito.setId(1);
            Producto producto = new Producto();
            producto.setId(10);
            producto.setCantidadStock(50);
            ItemsCarrito itemEntidad = new ItemsCarrito();
            ItemCarritoResponseDTO responseDTO = new ItemCarritoResponseDTO(1, 2, null, null);

            when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
            when(productoRepository.findById(10)).thenReturn(Optional.of(producto));
            when(itemsCarritoRepository.findByCarrito_IdAndProducto_Id(1, 10)).thenReturn(Optional.empty());
            when(itemCarritoMapper.toEntity(dto)).thenReturn(itemEntidad);
            when(itemsCarritoRepository.save(any(ItemsCarrito.class))).thenReturn(itemEntidad);
            when(itemCarritoMapper.toDto(itemEntidad)).thenReturn(responseDTO);

            ItemCarritoResponseDTO resultado = itemCarritoService.añadir(dto);

            assertNotNull(resultado);
            assertEquals(2, resultado.cantidad());
            verify(itemsCarritoRepository).save(itemEntidad);
        }

        @Test
        void anadir_ItemExistente_SumaCantidad_Exito() {
            ItemCarritoRegistroDTO dto = new ItemCarritoRegistroDTO(2, 1, 10);
            Carrito carrito = new Carrito();
            carrito.setId(1);
            Producto producto = new Producto();
            producto.setId(10);
            producto.setCantidadStock(50);

            ItemsCarrito itemExistente = new ItemsCarrito();
            itemExistente.setCantidad(3);
            itemExistente.setProducto(producto);
            itemExistente.setCarrito(carrito);

            ItemCarritoResponseDTO responseDTO = new ItemCarritoResponseDTO(1, 5, null, null);

            when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
            when(productoRepository.findById(10)).thenReturn(Optional.of(producto));
            when(itemsCarritoRepository.findByCarrito_IdAndProducto_Id(1, 10)).thenReturn(Optional.of(itemExistente));
            when(itemsCarritoRepository.save(itemExistente)).thenReturn(itemExistente);
            when(itemCarritoMapper.toDto(itemExistente)).thenReturn(responseDTO);

            ItemCarritoResponseDTO resultado = itemCarritoService.añadir(dto);

            assertNotNull(resultado);
            assertEquals(5, itemExistente.getCantidad());
            verify(itemsCarritoRepository).save(itemExistente);
        }



        @Test
        void anadir_Fallo_CarritoNoExiste() {
            ItemCarritoRegistroDTO dto = new ItemCarritoRegistroDTO(2, 99, 10);
            when(carritoRepository.findById(99)).thenReturn(Optional.empty());

            assertThrows(CarritoNoEncontrado.class, () -> itemCarritoService.añadir(dto));
        }

        @Test
        void anadir_Fallo_ProductoNoExiste() {
            ItemCarritoRegistroDTO dto = new ItemCarritoRegistroDTO(2, 1, 99);
            when(carritoRepository.findById(1)).thenReturn(Optional.of(new Carrito()));
            when(productoRepository.findById(99)).thenReturn(Optional.empty());

            assertThrows(ProductoNoEncontradoExeption.class, () -> itemCarritoService.añadir(dto));
        }

        @Test
        void anadir_Fallo_CantidadCeroONegativa() {
            ItemCarritoRegistroDTO dto = new ItemCarritoRegistroDTO(0, 1, 10);
            assertThrows(StockMenorACero.class, () -> itemCarritoService.añadir(dto));
        }
    }

    @Nested
    class PruebasModificar {

        @Test
        void modificar_Exito() {
            Integer idItem = 5;
            ItemCarritoRegistroDTO dto = new ItemCarritoRegistroDTO(10, 1, 1);
            Producto producto = new Producto();
            producto.setCantidadStock(20);
            ItemsCarrito item = new ItemsCarrito();
            item.setProducto(producto);

            when(itemsCarritoRepository.findById(idItem)).thenReturn(Optional.of(item));
            when(itemsCarritoRepository.save(item)).thenReturn(item);
            when(itemCarritoMapper.toDto(item)).thenReturn(new ItemCarritoResponseDTO(idItem, 10, null, null));

            ItemCarritoResponseDTO resultado = itemCarritoService.modificar(idItem, dto);

            assertNotNull(resultado);
            verify(itemCarritoMapper).updateFromDto(dto, item);
            verify(itemsCarritoRepository).save(item);
        }

        @Test
        void modificar_Fallo_ItemNoEncontrado() {
            Integer idItem = 99;
            ItemCarritoRegistroDTO dto = new ItemCarritoRegistroDTO(5, 1, 1);
            when(itemsCarritoRepository.findById(idItem)).thenReturn(Optional.empty());

            assertThrows(ItemCarritoNoEncontrado.class, () -> itemCarritoService.modificar(idItem, dto));
        }

        @Test
        void modificar_Fallo_StockInsuficiente() {
            Integer idItem = 5;
            ItemCarritoRegistroDTO dto = new ItemCarritoRegistroDTO(20, 1, 1);
            Producto producto = new Producto();
            producto.setCantidadStock(10);
            ItemsCarrito item = new ItemsCarrito();
            item.setProducto(producto);

            when(itemsCarritoRepository.findById(idItem)).thenReturn(Optional.of(item));

            assertThrows(StockMenorACero.class, () -> itemCarritoService.modificar(idItem, dto));
        }
    }

    @Nested
    class PruebasEliminarYVaciar {

        @Test
        void eliminar_Exito() {
            int id = 1;
            itemCarritoService.eliminar(id);
            verify(itemsCarritoRepository).deleteById(id);
        }

        @Test
        void vaciar_Exito() {
            int idCarrito = 1;
            Carrito carrito = new Carrito();
            when(carritoRepository.findById(idCarrito)).thenReturn(Optional.of(carrito));

            itemCarritoService.vaciar(idCarrito);
            verify(itemsCarritoRepository).deleteByCarrito(carrito);
        }

        @Test
        void vaciar_Fallo_CarritoNoEncontrado() {
            int idCarrito = 99;
            when(carritoRepository.findById(idCarrito)).thenReturn(Optional.empty());

            assertThrows(CarritoNoEncontrado.class, () -> itemCarritoService.vaciar(idCarrito));
        }
    }

    @Nested
    class PruebasListar {

        @Test
        void obtenerItemsPorCarrito_Exito() {
            Integer idCarrito = 1;
            List<ItemsCarrito> listaEntidades = List.of(new ItemsCarrito(), new ItemsCarrito());

            when(itemsCarritoRepository.findByCarritoId(idCarrito)).thenReturn(listaEntidades);
            when(itemCarritoMapper.toDto(any(ItemsCarrito.class))).thenReturn(new ItemCarritoResponseDTO(1, 1, null, null));

            List<ItemCarritoResponseDTO> resultado = itemCarritoService.obtenerItemsPorCarrito(idCarrito);

            assertEquals(2, resultado.size());
            verify(itemsCarritoRepository).findByCarritoId(idCarrito);
        }

        @Test
        void obtenerItemsPorCarrito_Vacio() {
            Integer idCarrito = 1;
            when(itemsCarritoRepository.findByCarritoId(idCarrito)).thenReturn(Collections.emptyList());

            List<ItemCarritoResponseDTO> resultado = itemCarritoService.obtenerItemsPorCarrito(idCarrito);

            assertTrue(resultado.isEmpty());
        }
    }
}