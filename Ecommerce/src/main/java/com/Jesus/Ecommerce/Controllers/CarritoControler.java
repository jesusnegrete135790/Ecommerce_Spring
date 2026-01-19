package com.Jesus.Ecommerce.Controllers;


import com.Jesus.Ecommerce.DTOs.Carrito.CarritoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;
import com.Jesus.Ecommerce.Servicios.Impl.CarritoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarritoControler{

    @Autowired
    private CarritoServiceImpl carritoService;

    @GetMapping("/carrito/{id}")
    public ResponseEntity<CarritoResponseDTO> obtenerCategorias(@PathVariable Integer id){
        return ResponseEntity.ok(carritoService.obtenerCarritoPorUsuario(id));
    }

}
