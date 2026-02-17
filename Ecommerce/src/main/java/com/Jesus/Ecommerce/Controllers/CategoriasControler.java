package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseDTO;
import com.Jesus.Ecommerce.DTOs.Categoria.CategoriaResponseSimpleDTO;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Repositorios.CategoriasRepository;
import com.Jesus.Ecommerce.Servicios.Impl.CategoriaServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class CategoriasControler {


    @Autowired
    private CategoriaServiceImpl categoriaServiceimpl;

    @GetMapping("/categoria")
    public ResponseEntity<List<CategoriaResponseSimpleDTO>> obtenerCategorias(){
        return ResponseEntity.ok(categoriaServiceimpl.obtenerCategorias()) ;
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategorias(@PathVariable Integer id){
        return ResponseEntity.ok(categoriaServiceimpl.obtenerCategoriasId(id)) ;
    }

    @PostMapping("/categoria")
    public ResponseEntity<CategoriaResponseDTO> AñadirCategoria(@Validated @RequestBody CategoriaRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaServiceimpl.crearCategoria(dto));
    }

    @PutMapping("/categoria/{id}")
    public ResponseEntity<CategoriaResponseDTO>ModificarCategoria(@Validated @PathVariable int id,@Validated @RequestBody CategoriaRegistroDTO dto){
        return ResponseEntity.ok(categoriaServiceimpl.modificarCategoria(id,dto));
    }


    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<Void> borrarCategoria(@PathVariable int id){
        categoriaServiceimpl.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
