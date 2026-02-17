package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoResponseDTO;
import com.Jesus.Ecommerce.Modelos.ItemsCarrito;
import com.Jesus.Ecommerce.Servicios.Impl.ItemCarritoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ItemCarritoControler {


    @Autowired
    private ItemCarritoServiceImpl itemCarritoServiceImpl;


    @GetMapping("/itemcarrito")
    public ResponseEntity<List<ItemCarritoResponseDTO>> obtenerItemCarrito(){
        return ResponseEntity.ok(itemCarritoServiceImpl.getAllItemsProducto());
    }

    @GetMapping("/itemcarrito/carrito/{id}")
    public ResponseEntity<List<ItemCarritoResponseDTO>> obtenerItemsDeUnCarrito(@PathVariable Integer id){
        return ResponseEntity.ok(itemCarritoServiceImpl.obtenerItemsPorCarrito(id));
    }

    @PostMapping("/itemcarrito")
    public ResponseEntity<ItemCarritoResponseDTO> AñadirItemCarrito(@Validated @RequestBody ItemCarritoRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCarritoServiceImpl.añadir(dto));
    }


    @PutMapping("/itemcarrito/{id}")
    public ResponseEntity<ItemCarritoResponseDTO> ModificarItemCarrito(@Validated @PathVariable Integer id,@Validated @RequestBody ItemCarritoRegistroDTO dto) {

        return ResponseEntity.ok(itemCarritoServiceImpl.modificar(id,dto));
    }

    @DeleteMapping("/itemcarrito/{id}")
    public ResponseEntity<Void>borrarItemCarrito(@PathVariable int id){

        itemCarritoServiceImpl.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
