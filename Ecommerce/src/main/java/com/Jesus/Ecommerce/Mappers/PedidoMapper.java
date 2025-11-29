package com.Jesus.Ecommerce.Mappers;


import com.Jesus.Ecommerce.DTOs.Pedido.PedidoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseSimpleDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioRegistroDTO;
import com.Jesus.Ecommerce.Modelos.Pedidos;
import com.Jesus.Ecommerce.Modelos.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedidos toEntity(PedidoRegistroDTO dto);

    PedidoResponseDTO toDto(Pedidos dto);

    List<PedidoResponseDTO> toListDto(List<Pedidos> dtos);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "creado", ignore = true)
    @Mapping(target = "actualizado", ignore = true)
    Pedidos toEntity (PedidoResponseDTO entity);

    PedidoResponseSimpleDTO toSimpleDto(Pedidos pedidos);



    List<PedidoResponseSimpleDTO> toSimpleListDto(List<Pedidos> dtos);
}
