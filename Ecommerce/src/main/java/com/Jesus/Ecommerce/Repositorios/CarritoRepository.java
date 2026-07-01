package com.Jesus.Ecommerce.Repositorios;

import com.Jesus.Ecommerce.Modelos.Carrito;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito,Integer> {


    Optional<Carrito> findByUsuarioId(Integer usuarioID);

}
