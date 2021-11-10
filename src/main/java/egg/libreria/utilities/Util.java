package egg.libreria.utilities;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Libro;

import java.util.Date;

public class Util {

    public static void noEsVacio(String respuesta) throws MiException{
        if(respuesta.trim().isEmpty() || respuesta == null) throw new MiException("El campo no puede ser vacío.");
    }

    public static void esNumero(String numero) throws MiException {
        noEsVacio(numero);
        if(!numero.matches("^[0-9]*$")) throw new MiException("Solo se deben ingresar números.");
    }

    public static void sonLetras(String palabra) throws MiException {
        noEsVacio(palabra);
        if(palabra.matches("^-?[0-9]+$")) throw new MiException("Solo se deben ingresar letras.");
    }

    public static void validarISBN(String isbn) throws MiException {
        esNumero(isbn);
        if (!isbn.matches("^\\w{10,13}+$")) throw new MiException("El largo del isbn debe ser entre 10 y 13 dígitos.");
    }

    public static void validarAnio(String anio) throws MiException {
        esNumero(anio);
        if (!anio.matches("^\\w{4}+$")) throw new MiException("El año debe tener 4 dígitos");
    }

    public static void validarDNI(String dni) throws MiException {
        esNumero(dni);
        if (!dni.matches("^\\w{7,8}+$")) throw new MiException("El largo del DNI debe ser entre 7 y 8 dígitos.");
    }

    public static void validarCronologia(Date fechaPrestamo, Date fechaDevolucion) throws MiException {
        if(fechaPrestamo.after(fechaDevolucion)) throw new MiException("La fecha de prestamo debe ser anterior a la de devolución.");
    }

    public static void hayStock(Libro libro) throws MiException {
        if(libro.getEjemplaresRestantes() <= 0) throw new MiException("No hay libros en stock");
    }

}
