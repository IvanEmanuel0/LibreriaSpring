package egg.libreria.repository;

import egg.libreria.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario buscarUsuarioPorUsername(@Param("username") String username);

    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE u.alta = true")
    List<Usuario> usuariosHabilitados();

    @Query("SELECT u FROM Usuario u WHERE u.alta = false")
    List<Usuario> usuariosDeshabilitados();

    boolean existsUsuarioByUsername(String username);
}
