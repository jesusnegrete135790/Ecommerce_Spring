package com.Jesus.Ecommerce.Servicios;

import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseSimpleDTO;

import java.util.List;

public interface UsuarioService {

        UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO dto);

        UsuarioResponseDTO modificarUsuario(Integer id, UsuarioRegistroDTO dto);

        UsuarioResponseDTO obtenerUsuarioPorId(Integer id);

        List<UsuarioResponseSimpleDTO> listarUsuarios();

        void eliminarUsuario(Integer id);
    }

