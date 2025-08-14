package com.LiterAlura.LiterAlura.service;

import org.springframework.stereotype.Service;
import java.util.Scanner;

@Service
public class MenuService {

    private final LibroService libroService;

    public MenuService(LibroService libroService) {
        this.libroService = libroService;
    }

    public void exhibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n📚 Bienvenido a LiterAlura");
            System.out.println("1 - Buscar libros en español desde la API");
            System.out.println("2 - Insertar libro manualmente");
            System.out.println("3 - Mostrar todos los libros guardados");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        libroService.buscarLibroPorTitulo(scanner);
                        break;
                    case 2:
                        libroService.mostrarLibrosPorIdioma(scanner);
                        break;
                    case 3:
                        libroService.mostrarLibrosGuardados();
                        break;
                    case 4:
                        libroService.mostrarAutores();
                        break;
                    case 5:
                        libroService.mostrarAutoresVivosEnAño(scanner);
                        break;

                    case 0:
                        System.out.println("👋 ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("❌ Opción inválida. Intente nuevamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrada no válida. Por favor ingrese un número.");
            }
        }
    }
}