package com.Jesus.Ecommerce.Mappers;

import com.Jesus.Ecommerce.DTOs.Pagos.PagosRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Pagos.PagosResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pagos.PagosSimplesDTO;
import com.Jesus.Ecommerce.Modelos.Pagos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagosMapper {

    PagosResponseDTO toDto(Pagos entity);

    List<PagosSimplesDTO> toDtoList(List<Pagos>entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creado", ignore = true)
    Pagos toEntity(PagosRegistroDTO dto);


}
