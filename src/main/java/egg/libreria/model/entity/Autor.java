package egg.libreria.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import egg.libreria.model.entity.Libro;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Autor a SET a.alta = false WHERE a.id = ?")
public class Autor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creacion;
    @LastModifiedDate
    private LocalDateTime modificacion;
    @Column(nullable = false)
    private Boolean alta;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros;
    
    public Autor() {
        
    }

    public Autor(Integer id, String nombre, Boolean alta, List<Libro> libros) {
        this.id = id;
        this.nombre = nombre;
        this.alta = alta;
        this.libros = libros;
    }

    public Autor(String nombre, Boolean alta) {
        this.nombre = nombre;
        this.alta = alta;
    }

}
