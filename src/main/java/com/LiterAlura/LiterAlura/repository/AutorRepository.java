package com.LiterAlura.LiterAlura.repository;

import com.LiterAlura.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Derived query para autores vivos en un año específico
    List<Autor> findByFallecimientoIsNullOrFallecimientoGreaterThan(Integer año);
    Optional<Autor> findByNombreIgnoreCase(String nombre);

}
