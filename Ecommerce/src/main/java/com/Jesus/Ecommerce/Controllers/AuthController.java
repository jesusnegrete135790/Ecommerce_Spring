package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Login.AuthResponseDTO;
import com.Jesus.Ecommerce.DTOs.Login.LoginRequestDTO;
import com.Jesus.Ecommerce.Modelos.Usuario;
import com.Jesus.Ecommerce.Seguridad.JWT.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") // Permitir Angular
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // Ojo: Debes exponer este Bean en SecurityConfig
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        // 1. Autenticar usando Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.nombreUsuario(), request.contrasena())
        );

        // 2. Cargar el usuario y convertirlo a tu clase 'Usuario'
        // Hacemos el cast (Usuario) para poder usar .getId()
        Usuario usuario = (Usuario) userDetailsService.loadUserByUsername(request.nombreUsuario());

        // 2. Si pasa, cargar detalles y generar token
        UserDetails user = userDetailsService.loadUserByUsername(request.nombreUsuario());
        String token = jwtService.generateToken(user);
        System.out.println("login exitoso");

        System.out.println(token);
        return ResponseEntity.ok(new AuthResponseDTO(
                token,
                usuario.getId()
        ));
    }
}
