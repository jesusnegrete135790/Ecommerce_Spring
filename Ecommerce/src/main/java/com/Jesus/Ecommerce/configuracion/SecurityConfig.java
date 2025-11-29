package com.Jesus.Ecommerce.configuracion;

import com.Jesus.Ecommerce.Repositorios.UsuarioRepository;
import com.Jesus.Ecommerce.Servicios.Impl.DetallesUsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF (necesario para APIs REST)
                .csrf(csrf -> csrf.disable())

                // 2. Deshabilitar el inicio de sesión basado en formularios
                .formLogin(form -> form.disable())

                // 3. Deshabilitar la gestión de logout
                .logout(logout -> logout.disable())

                // 4. Configurar las reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // 5. Permitir todas las peticiones a cualquier endpoint
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    // --- Mantenemos estos Beans ---
    // El PasswordEncoder sigue siendo necesario para hashear las contraseñas
    // en tu UsuarioControler.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Dejamos el UserDetailsService y el AuthenticationProvider por si
    // en el futuro quieres añadir seguridad a la API (ej. HTTP Basic o JWT).
    @Bean
    public UserDetailsService userDetailsService() {
        return new DetallesUsuarioServiceImpl(usuarioRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}