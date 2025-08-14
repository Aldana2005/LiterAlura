package com.LiterAlura.LiterAlura;

import com.LiterAlura.LiterAlura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	private final LibroService libroService;

	public LiterAluraApplication(LibroService libroService) {
		this.libroService = libroService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		mostrarMenu();
	}

	private void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		int opcion = -1;

		while (opcion != 0) {
			System.out.println("\n📚 Bienvenido a LiterAlura");
			System.out.println("1️⃣ Buscar libro por título");
			System.out.println("2️⃣ Mostrar todos los libros guardados");
			System.out.println("3️⃣ Mostrar libros por idioma");
			System.out.println("4️⃣ Mostrar todos los autores guardados");
			System.out.println("5️⃣ Mostrar autores vivos en un año");
			System.out.println("6.Eliminar libros inválidos");
			System.out.println("0️⃣ Salir");
			System.out.print("Seleccione una opción: ");

			try {
				opcion = Integer.parseInt(scanner.nextLine());

				switch (opcion) {
					case 1 -> libroService.buscarLibroPorTitulo(scanner);
					case 2 -> libroService.mostrarLibrosGuardados();
					case 3 -> libroService.mostrarLibrosPorIdioma(scanner);
					case 4 -> libroService.mostrarAutores();
					case 5 -> libroService.mostrarAutoresVivosEnAño(scanner);
					case 6 -> libroService.eliminarLibrosInvalidos();
					case 0 -> System.out.println("👋 ¡Gracias por usar LiterAlura!");
					default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
				}

			} catch (NumberFormatException e) {
				System.out.println("⚠️ Entrada no válida. Por favor ingrese un número.");
			}
		}

		scanner.close();
	}
}