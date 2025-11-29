package com.Jesus.Ecommerce.Modelos;


import jakarta.persistence.*;

@Entity
@Table(name ="categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "categoria_padre_id",referencedColumnName = "id",nullable = true)
    private Categoria categoria;

    public Categoria() {
    }

    public Categoria(Integer id, String nombre, String descripcion, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
