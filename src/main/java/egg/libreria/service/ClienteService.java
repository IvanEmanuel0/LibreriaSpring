package egg.libreria.service;

import egg.libreria.model.entity.Cliente;
import egg.libreria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Integer id) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        return optionalCliente.orElse(null);
    }

    @Transactional
    public void crear(Long dni, String nombre, String apellido, String telefono) {
        clienteRepository.save(new Cliente(dni, nombre, apellido, telefono, true));
    }

    @Transactional
    public void modificar(Integer id, Long dni, String nombre, String apellido, String telefono) {
        Cliente cliente = buscarPorId(id);
        if(cliente != null){
            cliente.setDni(dni);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            clienteRepository.save(cliente);
        }
    }

    public void eliminar(Integer id) {
        clienteRepository.deleteById(id);
    }
}
