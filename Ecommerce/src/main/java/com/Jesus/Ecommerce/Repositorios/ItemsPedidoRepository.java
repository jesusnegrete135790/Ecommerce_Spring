package com.Jesus.Ecommerce.Repositorios;

import com.Jesus.Ecommerce.Modelos.ItemsPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsPedidoRepository extends JpaRepository<ItemsPedido,Integer> {

    List<ItemsPedido> findByPedidosId(Integer pedidoId);

}
