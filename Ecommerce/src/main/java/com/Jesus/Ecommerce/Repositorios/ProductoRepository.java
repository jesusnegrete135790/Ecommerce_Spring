package com.Jesus.Ecommerce.Repositorios;

import com.Jesus.Ecommerce.Modelos.Producto;
import com.Jesus.Ecommerce.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    List<Producto> findByCategoriaId(int categoriaId);

    List<Producto>  findByNombreContaining(String nombre);

    List<Producto>  findByDescripcionContaining(String descripcion);


    List<Producto> findByUsuario(Usuario usuario);

}
