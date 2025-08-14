package com.LiterAlura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaGutendex {

    @JsonAlias("results")
    private List<Libro> libros;

    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }

    @Override
    public String toString() {
        return "📚 Libros encontrados: " + libros.size() + "\n" + libros;
    }
}