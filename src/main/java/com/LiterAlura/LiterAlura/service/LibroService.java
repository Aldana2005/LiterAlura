package com.LiterAlura.LiterAlura.service;

import com.LiterAlura.LiterAlura.model.Autor;
import com.LiterAlura.LiterAlura.model.Libro;
import com.LiterAlura.LiterAlura.repository.AutorRepository;
import com.LiterAlura.LiterAlura.repository.LibroRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarLibroPorTitulo(Scanner scanner) {
        System.out.print("🔍 Ingrese el título del libro: ");
        String titulo = scanner.nextLine();

        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            JSONArray resultados = json.getJSONArray("results");

            if (resultados.isEmpty()) {
                System.out.println("📭 No se encontró ningún libro con ese título.");
                return;
            }

            JSONObject libroJson = resultados.getJSONObject(0);
            JSONArray autores = libroJson.getJSONArray("authors");

            if (autores.isEmpty()) {
                System.out.println("⚠️ El libro no tiene autor registrado.");
                return;
            }

            JSONObject autorJson = autores.getJSONObject(0);
            String nombre = autorJson.getString("name");
            Integer nacimiento = autorJson.isNull("birth_year") ? null : autorJson.getInt("birth_year");
            Integer fallecimiento = autorJson.isNull("death_year") ? null : autorJson.getInt("death_year");

            Autor autor = new Autor(nombre, nacimiento, fallecimiento);
            Autor autorFinal = autorRepository.findByNombreIgnoreCase(nombre)
                    .orElseGet(() -> autorRepository.save(autor));

            String tituloLibro = libroJson.optString("title", null);
            String idioma = libroJson.optJSONArray("languages") != null && !libroJson.getJSONArray("languages").isEmpty()
                    ? libroJson.getJSONArray("languages").getString(0)
                    : null;
            Integer descargas = libroJson.has("download_count") ? libroJson.getInt("download_count") : null;

            if (tituloLibro != null && idioma != null && descargas != null && autorFinal != null) {
                Libro libro = new Libro(tituloLibro, idioma, descargas, autorFinal);
                libroRepository.save(libro);
                System.out.println("✅ Libro y autor guardados correctamente.");
            } else {
                System.out.println("⚠️ Datos incompletos. No se guardó el libro.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al buscar el libro: " + e.getMessage());
        }
    }

    public void mostrarLibrosGuardados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros guardados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    public void mostrarLibrosPorIdioma(Scanner scanner) {
        System.out.print("🌐 Ingrese el idioma a filtrar (ej. 'es'): ");
        String idioma = scanner.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros en ese idioma.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    public void mostrarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("📭 No hay autores guardados.");
        } else {
            System.out.println("📚 Lista de autores:");
            autores.forEach(System.out::println);
        }
    }

    public void mostrarAutoresVivosEnAño(Scanner scanner) {
        System.out.print("📅 Ingrese el año para buscar autores vivos: ");
        int año = Integer.parseInt(scanner.nextLine());

        List<Autor> vivos = autorRepository.findByFallecimientoIsNullOrFallecimientoGreaterThan(año);
        if (vivos.isEmpty()) {
            System.out.println("📭 No se encontraron autores vivos en ese año.");
        } else {
            System.out.println("🧑‍🎓 Autores vivos en el año " + año + ":");
            vivos.forEach(System.out::println);
        }
    }
    public void eliminarLibrosInvalidos() {
        List<Libro> libros = libroRepository.findAll();
        for (Libro libro : libros) {
            if (libro.getTitulo() == null || libro.getIdioma() == null || libro.getDescargas() == null || libro.getAutor() == null) {
                libroRepository.delete(libro);
                System.out.println("🗑️ Libro eliminado: id=" + libro.getId());
            }
        }
    }
}