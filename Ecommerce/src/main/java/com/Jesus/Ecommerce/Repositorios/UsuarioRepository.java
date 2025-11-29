package com.Jesus.Ecommerce.Repositorios;

import com.Jesus.Ecommerce.Modelos.Carrito;
import com.Jesus.Ecommerce.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
