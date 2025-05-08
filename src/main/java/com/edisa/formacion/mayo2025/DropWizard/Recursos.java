package com.edisa.formacion.mayo2025.DropWizard;

import com.google.zxing.BarcodeFormat;
import com.edisa.formacion.mayo2025.ejercicios.Ejercicio1;
import com.google.zxing.WriterException;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class Recursos {

    @GET
    @Path("/saludo")
    public String saludar(@QueryParam("nombre") String nombre,
                            @QueryParam("apellido") String apellido,
                            @QueryParam("edad") int edad) {

        return "Hola desde el metodo GET, " + nombre + " " + apellido + ". Tienes " + edad + " años.";
    }

    @POST
    @Path("/saludo/post")
    public String saludar_post(@QueryParam("nombre") String nombre,
                               @QueryParam("apellido") String apellido,
                               @QueryParam("edad") int edad) {

        return "Hola desde el metodo POST, " + nombre + " " + apellido + ". Tienes " + edad + " años.";
    }

    @GET
    @Path("/codabar/generar")
    @Produces("image/jpeg")
    public Response generarCodigoBarras(@QueryParam("texto") String texto,
                                        @QueryParam("formato_codigo_barras") String formatoCodigoBarras) {
        if (texto == null ) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Faltan parámetros requeridos: texto ").build();
        }

        try {
            BarcodeFormat formato;

            if (formatoCodigoBarras == null || formatoCodigoBarras.trim().isEmpty()) {
                formato = BarcodeFormat.QR_CODE;
            } else {
                formato = BarcodeFormat.valueOf(formatoCodigoBarras.toUpperCase());
            }

            if (formato == BarcodeFormat.EAN_13 && !texto.matches("\\d{12,13}")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Para EAN_13, el texto debe contener exactamente 12 o 13 dígitos numéricos.").build();
            }

            BufferedImage imagen = Ejercicio1.generarCodigoBarrasApi(texto, formato);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagen, "jpg", baos);

            return Response.ok(baos.toByteArray(), "image/jpeg").build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Formato no reconocido: " + formatoCodigoBarras).build();
        } catch (WriterException | IOException e) {
            return Response.serverError().entity("Error al generar el código: " + e.getMessage()).build();
        }
    }

}