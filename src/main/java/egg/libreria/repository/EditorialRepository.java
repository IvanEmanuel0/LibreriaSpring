package egg.libreria.repository;

import egg.libreria.model.entity.Autor;
import egg.libreria.model.entity.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EditorialRepository extends JpaRepository<Editorial, Integer> {

    /*@Modifying
    @Query("UPDATE Editorial e SET e.nombre = :nombre WHERE e.id = :id")
    void modificar(@Param("id") Integer id, @Param("nombre") String nombre);*/

    @Query("SELECT e FROM Editorial e WHERE e.alta=true")
    List<Editorial> editorialesDeAlta();

    @Query("SELECT e FROM Editorial e WHERE e.alta=false")
    List<Editorial> editorialesDeBaja();

}
