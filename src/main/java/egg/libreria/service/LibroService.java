/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.service;

import egg.libreria.model.entity.Autor;
import egg.libreria.model.entity.Editorial;
import egg.libreria.model.entity.Libro;
import egg.libreria.repository.LibroRepository;
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
    public List<Libro> buscarTodos() {
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(Integer id) {
        Optional<Libro> optionalLibro = libroRepository.findById(id);
        return optionalLibro.orElse(null);
    }

    @Transactional
    public void crear(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) {
        libroRepository.save(new Libro(isbn, titulo, anio, ejemplares, true, autor, editorial));
    }

    @Transactional
    public void modificar(Integer id, Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) {
        Libro libro = buscarPorId(id);
        if(libro != null){
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroRepository.save(libro);
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        libroRepository.deleteById(id);
    }
}
