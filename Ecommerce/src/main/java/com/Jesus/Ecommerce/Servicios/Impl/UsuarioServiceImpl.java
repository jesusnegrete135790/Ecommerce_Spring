package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.UsuarioExeptions.UsuarioNoEncontradoExepcion;
import com.Jesus.Ecommerce.Mappers.UsuarioMapper;
import com.Jesus.Ecommerce.Modelos.Usuario;
import com.Jesus.Ecommerce.Repositorios.UsuarioRepository;
import com.Jesus.Ecommerce.Servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private UsuarioMapper usuarioMapper;

        @Override
        public UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO dto) {

            Usuario usuario = usuarioMapper.toEntity(dto);

            usuario.setContrasena(passwordEncoder.encode(dto.contrasena()));
            usuario.setCreado(LocalDateTime.now());
            usuario.setActualizado(LocalDateTime.now());

            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            return usuarioMapper.toDto(usuarioGuardado);
        }

        @Override
        public UsuarioResponseDTO modificarUsuario(Integer id, UsuarioRegistroDTO dto) {

            Usuario usuarioExistente = usuarioRepository.findById(id)
                    .orElseThrow(() -> new UsuarioNoEncontradoExepcion("Usuario no encontrado con id: " + id));

            usuarioExistente.setNombreUsuario(dto.nombreUsuario());
            usuarioExistente.setCorreoElectronico(dto.correoElectronico());
            usuarioExistente.setNombreCompleto(dto.nombreCompleto());
            usuarioExistente.setTelefono(dto.telefono());
            usuarioExistente.setRol(dto.rol());
            usuarioExistente.setActualizado(LocalDateTime.now());

            if (dto.contrasena() != null && !dto.contrasena().isEmpty()) {
                usuarioExistente.setContrasena(passwordEncoder.encode(dto.contrasena()));
            }

            Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
            return usuarioMapper.toDto(usuarioActualizado);
        }

        @Override
        public UsuarioResponseDTO obtenerUsuarioPorId(Integer id) {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new UsuarioNoEncontradoExepcion("Usuario no encontrado con id: " + id));
            return usuarioMapper.toDto(usuario);
        }

        @Override
        public List<UsuarioResponseSimpleDTO> listarUsuarios() {
            List<Usuario> usuarios = usuarioRepository.findAll();
            return usuarioMapper.toSimpleDtoList(usuarios);
        }

        @Override
        public void eliminarUsuario(Integer id) {
            if (!usuarioRepository.existsById(id)) {
                throw new UsuarioNoEncontradoExepcion("Usuario no encontrado con id: " + id);
            }
            usuarioRepository.deleteById(id);
        }


}
