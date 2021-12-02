package egg.libreria.service;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Usuario;
import egg.libreria.repository.UsuarioRepository;
import egg.libreria.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private final String MENSAJE = "El username ingresado no existe %s";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(MENSAJE, username)));
        Usuario usuario = usuarioRepository.buscarUsuarioPorUsername(username);
        if(usuario == null) throw new UsernameNotFoundException(String.format(MENSAJE, username));
        return new User(usuario.getUsername(), usuario.getClave(), Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
        } catch(MiException e){
            throw e;
        } catch (Exception e){
            throw e;
        }
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        return optionalUsuario.orElse(null);
    }

    @Transactional
    public void crear(String username, String clave) throws MiException{
        try {
            if(usuarioRepository.existsUsuarioByUsername(username)) throw new MiException("El usuario ya existe");
            usuarioRepository.save(new Usuario(username, encoder.encode(clave), true));
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void eliminar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            usuarioRepository.deleteById(id);
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public void habilitar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            Usuario usuario = buscarPorId(id);
            usuario.setAlta(true);
            usuarioRepository.save(usuario);
        } catch (MiException e) {
            throw e;
        }
    }

    public void modificar(Integer id, String username, String clave) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            Usuario usuario = buscarPorId(id);
            usuario.setUsername(username);
            usuario.setClave(clave);
            usuarioRepository.save(usuario);
        } catch (MiException e) {
            throw e;
        }
    }

    public List<Usuario> buscarHabilitados() {
        return usuarioRepository.usuariosHabilitados();
    }

    public Object buscarDeshabilitados() {
        return usuarioRepository.usuariosDeshabilitados();
    }

    public Usuario buscarPorUsername(String username) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
        return optionalUsuario.orElse(null);
    }
}
