/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.service;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Autor;

import egg.libreria.repository.AutorRepository;
import java.util.List;
import java.util.Optional;

import egg.libreria.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static egg.libreria.utilities.Util.*;

@Service
public class AutorService {
    
    @Autowired
    private AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public List<Autor> buscarTodos() {
        return autorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
        } catch(MiException e){
            throw e;
        } catch(Exception e){
            throw e;
        }
        Optional<Autor> autorOptional = autorRepository.findById(id);
        return autorOptional.orElse(null);
    }

    @Transactional
    public void crear(String nombre) throws  MiException {
        try {
            Util.sonLetras(nombre);
            autorRepository.save(new Autor(nombre, true));
        } catch(MiException e) {
            throw e;
        }

    }

    @Transactional
    public void eliminar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            autorRepository.deleteById(id);
        } catch(MiException e){
            throw e;
        } catch (Exception e) {
            throw e;
        }


    }

    @Transactional
    public void modificar(Integer id, String nombre) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            Util.sonLetras(nombre);
            autorRepository.modificar(id, nombre);
        } catch(MiException e){
            throw e;
        }

    }
    
}
