package egg.libreria.service;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Editorial;
import egg.libreria.repository.EditorialRepository;
import java.util.List;
import java.util.Optional;

import egg.libreria.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {
    
    @Autowired
    private EditorialRepository editorialRepository;

    @Transactional(readOnly = true)
    public List<Editorial> buscarHabilitados() {
        return editorialRepository.editorialesDeAlta();
    }

    @Transactional(readOnly = true)
    public List<Editorial> buscarDeshabilitados() {
        return editorialRepository.editorialesDeBaja();
    }

    @Transactional(readOnly = true)
    public Editorial buscarPorId(Integer id) throws MiException {
        try{
            Util.esNumero(Integer.toString(id));
            Optional<Editorial> optionalEditorial = editorialRepository.findById(id);
            return optionalEditorial.orElse(null);
        } catch(MiException e) {
            throw e;
        } catch(Exception e) {
            throw e;
        }
    }

    @Transactional
    public void crear(String nombre) throws MiException {
        try {
            Util.sonLetras(nombre);
            editorialRepository.save(new Editorial(nombre, true));
        } catch (MiException e) {
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
            editorialRepository.modificar(id, nombre);
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
            editorialRepository.deleteById(id);
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void habilitar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            Editorial editorial = buscarPorId(id);
            editorial.setAlta(true);
            editorialRepository.save(editorial);
        } catch (MiException e) {
            throw e;
        }
    }

}
