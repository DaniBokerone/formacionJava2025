package com.edisa.formacion.mayo2025.ejercicios;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Ejercicio1 {

    public static void ejecutar(Scanner scanner) {
        System.out.print("Introduce el texto para el código QR: ");
        String texto = scanner.nextLine();

        System.out.print("Introduce la ruta del directorio donde guardar el archivo (ej: C:\\\\ruta): ");
        String rutaDirectorio = scanner.nextLine();

        Path pathDirectorio = Paths.get(rutaDirectorio);
        if (!Files.exists(pathDirectorio) || !Files.isDirectory(pathDirectorio)) {
            System.out.println("La ruta introducida no es válida o no es un directorio. Volviendo al menú...");
            return;
        }

        System.out.print("Introduce el nombre del archivo: ");
        String nombreArchivo = scanner.nextLine();

        if (nombreArchivo.trim().isEmpty()) {
            System.out.println("El nombre del archivo no puede estar vacío. Volviendo al menú...");
            return;
        }

        Path rutaCompleta = pathDirectorio.resolve(nombreArchivo + ".jpg");

        try {
            generarCodigoQR(texto, rutaCompleta.toString(), 300, 300);
            System.out.println("Código QR generado en: " + rutaCompleta);
        } catch (WriterException | IOException e) {
            System.err.println("Error al generar el código QR: " + e.getMessage());
        }
    }

    private static void generarCodigoQR(String texto, String rutaArchivo, int ancho, int alto)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, ancho, alto);

        Path path = FileSystems.getDefault().getPath(rutaArchivo);
        MatrixToImageWriter.writeToPath(bitMatrix, "JPG", path);
    }
}
