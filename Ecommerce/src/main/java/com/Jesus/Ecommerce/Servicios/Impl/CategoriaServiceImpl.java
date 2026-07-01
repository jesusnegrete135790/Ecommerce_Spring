package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaNoEncontradaExeption;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaPadreInvalida;
import com.Jesus.Ecommerce.Mappers.CategoriaMapper;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Repositorios.CategoriasRepository;
import com.Jesus.Ecommerce.Servicios.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private CategoriasRepository categoriasRepository;


    @Override
    @Cacheable(value = "categorias_lista")
    public List<CategoriaResponseSimpleDTO> obtenerCategorias() {
        return categoriaMapper.toSimpleDtoList(categoriasRepository.findAll());
    }

    @Override
    public CategoriaResponseDTO obtenerCategoriasId(Integer id) {
        return  categoriasRepository.findById(id)
                 .map(categoriaMapper::toDto)
                 .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoria no encontrada con id "+ id));
    }

    @Override
    @Transactional
    @CacheEvict(value = "categorias_lista", allEntries = true)
    public CategoriaResponseDTO crearCategoria(CategoriaRegistroDTO dto) {

        Categoria nuevaCategoria = categoriaMapper.toEntity(dto);


        if (dto.categoriaPadreId() != null) {
            Categoria padre = categoriasRepository.findById(dto.categoriaPadreId())
                    .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría padre no encontrada con id: " + dto.categoriaPadreId()));
            nuevaCategoria.setCategoria(padre);
        }

        Categoria guardada = categoriasRepository.save(nuevaCategoria);
        return categoriaMapper.toDto(guardada);
    }

    @Override
    @CacheEvict(value = "categorias_lista", allEntries = true)
    public CategoriaResponseDTO modificarCategoria(Integer id,CategoriaRegistroDTO dto) {

        // 1 Busca la categoria con el id del parametro
        Categoria categoriaExistente = categoriasRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría no encontrada con id: " + id));

        //2 transforma a entidad
        categoriaMapper.updateFromDto(dto, categoriaExistente);

        Optional.ofNullable(dto.categoriaPadreId()).ifPresentOrElse(idPadre ->{
            if (idPadre.equals(id)) {
                throw new CategoriaPadreInvalida("Una categoría no puede ser su propio padre");
            }
            Categoria nuevoPadre = categoriasRepository.findById(idPadre)
                    .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría padre no encontrada"));
                        categoriaExistente.setCategoria(nuevoPadre);}
                ,() -> categoriaExistente.setCategoria(null));


        //4 guarda la entidad actualizada en la bd
        Categoria actualizada = categoriasRepository.save(categoriaExistente);
        return categoriaMapper.toDto(actualizada);
    }

    @Override
    @CacheEvict(value = "categorias_lista", allEntries = true)
    public void eliminarCategoria(Integer id) {
        if (!categoriasRepository.existsById(id)) {
            throw new CategoriaNoEncontradaExeption("Categoría no encontrada con id: " + id);
        }
        categoriasRepository.deleteById(id);
    }
}
