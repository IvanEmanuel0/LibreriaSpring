package egg.libreria.repository;

import egg.libreria.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Integer>{

    @Modifying
    @Query("UPDATE Autor a SET a.nombre = :nombre WHERE a.id = :id")
    void modificar(@Param("id") Integer id, @Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a WHERE a.alta=true")
    List<Autor> autoresDeAlta();

    @Query("SELECT a FROM Autor a WHERE a.alta=false")
    List<Autor> autoresDeBaja();
}
