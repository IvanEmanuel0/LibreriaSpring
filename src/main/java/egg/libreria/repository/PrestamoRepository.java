package egg.libreria.repository;

import egg.libreria.model.entity.Autor;
import egg.libreria.model.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    @Query("SELECT p FROM Prestamo p WHERE p.alta=true")
    List<Prestamo> prestamosDeAlta();

    @Query("SELECT p FROM Prestamo p WHERE p.alta=false")
    List<Prestamo> prestamosDeBaja();

}
