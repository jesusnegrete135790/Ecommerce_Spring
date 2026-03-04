package com.Jesus.Ecommerce.Repositorios;

import com.Jesus.Ecommerce.Modelos.Producto;
import com.Jesus.Ecommerce.Modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    Page<Producto> findByCategoriaId(int categoriaId, Pageable pageable);

    Page<Producto>  findByNombreContaining(String nombre,Pageable pageable);

    Page<Producto>  findByDescripcionContaining(String descripcion,Pageable pageable);


    Page<Producto> findByUsuario(Usuario usuario,Pageable pageable);

    Page<Producto> findByUsuarioId(Integer usuarioId,Pageable pageable);

}
