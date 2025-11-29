package com.Jesus.Ecommerce.Mappers;

import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creado", ignore = true)
    @Mapping(target = "actualizado", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Usuario toEntity(UsuarioRegistroDTO dto);

    UsuarioResponseDTO toDto(Usuario entity);


    List<UsuarioResponseDTO> toDtoList(List<Usuario> entities);

    List<UsuarioResponseSimpleDTO> toSimpleDtoList(List<Usuario> entities);
    UsuarioResponseSimpleDTO toSimpleDto(Usuario usuario);
}
