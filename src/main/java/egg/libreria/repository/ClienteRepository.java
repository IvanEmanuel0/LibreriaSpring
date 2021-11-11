package egg.libreria.repository;

import egg.libreria.model.entity.Autor;
import egg.libreria.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.alta=true")
    List<Cliente> clientesDeAlta();

    @Query("SELECT c FROM Cliente c WHERE c.alta=false")
    List<Cliente> clientesDeBaja();

}
