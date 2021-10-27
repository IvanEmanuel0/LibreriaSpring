/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.service;

import egg.libreria.model.entity.Autor;

import egg.libreria.repository.AutorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {
    
    @Autowired
    private AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public List<Autor> buscarTodos() {
        return autorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(Integer id) {
        Optional<Autor> autorOptional = autorRepository.findById(id);
        return autorOptional.orElse(null);
    }

    @Transactional
    public void crear(String nombre) {
        autorRepository.save(new Autor(nombre, true));
    }

    @Transactional
    public void eliminar(Integer id) {
        autorRepository.deleteById(id);
    }

    @Transactional
    public void modificar(Integer id, String nombre) {
        autorRepository.modificar(id, nombre);
    }
    
}
