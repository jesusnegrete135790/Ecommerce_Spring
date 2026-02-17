package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaNoEncontradaExeption;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.ProductoNoEncontradoExeption;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.StockMenorACero;
import com.Jesus.Ecommerce.Mappers.ProductoMapper;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Modelos.Producto;
import com.Jesus.Ecommerce.Repositorios.CategoriasRepository;
import com.Jesus.Ecommerce.Repositorios.ProductoRepository;
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
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CategoriasRepository categoriasRepository;
    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoServiceImpl productoService;

    // Variables auxiliares para reutilizar
    private final Integer ID_PROD = 1;
    private final Integer ID_CAT = 10;

    // DTOs y Entidades Base
    private final ProductoRegistroDTO registroDTO = new ProductoRegistroDTO("Laptop", "Gamer", BigDecimal.valueOf(1000), 5, ID_CAT);
    private final ProductoResponseDTO responseDTO = new ProductoResponseDTO(ID_PROD, "Laptop", "Gamer", BigDecimal.valueOf(1000), 5, null);
    private final ProductoResponseSimpleDTO simpleDTO = new ProductoResponseSimpleDTO(ID_PROD, "Laptop", BigDecimal.valueOf(1000));
    private final Categoria categoriaMock = new Categoria(ID_CAT, "Tecnologia", "Desc", null);
    private final Producto productoMock = new Producto(ID_PROD, "Laptop", "Gamer", BigDecimal.valueOf(1000), 5, null, null, categoriaMock, null);

    @Nested
    @DisplayName("Pruebas para crearProducto()")
    class PruebasCrear {

        @Test
        @DisplayName("Éxito: Crea producto cuando la categoría existe")
        void crearProducto_Exito() {
            // Arrange
            Producto productoNuevo = new Producto(); // Producto sin ID antes de guardar

            when(categoriasRepository.findById(ID_CAT)).thenReturn(Optional.of(categoriaMock));
            when(productoMapper.toEntity(registroDTO)).thenReturn(productoNuevo);
            when(productoRepository.save(any(Producto.class))).thenReturn(productoMock); // Devuelve producto con ID
            when(productoMapper.toDto(productoMock)).thenReturn(responseDTO);

            // Act
            ProductoResponseDTO resultado = productoService.crearProducto(registroDTO);

            // Assert
            assertNotNull(resultado);
            assertEquals("Laptop", resultado.nombre());
            verify(productoRepository).save(any(Producto.class));
        }

        @Test
        @DisplayName("Fallo: Lanza excepción si la categoría no existe")
        void crearProducto_Fallo_CategoriaNoExiste() {
            // Arrange
            when(categoriasRepository.findById(ID_CAT)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(CategoriaNoEncontradaExeption.class, () -> {
                productoService.crearProducto(registroDTO);
            });
            verify(productoRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Pruebas para actualizarProducto()")
    class PruebasActualizar {

        @Test
        @DisplayName("Éxito: Actualiza producto existente")
        void actualizarProducto_Exito() {
            // Arrange
            when(productoRepository.findById(ID_PROD)).thenReturn(Optional.of(productoMock));
            when(categoriasRepository.findById(ID_CAT)).thenReturn(Optional.of(categoriaMock));
            when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);
            when(productoMapper.toDto(productoMock)).thenReturn(responseDTO);

            // Act
            ProductoResponseDTO resultado = productoService.actualizarProducto(ID_PROD, registroDTO);

            // Assert
            assertNotNull(resultado);
            verify(productoMapper).updateProductFromDto(registroDTO, productoMock);
            verify(productoRepository).save(productoMock);
        }

        @Test
        @DisplayName("Fallo: Producto no encontrado")
        void actualizarProducto_Fallo_ProductoNoExiste() {
            when(productoRepository.findById(ID_PROD)).thenReturn(Optional.empty());

            assertThrows(ProductoNoEncontradoExeption.class, () -> {
                productoService.actualizarProducto(ID_PROD, registroDTO);
            });
        }

        @Test
        @DisplayName("Fallo: Categoría nueva no encontrada")
        void actualizarProducto_Fallo_CategoriaNoExiste() {
            when(productoRepository.findById(ID_PROD)).thenReturn(Optional.of(productoMock));
            when(categoriasRepository.findById(ID_CAT)).thenReturn(Optional.empty());

            assertThrows(CategoriaNoEncontradaExeption.class, () -> {
                productoService.actualizarProducto(ID_PROD, registroDTO);
            });
        }
    }

    @Nested
    @DisplayName("Pruebas para actualizarStock()")
    class PruebasStock {

        @Test
        @DisplayName("Éxito: Actualiza stock correctamente")
        void actualizarStock_Exito() {
            int nuevoStock = 20;
            when(productoRepository.findById(ID_PROD)).thenReturn(Optional.of(productoMock));
            when(productoRepository.save(productoMock)).thenReturn(productoMock);
            when(productoMapper.toDto(productoMock)).thenReturn(responseDTO);

            ProductoResponseDTO resultado = productoService.actualizarStock(ID_PROD, nuevoStock);

            assertNotNull(resultado);
            verify(productoRepository).save(productoMock);
            assertEquals(nuevoStock, productoMock.getCantidadStock()); // Verificamos que se seteó en la entidad
        }

        @Test
        @DisplayName("Fallo: Stock negativo lanza excepción")
        void actualizarStock_Fallo_Negativo() {
            assertThrows(StockMenorACero.class, () -> {
                productoService.actualizarStock(ID_PROD, -5);
            });
            verify(productoRepository, never()).save(any());
        }

        @Test
        @DisplayName("Fallo: Producto no encontrado")
        void actualizarStock_Fallo_ProductoNoExiste() {
            when(productoRepository.findById(ID_PROD)).thenReturn(Optional.empty());

            assertThrows(ProductoNoEncontradoExeption.class, () -> {
                productoService.actualizarStock(ID_PROD, 10);
            });
        }
    }

    @Nested
    @DisplayName("Pruebas para obtenerProductoPorId()")
    class PruebasObtenerId {

        @Test
        @DisplayName("Éxito: Retorna producto")
        void obtenerPorId_Exito() {
            when(productoRepository.findById(ID_PROD)).thenReturn(Optional.of(productoMock));
            when(productoMapper.toDto(productoMock)).thenReturn(responseDTO);

            ProductoResponseDTO resultado = productoService.obtenerProductoPorId(ID_PROD);

            assertNotNull(resultado);
            assertEquals(ID_PROD, resultado.id());
        }

        @Test
        @DisplayName("Fallo: Producto no encontrado")
        void obtenerPorId_Fallo() {
            when(productoRepository.findById(ID_PROD)).thenReturn(Optional.empty());

            assertThrows(ProductoNoEncontradoExeption.class, () -> {
                productoService.obtenerProductoPorId(ID_PROD);
            });
        }
    }

    @Nested
    @DisplayName("Pruebas para eliminarProducto()")
    class PruebasEliminar {

        @Test
        @DisplayName("Éxito: Elimina si existe")
        void eliminar_Exito() {
            when(productoRepository.existsById(ID_PROD)).thenReturn(true);

            productoService.eliminarProducto(ID_PROD);

            verify(productoRepository).deleteById(ID_PROD);
        }

        @Test
        @DisplayName("Fallo: Lanza excepción si no existe")
        void eliminar_Fallo() {
            when(productoRepository.existsById(ID_PROD)).thenReturn(false);

            assertThrows(ProductoNoEncontradoExeption.class, () -> {
                productoService.eliminarProducto(ID_PROD);
            });
            verify(productoRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("Pruebas de Listados y Búsquedas")
    class PruebasListados {

        @Test
        @DisplayName("getAllProducts: Retorna lista")
        void getAllProducts() {
            when(productoRepository.findAll()).thenReturn(List.of(productoMock));
            when(productoMapper.toDtoList(anyList())).thenReturn(List.of(responseDTO));

            List<ProductoResponseDTO> resultado = productoService.getAllProducts();

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
        }

        @Test
        @DisplayName("ordenarCategoria: Retorna lista simple")
        void ordenarCategoria() {
            when(productoRepository.findByCategoriaId(ID_CAT)).thenReturn(List.of(productoMock));
            when(productoMapper.toSimpleDtoList(anyList())).thenReturn(List.of(simpleDTO));

            List<ProductoResponseSimpleDTO> resultado = productoService.ordenarCategoria(ID_CAT);

            assertFalse(resultado.isEmpty());
        }

        @Test
        @DisplayName("ordenarNombre: Busca por nombre")
        void ordenarNombre() {
            String termino = "Lap";
            when(productoRepository.findByNombreContaining(termino)).thenReturn(List.of(productoMock));
            when(productoMapper.toSimpleDtoList(anyList())).thenReturn(List.of(simpleDTO));

            List<ProductoResponseSimpleDTO> resultado = productoService.ordenarNombre(termino);

            assertFalse(resultado.isEmpty());
            verify(productoRepository).findByNombreContaining(termino);
        }

        @Test
        @DisplayName("ordenarDescripcion: Busca por descripción")
        void ordenarDescripcion() {
            String termino = "Gamer";
            when(productoRepository.findByDescripcionContaining(termino)).thenReturn(List.of(productoMock));
            when(productoMapper.toSimpleDtoList(anyList())).thenReturn(List.of(simpleDTO));

            List<ProductoResponseSimpleDTO> resultado = productoService.ordenarDescripcion(termino);

            assertFalse(resultado.isEmpty());
            verify(productoRepository).findByDescripcionContaining(termino);
        }

        @Test
        @DisplayName("obtenerProductosPorUsuario: Retorna lista de productos de un usuario")
        void obtenerProductosPorUsuario() {
            Integer usuarioId = 55;
            when(productoRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(productoMock));
            // Ojo: tu servicio usa stream().map(productoMapper::toDto) en lugar de toDtoList
            when(productoMapper.toDto(productoMock)).thenReturn(responseDTO);

            List<ProductoResponseDTO> resultado = productoService.obtenerProductosPorUsuario(usuarioId);

            assertFalse(resultado.isEmpty());
            verify(productoRepository).findByUsuarioId(usuarioId);
        }
    }
}