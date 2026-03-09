package com.Jesus.Ecommerce.Mappers;


import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    @Mapping(source = "categoria", target = "categoriaPadre")
    CategoriaResponseDTO toDto(Categoria entity);

    List<CategoriaResponseDTO> toDtoList(List<Categoria> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Categoria toEntity(CategoriaRegistroDTO dto);

    // DTO -> Entidad (Actualizar)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateFromDto(CategoriaRegistroDTO dto, @MappingTarget Categoria entity);

    // Mapeo de apoyo (MapStruct lo usará automáticamente para el campo 'categoria')
    List<CategoriaResponseSimpleDTO> toSimpleDtoList(List<Categoria> entities);
    CategoriaResponseSimpleDTO toResumenDto(Categoria categoria);
}
