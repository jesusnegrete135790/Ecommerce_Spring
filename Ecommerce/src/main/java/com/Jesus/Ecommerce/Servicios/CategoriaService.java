package com.Jesus.Ecommerce.Servicios;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseSimpleDTO> obtenerCategorias();

    CategoriaResponseDTO obtenerCategoriasId(Integer id);

    CategoriaResponseDTO crearCategoria(CategoriaRegistroDTO dto);

    CategoriaResponseDTO modificarCategoria(Integer id,CategoriaRegistroDTO dto);

    void eliminarCategoria(Integer id);
}
