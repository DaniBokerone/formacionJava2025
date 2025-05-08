package com.edisa.formacion.mayo2025;

import com.edisa.formacion.mayo2025.DropWizard.DropWizardConfiguration;
import com.edisa.formacion.mayo2025.DropWizard.Recursos;
import com.edisa.formacion.mayo2025.ejercicios.Ejercicio1;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.Scanner;

public class Main extends Application<DropWizardConfiguration> {
    public static void main(String[] args) throws Exception{

        System.out.println("------------------- Arrancado modo server tambien -------------------");
        //Con esto activamos las apis
        new Main().run("server");

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("------------------- MENU EJERCICIOS -------------------");
            System.out.println("Selecciona una opcion:");
            System.out.println("1. Ejercicio 1");
            System.out.println("2. Ejercicio 2");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            System.out.println("-------------------------------------------------------");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        Ejercicio1.ejecutar_ex1(scanner);
                        break;
                    case 2:
                        Ejercicio1.ejecutar_ex2(scanner);
                        break;
                    case 0:
                        System.out.println("Buenos dias, buenas tardes, y buenas noches ...");
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

    @Override
    public void initialize(Bootstrap<DropWizardConfiguration> bootstrap) {
        // Configuración adicional si es necesaria
    }

    @Override
    public void run(DropWizardConfiguration configuration, Environment environment) {
        final Recursos resource = new Recursos();
        environment.jersey().register(resource);
    }
}