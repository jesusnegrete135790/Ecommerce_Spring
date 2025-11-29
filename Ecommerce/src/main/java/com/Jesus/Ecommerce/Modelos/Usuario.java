package com.Jesus.Ecommerce.Modelos;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name ="usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombreUsuario;
    private String contrasena;
    private String correoElectronico;
    private String nombreCompleto;
    private String telefono;
    private String rol;

    private LocalDateTime creado;
    private LocalDateTime actualizado;

    public Usuario() {
    }

    public Usuario(Integer id, String nombreUsuario, String contrasena, String correoElectronico, String nombreCompleto, String telefono, LocalDateTime creado, LocalDateTime actualizado,String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.creado = creado;
        this.actualizado = actualizado;
        this.rol=rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getCreado() {
        return creado;
    }

    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }

    public LocalDateTime getActualizado() {
        return actualizado;
    }

    public void setActualizado(LocalDateTime actualizado) {
        this.actualizado = actualizado;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.rol == null) {
            return List.of();
        }
        // Esto convierte tu String "ROLE_VENDEDOR" en una autoridad
        return List.of(new SimpleGrantedAuthority(this.rol));
    }

    @Override
    public String getPassword() {
        // Debe devolver la contraseña almacenada
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        // Debe devolver el nombre de usuario
        return this.nombreUsuario;
    }

    // Puedes dejar estos como true por ahora
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}