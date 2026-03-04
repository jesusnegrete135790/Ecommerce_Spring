package com.Jesus.Ecommerce.Servicios;


import com.Jesus.Ecommerce.DTOs.Producto.ProductoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Modelos.Producto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {

    // Crear un producto usando DTO
    ProductoResponseDTO crearProducto(ProductoRegistroDTO dto);

    // Actualizar un producto existente usando DTO
    ProductoResponseDTO actualizarProducto(Integer idProducto, ProductoRegistroDTO dto);

    // Eliminar un producto
    void eliminarProducto(Integer idProducto);

    // Obtener un producto por su ID
    ProductoResponseDTO obtenerProductoPorId(Integer idProducto);

    // Listar todos los productos
    Page<ProductoResponseDTO> getAllProducts(Pageable pageable);

    // Actualizar stock
    ProductoResponseDTO actualizarStock(Integer idProducto, int stock);

    // Métodos de filtrado (ahora devuelven DTOs)
    Page<ProductoResponseSimpleDTO> ordenarCategoria(Integer idCategoria,Pageable pageable);
    Page<ProductoResponseSimpleDTO> ordenarNombre(String nombre,Pageable pageable);
    Page<ProductoResponseSimpleDTO> ordenarDescripcion(String descripcion,Pageable pageable);

    Page<ProductoResponseDTO> obtenerProductosPorUsuario(Integer usuarioId,Pageable pageable);
}
