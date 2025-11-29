package com.Jesus.Ecommerce.Mappers;

import com.Jesus.Ecommerce.DTOs.Carrito.CarritoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Carrito.CarritoResponseDTO;
import com.Jesus.Ecommerce.Modelos.Carrito;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarritoMapper {

    CarritoResponseDTO toDto(Carrito entity);

    List<CarritoResponseDTO> toDtoList(List<Carrito>entities);

    Carrito toEntity(CarritoRegistroDTO dto);


}
