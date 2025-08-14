package com.LiterAlura.LiterAlura.repository;

import com.LiterAlura.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(String idioma);
}