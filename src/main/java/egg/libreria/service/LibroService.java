/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Autor;
import egg.libreria.model.entity.Editorial;
import egg.libreria.model.entity.Libro;
import egg.libreria.repository.LibroRepository;
import egg.libreria.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Transactional(readOnly = true)
    public List<Libro> buscarHabilitados() {
        return libroRepository.librosDeAlta();
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarDeshabilitados() {
        return libroRepository.librosDeBaja();
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
        } catch (MiException e){
            throw e;
        } catch (Exception e) {
            throw e;
        }
        Optional<Libro> optionalLibro = libroRepository.findById(id);
        return optionalLibro.orElse(null);
    }

    @Transactional
    public void crear(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws MiException {
        try {
            Util.validarISBN(Long.toString(isbn));
            Util.validarAnio(Integer.toString(anio));
            Util.esNumero(Integer.toString(ejemplares));
            libroRepository.save(new Libro(isbn, titulo, anio, ejemplares, true, autor, editorial));
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificar(Integer id, Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws MiException {
        try {
            Util.validarISBN(Long.toString(isbn));
            Util.validarAnio(Integer.toString(anio));
            Util.esNumero(Integer.toString(ejemplares));
            Libro libro = buscarPorId(id);
            if (libro != null) {
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                libro.setAnio(anio);
                libro.setEjemplares(ejemplares);
                libro.setAutor(autor);
                libro.setEditorial(editorial);
                libroRepository.save(libro);
            }
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void eliminar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            libroRepository.deleteById(id);
        } catch (MiException e) {
            throw e;
        }
    }

    @Transactional
    public void habilitar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            Libro libro = buscarPorId(id);
            libro.setAlta(true);
            libroRepository.save(libro);
        } catch (MiException e) {
            throw e;
        }
    }

}
