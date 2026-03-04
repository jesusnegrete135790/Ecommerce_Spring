package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Producto.ProductoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Producto;
import com.Jesus.Ecommerce.Repositorios.ProductoRepository;
import com.Jesus.Ecommerce.Servicios.Impl.ProductoServiceImpl;
import com.Jesus.Ecommerce.Servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ProductoControler {
//actualizado

    @Autowired
    private ProductoServiceImpl productoServiceImpl;

    @GetMapping("/producto/usuario/{id}")
    public ResponseEntity<Page<ProductoResponseDTO>> obtenerProductosPorUsuario(
            @PathVariable Integer id,
            @PageableDefault(page = 0, size = 20) Pageable pageable){
        return ResponseEntity.ok(productoServiceImpl.obtenerProductosPorUsuario(id,pageable));
    }

    // http://localhost:8080/producto
    @GetMapping("/producto")
    public ResponseEntity<Page<ProductoResponseDTO>> getAllProducts(
            @PageableDefault(page = 0, size = 20) Pageable pageable
    ) {
        Page<ProductoResponseDTO> productos = productoServiceImpl.getAllProducts(pageable);
        return ResponseEntity.ok(productos);
    }

    // http://localhost:8080/obtener/1
    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener_productos_Categoriaid(
            @PathVariable int id){
        return ResponseEntity.ok(productoServiceImpl.obtenerProductoPorId(id));
    }


    // http://localhost:8080/producto/x obtener/nombre/
    @GetMapping("/producto/nombre/{nombre}")
    public ResponseEntity<Page<ProductoResponseSimpleDTO>> obtener_productosLikeNombre(
            @PathVariable String nombre,
            @PageableDefault(page = 0, size = 20) Pageable pageable) {

        return ResponseEntity.ok(productoServiceImpl.ordenarNombre(nombre,pageable));
    }

    // http://localhost:8080/producto/obtener/descripcion/
    @GetMapping("/producto/descripcion/{descripcion}")
    public ResponseEntity<Page<ProductoResponseSimpleDTO>> obtener_productosLikeDescripcion(
            @PathVariable String descripcion,
            @PageableDefault(page = 0, size = 20) Pageable pageable) {

        return ResponseEntity.ok(productoServiceImpl.ordenarDescripcion(descripcion,pageable));
    }
    // http://localhost:8080/producto/obtener/categoria/
    @GetMapping("/producto/categoria/{categoria}")
    public ResponseEntity<Page<ProductoResponseSimpleDTO>> obtener_productosLikeCategoria(
            @PathVariable int categoria,
            @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseEntity.ok(productoServiceImpl.ordenarCategoria(categoria,pageable));
    }


    @PostMapping("/producto")
    public ResponseEntity<ProductoResponseDTO> Añadir_prodiucto(@Validated @RequestBody ProductoRegistroDTO dto) {
         return ResponseEntity.status(HttpStatus.CREATED).body(productoServiceImpl.crearProducto(dto));
    }


    @PutMapping("/producto/{id}")
    public ResponseEntity<ProductoResponseDTO> Modificar_producto(@Validated @PathVariable int id,@Validated  @RequestBody ProductoRegistroDTO dto) {
        return ResponseEntity.ok(productoServiceImpl.actualizarProducto(id, dto));
    }
    @PutMapping("/producto/stock/{id}/{stock}")
    public ResponseEntity<ProductoResponseDTO> ModificarproductoStock(@Validated @PathVariable int id,@Validated  @PathVariable int stock) {

        return ResponseEntity.ok(productoServiceImpl.actualizarStock(id,stock));
    }


    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Void>borrarProducto(@PathVariable int id) {
        productoServiceImpl.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}
