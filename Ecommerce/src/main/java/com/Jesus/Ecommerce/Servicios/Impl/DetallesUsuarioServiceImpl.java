package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.Modelos.Usuario;
import com.Jesus.Ecommerce.Repositorios.UsuarioRepository;
// No importes @Service o @Autowired
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service; // <-- ELIMINA O COMENTA ESTA LÍNEA

// SIN anotaotación @Service
public class DetallesUsuarioServiceImpl implements UserDetailsService {

    // ELIMINA @Autowired
    private UsuarioRepository usuarioRepository;

    // AÑADE ESTE CONSTRUCTOR
    public DetallesUsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con nombre: " + username));
    }
}