package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseSimpleDTO;
import com.Jesus.Ecommerce.Servicios.Impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioControler {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    //actualizado

    @GetMapping("/usuario")
    public ResponseEntity<List<UsuarioResponseSimpleDTO>> obtener_usuario(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtener_usuario_id(@PathVariable int id){
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    //
    // //
    // // //  metodos Post
    // //
    //


    @PostMapping("/usuario")
    public ResponseEntity<UsuarioResponseDTO> AñadirUsuario(@Validated @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }


    @PutMapping("/usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO>ModificarUsuario(@Validated @PathVariable int id,@Validated @RequestBody UsuarioRegistroDTO dto){
        return ResponseEntity.ok(usuarioService.modificarUsuario(id,dto));
    }

    //
    // //
    // // //  metodos Delete
    // //
    //

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> borrarUsuario(@PathVariable int id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /*
}
//antes de DTOs
@GetMapping("/obtener1")
public HashMap<String, Object> obtener_usuario1(){
    //obtener todas las carreras
    List<Usuario> Usuario = usuarioRepository.findAll();

    HashMap<String,Object> respuesta= new HashMap<>();
    respuesta.put("Status", 200);
    respuesta.put("mensaje","Datos obtenidos exitosamente");
    respuesta.put("Usuario",Usuario);
    return respuesta;
}

//
// //
// // //  metodos Post
// //
//


@PostMapping("/añadir")
public ResponseEntity<Void> AñadirUsuario(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {

    usuarioService.registrarUsuario(usuarioRegistroDTO);

    return ResponseEntity.ok().build();
}

    /*
    @PostMapping("/añadir1")
    public HashMap<String,Object> AñadirUsuario1(@RequestBody Usuario nuevosDatos) {

        nuevosDatos.setContrasena(passwordEncoder.encode(nuevosDatos.getContrasena()));

        usuarioRepository.save(nuevosDatos);

        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("Status", 201);
        respuesta.put("mensaje", "Datos Registrados exitosamente");
        respuesta.put("productos", nuevosDatos); // Cuidado: no devuelvas la contraseña
        return respuesta;
    }



@PutMapping("/modificar/{id}")
public HashMap<String,Object>ModificarUsuario(@RequestParam int id,@RequestBody UsuarioRegistroDTO usuarioRegistroDTO){

    Usuario usuario_existente = usuarioRepository.findById(id).orElseThrow();

    usuario_existente.setNombreUsuario(nuevosDatos.getNombreUsuario());

    if (nuevosDatos.getContrasena() != null && !nuevosDatos.getContrasena().isEmpty()) {
        usuario_existente.setContrasena(passwordEncoder.encode(nuevosDatos.getContrasena()));
    }

    usuario_existente.setCorreoElectronico(nuevosDatos.getCorreoElectronico());
    usuario_existente.setNombreCompleto(nuevosDatos.getNombreCompleto());
    usuario_existente.setTelefono(nuevosDatos.getTelefono());
    usuario_existente.setActualizado(nuevosDatos.getActualizado());
    usuario_existente.setRol(nuevosDatos.getRol());

    usuarioRepository.save(usuario_existente);

    // ... (respuesta sin cambios) ...
    HashMap<String,Object> respuesta= new HashMap<>();
    respuesta.put("Status", 201);
    respuesta.put("mensaje","producto modificados exitosamente");
    respuesta.put("Usuario", usuario_existente);
    return respuesta;
}

//
// //
// // //  metodos Delete
// //
//

@DeleteMapping("/eliminar/{id}")
public HashMap<String,Object>borrarUsuario(@RequestParam int id){

    HashMap<String,Object> respuesta= new HashMap<>();

    if (usuarioRepository.existsById(id)){
        usuarioRepository.deleteById(id);
        respuesta.put("Status", 200);
        respuesta.put("Mensaje","Usuario eliminado exitosamente");
    }else {
        respuesta.put("Status", 404);
        respuesta.put("mensaje","Usuario no encontrado");
    }

    return respuesta;
}*/

}

