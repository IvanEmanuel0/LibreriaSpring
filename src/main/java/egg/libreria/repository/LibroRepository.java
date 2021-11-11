package egg.libreria.repository;

import egg.libreria.model.entity.Autor;
import egg.libreria.model.entity.Editorial;
import egg.libreria.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    /*@Modifying
    @Query("UPDATE Libro l SET l.isbn = :isbn, l.titulo = :titulo, l.anio = :anio, l.ejemplares = :ejemplares, l.autor = :autor, l.editorial = :editorial WHERE l.id = :id ")
    void modificar(@Param("id") Integer id,@Param("isbn") Long isbn,@Param("titulo") String titulo,@Param("anio") Integer anio, @Param("ejemplares") Integer ejemplares, @Param("autor") Autor autor, @Param("editorial") Editorial editorial);
*/

    @Query("SELECT l FROM Libro l WHERE l.alta=true")
    List<Libro> librosDeAlta();

    @Query("SELECT l FROM Libro l WHERE l.alta=false")
    List<Libro> librosDeBaja();

}
