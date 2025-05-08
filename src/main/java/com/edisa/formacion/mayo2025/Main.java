package com.edisa.formacion.mayo2025;

import com.edisa.formacion.mayo2025.ejercicios.Ejercicio1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("------------------- MENU EJERCICIOS -------------------");
            System.out.println("Selecciona una opcion:");
            System.out.println("1. Ejercicio 1");
            System.out.println("2. Ejercicio 2");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        Ejercicio1.ejecutar(scanner);
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, debe ser un número.");
            }

            System.out.println();
        }

        scanner.close();
    }
}