package com.Jesus.Ecommerce.Repositorios;

import com.Jesus.Ecommerce.Modelos.Carrito;
import com.Jesus.Ecommerce.Modelos.ItemsCarrito;
import com.Jesus.Ecommerce.Modelos.Producto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemsCarritoRepository extends JpaRepository<ItemsCarrito,Integer> {


    Optional<ItemsCarrito> findByCarrito_IdAndProducto_Id(Integer IdCarrito, Integer IdProducto);
    List<ItemsCarrito> findByCarrito(Carrito carrito); // <-- Correcto
    List<ItemsCarrito> findByCarritoId(Integer id); // <-- Correcto
    void deleteByCarrito(Carrito carrito);


}
