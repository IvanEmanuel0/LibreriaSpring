package egg.libreria.service;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Cliente;
import egg.libreria.model.entity.Usuario;
import egg.libreria.repository.ClienteRepository;
import egg.libreria.utilities.Util;
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
    @Autowired
    private UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public List<Cliente> buscarHabilitados() {
        return clienteRepository.clientesDeAlta();
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarDeshabilitados() {
        return clienteRepository.clientesDeBaja();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        return optionalCliente.orElse(null);
    }

    @Transactional
    public void crear(Long dni, String nombre, String apellido, String telefono, String username, String clave) throws MiException {
        try {
            Util.validarDNI(Long.toString(dni));
            Util.sonLetras(nombre);
            Util.sonLetras(apellido);
            Util.esNumero(telefono);
            usuarioService.crear(username, clave);
            clienteRepository.save(new Cliente(dni, nombre, apellido, telefono, true, usuarioService.buscarPorUsername(username)));
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificar(Integer id, Long dni, String nombre, String apellido, String telefono) throws MiException {
        try {
            Util.validarDNI(Long.toString(dni));
            Util.sonLetras(nombre);
            Util.sonLetras(apellido);
            Util.esNumero(telefono);
            Cliente cliente = buscarPorId(id);
            if(cliente != null){
                cliente.setDni(dni);
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setTelefono(telefono);
                clienteRepository.save(cliente);
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
            clienteRepository.deleteById(id);
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
            Cliente cliente = buscarPorId(id);
            cliente.setAlta(true);
            clienteRepository.save(cliente);
        } catch (MiException e) {
            throw e;
        }
    }

}
