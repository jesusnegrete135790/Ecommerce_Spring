package com.Jesus.Ecommerce.Seguridad.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter { // <--- 1. Faltaba extender esto

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // A. Obtener el token del header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // B. Si no hay token o no empieza con "Bearer ", dejamos pasar la petición
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Quitamos la palabra "Bearer "
        userEmail = jwtService.extractUsername(jwt); // Extraemos usuario del token

        // C. Si hay usuario y NO está autenticado todavía en el contexto
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Vamos a la base de datos a buscar los detalles reales del usuario
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // D. Validamos que el token pertenezca a este usuario y no haya expirado
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // E. Creamos el objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // IMPORTANTE: Añadir detalles de la solicitud (IP, sesión, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // F. Lo guardamos en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // G. Continuar con el siguiente filtro
        filterChain.doFilter(request, response);
    }
}