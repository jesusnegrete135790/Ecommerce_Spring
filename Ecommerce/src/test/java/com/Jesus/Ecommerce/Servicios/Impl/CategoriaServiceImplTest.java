package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaNoEncontradaExeption;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaPadreInvalida;
import com.Jesus.Ecommerce.Mappers.CategoriaMapper;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Repositorios.CategoriasRepository;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @Mock
    private CategoriaMapper categoriaMapper;
    @Mock
    private CategoriasRepository categoriasRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Nested
    @DisplayName("Pruebas para obtenerCategorias()")
    class PruebasObtenerCategorias {

        @Test
        @DisplayName("Éxito: Debe retornar una lista de DTOs cuando existen categorías")
        void obtenerCategorias_Exito() {

            Categoria cat1 = new Categoria(1, "Tecnología", "Gadgets", null);
            Categoria cat2 = new Categoria(2, "Hogar", "Muebles", null);
            List<Categoria> listaEntidades = List.of(cat1, cat2);
            CategoriaResponseSimpleDTO dto1 = new CategoriaResponseSimpleDTO(1, "Tecnología");
            CategoriaResponseSimpleDTO dto2 = new CategoriaResponseSimpleDTO(2, "Hogar");
            List<CategoriaResponseSimpleDTO> listaDtos = List.of(dto1, dto2);


            when(categoriasRepository.findAll()).thenReturn(listaEntidades);
            when(categoriaMapper.toSimpleDtoList(listaEntidades)).thenReturn(listaDtos);

            List<CategoriaResponseSimpleDTO> resultado = categoriaService.obtenerCategorias();

            assertNotNull(resultado);
            assertEquals(2, resultado.size());
            assertEquals("Tecnología", resultado.get(0).nombre());

            verify(categoriasRepository).findAll();
        }

        @Test
        @DisplayName("Vacío: Debe retornar lista vacía si no hay categorías")
        void obtenerCategorias_ListaVacia() {

            when(categoriasRepository.findAll()).thenReturn(Collections.emptyList());
            when(categoriaMapper.toSimpleDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

            List<CategoriaResponseSimpleDTO> resultado = categoriaService.obtenerCategorias();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    @Nested
    @DisplayName("Pruebas para obtenerCategoriasId()")
    class PruebasObtenerCategoriasId{

        @Test
        @DisplayName("Exito, debe retornar un CategoriaResponseDTO")
        void obtenerCategoriasId_CuandoIdExiste_DebeRetornarDTO() {
            Integer id = 1;
            Categoria categoria = new Categoria(1, "categoria prueba", "descripcion prueba", null);
            CategoriaResponseDTO categoriaResponseDTO = new CategoriaResponseDTO(1, "categoria prueba dto", "descripcion prueba", null);

            when(categoriasRepository.findById(id)).thenReturn(Optional.of(categoria));
            when(categoriaMapper.toDto(categoria)).thenReturn(categoriaResponseDTO);

            CategoriaResponseDTO resultado = categoriaService.obtenerCategoriasId(id);

            assertNotNull(resultado);
            assertEquals("categoria prueba dto", resultado.nombre());

            verify(categoriasRepository, atLeastOnce()).findById(id);
        }

        @Test
        @DisplayName("Vacio obtenerCategoriasId no se encuentra en la bd")
        void obtenerCategoriasId_vacio() {

            //Arrange
            Integer id = 100;
            when(categoriasRepository.findById(id)).thenReturn(Optional.empty());
            //Act
            CategoriaNoEncontradaExeption exeption = assertThrows(CategoriaNoEncontradaExeption.class,()->{
                categoriaService.obtenerCategoriasId(id);
            });
            //Assert
            assertEquals("Categoría no encontrada con id: 100", exeption.getMessage());
        }
    }

    @Nested
    @DisplayName("Pruebas para crearCategoria()")
    class PruebasCrearCategoria{

        @Test
        @DisplayName("Exito Crear categoria")
        void crearCategoria() {
            // Arrange
            Integer idCategoriaPadre = 1;

            Categoria padre = new Categoria(idCategoriaPadre, "Categoria Padre", "descripcion categoria padre", null);
            Categoria categoria = new Categoria(3, "Categoria Nueva", "descripcion prueba 1", padre);
            CategoriaRegistroDTO dto = new CategoriaRegistroDTO("Categoria Nueva", "Descripcion prueba", idCategoriaPadre);
            CategoriaResponseDTO nuevaCategoriaResponse = new CategoriaResponseDTO(3, "Categoria Nueva", "Descripcion prueba", null);

            when(categoriaMapper.toEntity(dto)).thenReturn(categoria);
            when(categoriasRepository.findById(idCategoriaPadre)).thenReturn(Optional.of(padre));
            when(categoriasRepository.save(categoria)).thenReturn(categoria);
            when(categoriaMapper.toDto(categoria)).thenReturn(nuevaCategoriaResponse);

            CategoriaResponseDTO resultado = categoriaService.crearCategoria(dto);

            assertNotNull(resultado);
            assertEquals("Categoria Nueva", resultado.nombre());

        }

    }

    @Nested
    @DisplayName("Pruebas para modificarCategoria()")
    class PruebasModificar {

        @Test
        @DisplayName("Éxito: Actualiza datos y asigna nuevo padre correctamente")
        void modificar_Exito_ConPadre() {

            Integer idAModificar = 10;
            Integer idNuevoPadre = 20;

            Categoria categoriaViejita = new Categoria(idAModificar, "Nombre Viejo", "Desc Vieja", null);
            Categoria nuevoPadre = new Categoria(idNuevoPadre, "Nuevo Padre", "Desc Padre", null);
            CategoriaRegistroDTO dtoCambios = new CategoriaRegistroDTO("Nombre Nuevo", "Desc Nueva", idNuevoPadre);
            CategoriaResponseDTO respuestaEsperada = new CategoriaResponseDTO(idAModificar, "Nombre Nuevo", "Desc Nueva",null);

            when(categoriasRepository.findById(idAModificar)).thenReturn(Optional.of(categoriaViejita));
            when(categoriasRepository.findById(idNuevoPadre)).thenReturn(Optional.of(nuevoPadre));
            when(categoriasRepository.save(categoriaViejita)).thenReturn(categoriaViejita);
            when(categoriaMapper.toDto(categoriaViejita)).thenReturn(respuestaEsperada);

            CategoriaResponseDTO resultado = categoriaService.modificarCategoria(idAModificar, dtoCambios);

            assertNotNull(resultado);
            assertEquals("Nombre Nuevo", resultado.nombre());

            verify(categoriaMapper).updateFromDto(dtoCambios, categoriaViejita);

            verify(categoriasRepository).save(categoriaViejita);
        }

        @Test
        @DisplayName("Fallo: Lanza excepción si intentas ser tu propio padre")
        void modificar_Fallo_AutoPadre() {

            Integer idConflicto = 5;


            CategoriaRegistroDTO dtoAutoPadre = new CategoriaRegistroDTO("Nombre", "Desc", idConflicto);

            Categoria categoriaExistente = new Categoria(idConflicto, "Nombre", "Desc", null);


            when(categoriasRepository.findById(idConflicto)).thenReturn(Optional.of(categoriaExistente));


            assertThrows(CategoriaPadreInvalida.class, () -> {
                categoriaService.modificarCategoria(idConflicto, dtoAutoPadre);
            });

            verify(categoriasRepository, never()).save(any());
        }

        @Test
        @DisplayName("Fallo: Lanza excepción si la categoría a editar no existe")
        void modificar_Fallo_IdNoExiste() {

            Integer idInexistente = 99;
            CategoriaRegistroDTO dto = new CategoriaRegistroDTO("X", "X", null);

            when(categoriasRepository.findById(idInexistente)).thenReturn(Optional.empty());


            assertThrows(CategoriaNoEncontradaExeption.class, () -> {
                categoriaService.modificarCategoria(idInexistente, dto);
            });
        }

        @Test
        @DisplayName("Fallo: Lanza excepción si el nuevo padre no existe")
        void modificar_Fallo_PadreNoExiste() {

            Integer idAModificar = 10;
            Integer idPadreFantasma = 999;

            Categoria categoriaExistente = new Categoria(idAModificar, "A", "B", null);
            CategoriaRegistroDTO dto = new CategoriaRegistroDTO("A", "B", idPadreFantasma);


            when(categoriasRepository.findById(idAModificar)).thenReturn(Optional.of(categoriaExistente));
            // ...PERO NO encuentra al padre
            when(categoriasRepository.findById(idPadreFantasma)).thenReturn(Optional.empty());


            assertThrows(CategoriaNoEncontradaExeption.class, () -> {
                categoriaService.modificarCategoria(idAModificar, dto);
            });
        }
    }

    @Nested
    @DisplayName("Pruebas para eliminarCategoria()")
    class PruebasEliminar {

        @Test
        @DisplayName("Éxito: Debe eliminar si el ID existe")
        void eliminar_Exito() {

            Integer idAEliminar = 1;

            // Entrenamos al "existsById" para que diga TRUE
            when(categoriasRepository.existsById(idAEliminar)).thenReturn(true);

            categoriaService.eliminarCategoria(idAEliminar);

            verify(categoriasRepository).deleteById(idAEliminar);
        }

        @Test
        @DisplayName("Fallo: Debe lanzar excepción si el ID no existe")
        void eliminar_Fallo_NoExiste() {

            Integer idFantasma = 99;

            when(categoriasRepository.existsById(idFantasma)).thenReturn(false);

            assertThrows(CategoriaNoEncontradaExeption.class, () -> {
                categoriaService.eliminarCategoria(idFantasma);
            });

            verify(categoriasRepository, never()).deleteById(any());
        }
    }
}
