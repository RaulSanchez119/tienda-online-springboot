package rivera.sanchez.raul.tiendaonline.services;

import rivera.sanchez.raul.tiendaonline.enums.Formato;

public class Validador {

    public static String validarCamposDiscos(String titulo, String autor, int pistas, double precio, int anyo) {

        if (titulo == null || titulo.isEmpty()) {
            return "El título es obligatorio y no puede estar vacío";
        }

        if (pistas <= 0) {
            return "El número de pistas debe ser mayor a 0";
        }

        if (precio <= 0) {
            return "El precio debe ser mayor a 0";
        }

        if (autor == null || autor.isEmpty()) {
            return "El autor es obligatorio y no puede estar vacío";
        }

        if (anyo <= 0) {
            return "El año debe ser un valor positivo y mayor a 0";
        }

        return null;
    }

    public static String validarCamposLibros(String titulo, String autor, String isbn, double precio, String editorial) {

        if (titulo == null || titulo.isEmpty()) {
            return "El título es obligatorio y no puede estar vacío";
        }

        if (autor == null || autor.isEmpty()) {
            return "El autor es obligatorio y no puede estar vacio";
        }

        if (precio <= 0) {
            return "El precio debe ser mayor a 0";
        }

        if (isbn == null || isbn.isEmpty()) {
            return "El isbn es obligatorio y no puede estar vacío";
        }

        if (editorial == null || editorial.isEmpty()) {
            return "La editorial es obligatoria y no puede estar vacía";
        }

        return null;
    }

    public static String validarCamposPeliculas(String titulo, Formato formato, int anyo, double precio, String director) {

        if (titulo == null || titulo.isEmpty()) {
            return "El título es obligatorio y no puede estar vacío";
        }

        if (formato == null) {
            return "El formato es obligatorio y no puede estar vacio";
        }

        if (precio <= 0) {
            return "El precio debe ser mayor a 0";
        }

        if (anyo <= 0) {
            return "El año debe ser un valor positivo y mayor a 0";
        }

        if (director == null || director.isEmpty()) {
            return "El director es obligatorio y no puede estar vacío";
        }

        return null;
    }
}
