package com.Jesus.Ecommerce.Mappers;

import com.Jesus.Ecommerce.DTOs.Producto.ProductoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {


    // Mapeo de Salida (Entidad -> DTO)
    ProductoResponseDTO toDto(Producto producto);

    List<ProductoResponseDTO> toDtoList(List<Producto> productos);

    // 1. Mapeo de Entrada (DTO -> Entidad) para CREAR
    // Ignoramos ID (se genera solo), fechas (se ponen en el servicio) y categoria (la buscamos en el repo)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creado", ignore = true)
    @Mapping(target = "actualizado", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoRegistroDTO dto);

    // 2. Mapeo para ACTUALIZAR (DTO -> Entidad Existente)
    // @MappingTarget actualiza el objeto 'producto' con los datos del 'dto'
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creado", ignore = true)
    @Mapping(target = "actualizado", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateProductFromDto(ProductoRegistroDTO dto, @MappingTarget Producto producto);

    ProductoResponseSimpleDTO toSimpleDto(Producto producto);

    List<ProductoResponseSimpleDTO> toSimpleDtoList(List<Producto> productos);
}
