package com.Jesus.Ecommerce.Mappers;

import com.Jesus.Ecommerce.DTOs.Carrito.CarritoResponseDTO;
import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoResponseDTO;
import com.Jesus.Ecommerce.Modelos.ItemsCarrito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

// "uses" permite reciclar la lógica de los otros mappers para los objetos anidados
@Mapper(componentModel = "spring", uses = {CarritoMapper.class, ProductoMapper.class})
public interface ItemCarritoMapper {




    @Mapping(source = "carrito", target = "carritoResponseDTO")
    @Mapping(source = "producto", target = "productoResponseSimpleDTO")
    ItemCarritoResponseDTO toDto(ItemsCarrito entity);

    List<ItemCarritoResponseDTO> toListDto(List<ItemsCarrito>entities);

    // Mapeo DTO -> Entidad (Entrada)
    // Ignoramos campos que se llenan en el servicio
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "carrito", ignore = true)  // Lo buscamos por ID en el servicio
    @Mapping(target = "producto", ignore = true) // Lo buscamos por ID en el servicio
    ItemsCarrito toEntity(ItemCarritoRegistroDTO dto);

    // Método para actualizar
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "carrito", ignore = true)
    @Mapping(target = "producto", ignore = true)
    void updateFromDto(ItemCarritoRegistroDTO dto, @MappingTarget ItemsCarrito entity);

}
