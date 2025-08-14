package com.LiterAlura.LiterAlura.service;

import com.LiterAlura.LiterAlura.model.Libro;
import com.LiterAlura.LiterAlura.model.RespuestaGutendex;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GutendexService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "https://gutendex.com/books?languages=es";

    public List<Libro> obtenerTodosLosLibrosConJackson() {
        try {
            RespuestaGutendex respuesta = restTemplate.getForObject(URL, RespuestaGutendex.class);
            System.out.println("📨 Respuesta recibida correctamente.");
            return respuesta != null ? respuesta.getLibros() : List.of();
        } catch (Exception e) {
            System.out.println("❌ Error al consumir la API: " + e.getMessage());
            return List.of();
        }
    }
}