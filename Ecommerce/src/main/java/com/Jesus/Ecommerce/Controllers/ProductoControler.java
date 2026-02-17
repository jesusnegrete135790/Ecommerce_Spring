package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Producto.ProductoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Producto;
import com.Jesus.Ecommerce.Repositorios.ProductoRepository;
import com.Jesus.Ecommerce.Servicios.Impl.ProductoServiceImpl;
import com.Jesus.Ecommerce.Servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosPorUsuario(@PathVariable Integer id){
        return ResponseEntity.ok(productoServiceImpl.obtenerProductosPorUsuario(id));
    }

    // http://localhost:8080/producto
    @GetMapping("/producto")
    public ResponseEntity<List<ProductoResponseDTO>> obtener_productos() {
        return ResponseEntity.ok(productoServiceImpl.getAllProducts());
    }

    // http://localhost:8080/obtener/1
    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener_productos_Categoriaid(@PathVariable int id) {
        return ResponseEntity.ok(productoServiceImpl.obtenerProductoPorId(id));
    }


    // http://localhost:8080/producto/x obtener/nombre/
    @GetMapping("/producto/nombre/{nombre}")
    public ResponseEntity<List<ProductoResponseSimpleDTO>> obtener_productosLikeNombre(@PathVariable String nombre) {

        return ResponseEntity.ok(productoServiceImpl.ordenarNombre(nombre));
    }

    // http://localhost:8080/producto/obtener/descripcion/
    @GetMapping("/producto/descripcion/{descripcion}")
    public ResponseEntity<List<ProductoResponseSimpleDTO>> obtener_productosLikeDescripcion(@PathVariable String descripcion) {

        return ResponseEntity.ok(productoServiceImpl.ordenarDescripcion(descripcion));
    }
    // http://localhost:8080/producto/obtener/categoria/
    @GetMapping("/producto/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseSimpleDTO>> obtener_productosLikeCategoria(@PathVariable int categoria) {
        return ResponseEntity.ok(productoServiceImpl.ordenarCategoria(categoria));
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
