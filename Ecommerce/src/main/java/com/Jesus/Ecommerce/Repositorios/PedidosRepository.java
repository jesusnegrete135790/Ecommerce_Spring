package com.Jesus.Ecommerce.Repositorios;

import com.Jesus.Ecommerce.Modelos.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos,Integer> {

    List<Pedidos> findByUsuarioId(Integer id);

}
