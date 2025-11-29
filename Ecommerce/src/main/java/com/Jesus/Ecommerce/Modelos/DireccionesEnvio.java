package com.Jesus.Ecommerce.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "direcciones_envio")
public class DireccionesEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String direccion1;
    private String direccion2;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;


    @ManyToOne
    @JoinColumn(name = "usuarioId",referencedColumnName = "id",nullable = true)
    private Usuario usuario;

    public DireccionesEnvio() {
    }

    public DireccionesEnvio(Integer id, String direccion1, String direccion2, String ciudad, String estado, String codigoPostal, String pais, Usuario usuario) {
        this.id = id;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
