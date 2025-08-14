package com.LiterAlura.LiterAlura.model;

import jakarta.persistence.*;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    public Autor() {}

    public Autor(String nombre, Integer nacimiento, Integer fallecimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
    }

    // Getters y setters

    @Override
    public String toString() {
        return nombre + " (" + nacimiento + "–" + (fallecimiento != null ? fallecimiento : "vivo") + ")";
    }

    public String getNombre() {
        return nombre;
    }
}