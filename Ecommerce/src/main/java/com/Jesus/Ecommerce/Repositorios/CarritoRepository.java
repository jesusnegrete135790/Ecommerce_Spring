package com.Jesus.Ecommerce.Repositorios;

import com.Jesus.Ecommerce.Modelos.Carrito;
import com.Jesus.Ecommerce.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito,Integer> {


    Optional<Carrito> findByUsuarioId(Integer usuarioID);

}
