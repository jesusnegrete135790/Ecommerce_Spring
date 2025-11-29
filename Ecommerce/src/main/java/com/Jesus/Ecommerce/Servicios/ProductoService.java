package com.Jesus.Ecommerce.Servicios;


import com.Jesus.Ecommerce.DTOs.Producto.ProductoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Modelos.Producto;
import jakarta.persistence.criteria.CriteriaBuilder;

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
    List<ProductoResponseDTO> getAllProducts();

    // Actualizar stock
    ProductoResponseDTO actualizarStock(Integer idProducto, int stock);

    // Métodos de filtrado (ahora devuelven DTOs)
    List<ProductoResponseSimpleDTO> ordenarCategoria(Integer idCategoria);
    List<ProductoResponseSimpleDTO> ordenarNombre(String nombre);
    List<ProductoResponseSimpleDTO> ordenarDescripcion(String descripcion);
}
