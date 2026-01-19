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

    //
    // //
    // // //  metodos Delete
    // //
    //

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Void>borrarProducto(@PathVariable int id) {
        productoServiceImpl.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}

    //
    // //
    // // //  metodos Get
    // //
    //
     /*antes de DTOs
    // http://localhost:8080/producto/obtener
    @GetMapping("/obtener")
    public HashMap<String, Object> obtener_productos(){
        //obtener todas las carreras
        List<Producto> productos = conector_productos.findAll();

        HashMap<String,Object> respuesta= new HashMap<>();
        respuesta.put("Status", 200);
        respuesta.put("mensaje","Datos obtenidos exitosamente");
        respuesta.put("productos",productos);
        return respuesta;
    }
    // http://localhost:8080/producto/obtener/1
    @GetMapping("/obtener/{id}")
    public HashMap<String, Object> obtener_productos_Categoriaid(@PathVariable int id){

        //obtener todas las carreras
        List<Producto> productos = productoServiceImpl.ordenarCategoria(id);

        HashMap<String,Object> respuesta= new HashMap<>();
        respuesta.put("Status", 200);
        respuesta.put("mensaje","Datos obtenidos exitosamente");
        respuesta.put("productos",productos);
        return respuesta;
    }
    // http://localhost:8080/producto/obtener/nombre/
    @GetMapping("/obtener/nombre/{nombre}")
    public HashMap<String, Object> obtener_productosLikeNombre(@PathVariable String nombre){

        //obtener todas las carreras
        List<Producto> productos = productoServiceImpl.ordenarNombre(nombre);

        HashMap<String,Object> respuesta= new HashMap<>();
        respuesta.put("Status", 200);
        respuesta.put("mensaje","Datos obtenidos exitosamente");
        respuesta.put("productos",productos);
        return respuesta;
    }

    // http://localhost:8080/producto/obtener/descripcion/
    @GetMapping("/obtener/descripcion/{descripcion}")
    public HashMap<String, Object> obtener_productosLikeDescripcion(@PathVariable String descripcion){

        //obtener todas las carreras
        List<Producto> productos = productoServiceImpl.ordenarDescripcion(descripcion);

        HashMap<String,Object> respuesta= new HashMap<>();
        respuesta.put("Status", 200);
        respuesta.put("mensaje","Datos obtenidos exitosamente");
        respuesta.put("productos",productos);
        return respuesta;
    }


    //
    // //
    // // //  metodos Post
    // //
    //

    @PostMapping("/añadir")
    public HashMap<String,Object> Añadir_prodiucto(@RequestBody Producto nuevosDatos){

        conector_productos.save(nuevosDatos);

        HashMap<String,Object> respuesta= new HashMap<>();
        respuesta.put("Status", 201);
        respuesta.put("mensaje","Datos Registrados exitosamente");
        respuesta.put("productos",nuevosDatos);
        return respuesta;

    }

    //
    // //
    // // //  metodos Put
    // //
    //

    @PutMapping("/modificar/{id}")
    public HashMap<String,Object>Modificar_producto(@RequestParam int id,@RequestBody Producto nuevosDatos){


        Producto producto_existente = conector_productos.findById(id).orElseThrow();

        producto_existente.setNombre(nuevosDatos.getNombre());
        producto_existente.setDescripcion(nuevosDatos.getDescripcion());
        producto_existente.setPrecio(nuevosDatos.getPrecio());
        producto_existente.setCategoria(nuevosDatos.getCategoria());
        producto_existente.setActualizado(nuevosDatos.getActualizado());
        producto_existente.setCantidadStock(nuevosDatos.getCantidadStock());
        producto_existente.setUsuario(nuevosDatos.getUsuario());


        conector_productos.save(producto_existente);

        HashMap<String,Object> respuesta= new HashMap<>();
        respuesta.put("Status", 201);
        respuesta.put("mensaje","producto modificados exitosamente");
        respuesta.put("productos",producto_existente);
        return respuesta;

    }

    //
    // //
    // // //  metodos Delete
    // //
    //

    @DeleteMapping("/eliminar/{id}")
    public HashMap<String,Object>borrarProducto(@RequestParam int id){

        HashMap<String,Object> respuesta= new HashMap<>();

        if (conector_productos.existsById(id)){
            conector_productos.deleteById(id);
            respuesta.put("Status", 200);
            respuesta.put("Mensaje","producto eliminado exitosamente");
        }else {
            respuesta.put("Status", 404);
            respuesta.put("mensaje","producto no encontrado");
        }

        return respuesta;
    }

}*/
