package com.edisa.formacion.mayo2025.ejercicios;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Ejercicio1 {

    private static class DatosEntrada {
        String text;
        Path dirPath;
        String fileName;

        DatosEntrada(String text, Path dirPath, String fileName) {
            this.text = text;
            this.dirPath = dirPath;
            this.fileName = fileName;
        }
    }

    public static void ejecutar_ex1(Scanner scanner) {
        DatosEntrada datos = leerDatosEntrada(scanner);
        if (datos == null) return;

        Path rutaCompleta = datos.dirPath.resolve(datos.fileName + ".jpg");

        try {
            generarCodigoBarras(datos.text, rutaCompleta, BarcodeFormat.QR_CODE);
            System.out.println("Código QR generado en: " + rutaCompleta);
        } catch (WriterException | IOException e) {
            System.err.println("Error al generar el código QR: " + e.getMessage());
        }
    }

    public static void ejecutar_ex2(Scanner scanner) {
        DatosEntrada datos = leerDatosEntrada(scanner);
        if (datos == null) return;

        System.out.print("Introduce el formato del código de barras (ej: QR_CODE, EAN_13): ");
        String formatoInput = scanner.nextLine().toUpperCase();

        BarcodeFormat formato;
        try {
            formato = BarcodeFormat.valueOf(formatoInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Formato no reconocido. Volviendo al menú...");
            return;
        }

        if (formato == BarcodeFormat.EAN_13) {
            if (!datos.text.matches("\\d{12}")) {
                System.out.println("Para EAN_13, el texto debe contener exactamente 12 dígitos numéricos (el último se calculará automáticamente). Volviendo al menú...");
                return;
            }
        }

        Path subdirectorio = datos.dirPath.resolve(formato.name());
        Path rutaCompleta = subdirectorio.resolve(datos.fileName + ".jpg");

        try {
            Files.createDirectories(subdirectorio);
            generarCodigoBarras(datos.text, rutaCompleta, formato);
            System.out.println("Código de barras generado en: " + rutaCompleta);
        } catch (IOException | WriterException e) {
            System.err.println("Error al generar el código: " + e.getMessage());
        }
    }

    private static DatosEntrada leerDatosEntrada(Scanner scanner) {
        System.out.print("Introduce el text para el código: ");
        String text = scanner.nextLine();

        System.out.print("Introduce la ruta del directorio donde guardar el archivo (ej: C:\\\\ruta): ");
        String dirPathStr = scanner.nextLine();
        Path dirPath = Paths.get(dirPathStr);

        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            System.out.println("La ruta introducida no es válida o no es un directorio. Volviendo al menú...");
            return null;
        }

        System.out.print("Introduce el nombre del archivo: ");
        String fileName = scanner.nextLine();

        if (fileName.trim().isEmpty()) {
            System.out.println("El nombre del archivo no puede estar vacío. Volviendo al menú...");
            return null;
        }

        return new DatosEntrada(text, dirPath, fileName);
    }

    private static void generarCodigoBarras(String text, Path rutaArchivo, BarcodeFormat formato)
            throws WriterException, IOException {

        int ancho = 300;
        int alto = 300;

        if (formato == BarcodeFormat.EAN_13 || formato == BarcodeFormat.CODE_128) {
            ancho = 400;
            alto = 150;
        }

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, formato, ancho, alto);
        MatrixToImageWriter.writeToPath(bitMatrix, "JPG", rutaArchivo);
    }

    //FUNCION SOLO PARA API
    public static BufferedImage generarCodigoBarrasApi(String text, BarcodeFormat formato)
            throws WriterException {

        int ancho = 300;
        int alto = 300;

        if (formato == BarcodeFormat.EAN_13 || formato == BarcodeFormat.CODE_128) {
            ancho = 400;
            alto = 150;
        }

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, formato, ancho, alto);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
