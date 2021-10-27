package egg.libreria.service;

import egg.libreria.model.entity.Editorial;
import egg.libreria.repository.EditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {
    
    @Autowired
    private EditorialRepository editorialRepository;

    @Transactional(readOnly = true)
    public List<Editorial> buscarTodos() {
        return editorialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Editorial buscarPorId(Integer id) {
        Optional<Editorial> optionalEditorial = editorialRepository.findById(id);
        return optionalEditorial.orElse(null);
    }

    @Transactional
    public void crear(String nombre) {
        editorialRepository.save(new Editorial(nombre, true));
    }

    public void modificar(Integer id, String nombre) {
        editorialRepository.modificar(id, nombre);
    }

    public void eliminar(Integer id) {
        editorialRepository.deleteById(id);
    }
    
}
