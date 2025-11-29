package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaNoEncontradaExeption;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaPadreInvalida;
import com.Jesus.Ecommerce.Mappers.CategoriaMapper;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Repositorios.CategoriasRepository;
import com.Jesus.Ecommerce.Servicios.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private CategoriasRepository categoriasRepository;


    @Override
    public List<CategoriaResponseSimpleDTO> obtenerCategorias() {
        return categoriaMapper.toSimpleDtoList(categoriasRepository.findAll());
    }

    @Override
    public CategoriaResponseDTO obtenerCategoriasId(Integer id) {
        Categoria categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría no encontrada con id: " + id));
        return categoriaMapper.toDto(categoriasRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public CategoriaResponseDTO crearCategoria(CategoriaRegistroDTO dto) {

        Categoria nuevaCategoria = categoriaMapper.toEntity(dto);

        // 2. Asignar Categoría Padre si existe el ID
        if (dto.categoriaPadreId() != null) {
            Categoria padre = categoriasRepository.findById(dto.categoriaPadreId())
                    .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría padre no encontrada con id: " + dto.categoriaPadreId()));
            nuevaCategoria.setCategoria(padre);
        }

        Categoria guardada = categoriasRepository.save(nuevaCategoria);
        return categoriaMapper.toDto(guardada);
    }

    @Override
    public CategoriaResponseDTO modificarCategoria(Integer id,CategoriaRegistroDTO dto) {

        Categoria categoriaExistente = categoriasRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría no encontrada con id: " + id));


        categoriaMapper.updateFromDto(dto, categoriaExistente);

        if (dto.categoriaPadreId() != null) {
            // Validar que no sea padre de sí misma
            if (dto.categoriaPadreId().equals(id)) {
                throw new CategoriaPadreInvalida("Una categoría no puede ser su propio padre");
            }

            Categoria nuevoPadre = categoriasRepository.findById(dto.categoriaPadreId())
                    .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría padre no encontrada"));
            categoriaExistente.setCategoria(nuevoPadre);
        } else {
            categoriaExistente.setCategoria(null);
        }

        Categoria actualizada = categoriasRepository.save(categoriaExistente);
        return categoriaMapper.toDto(actualizada);
    }

    @Override
    public void eliminarCategoria(Integer id) {
        if (!categoriasRepository.existsById(id)) {
            throw new CategoriaNoEncontradaExeption("Categoría no encontrada con id: " + id);
        }
        categoriasRepository.deleteById(id);
    }
}
