package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Repositorios.CategoriasRepository;
import com.Jesus.Ecommerce.Servicios.Impl.CategoriaServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class CategoriasControler {


    @Autowired
    private CategoriaServiceImpl categoriaServiceimpl;

    //
    // //
    // // //  metodos Get
    // //
    //

    @GetMapping("/categoria")
    public ResponseEntity<List<CategoriaResponseSimpleDTO>> obtenerCategorias(){
        return ResponseEntity.ok(categoriaServiceimpl.obtenerCategorias()) ;
    }



    //
    // //
    // // //  metodos Post
    // //
    //

    @PostMapping("/categoria")
    public ResponseEntity<CategoriaResponseDTO> AñadirCategoria(@Validated @RequestBody CategoriaRegistroDTO dto) {
        return ResponseEntity.ok(categoriaServiceimpl.crearCategoria(dto));
    }

    //
    // //
    // // //  metodos Put
    // //
    //

    @PutMapping("/categoria/{id}")
    public ResponseEntity<CategoriaResponseDTO>ModificarCategoria(@Validated @RequestParam int id,@Validated @RequestBody CategoriaRegistroDTO dto){
        return ResponseEntity.ok(categoriaServiceimpl.modificarCategoria(id,dto));
    }

    //
    // //
    // // //  metodos Delete
    // //
    //
    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<Void> borrarCategoria(@RequestParam int id){
        categoriaServiceimpl.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
