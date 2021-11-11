package egg.libreria.model.entity;

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
@SQLDelete(sql = "UPDATE Libro l SET l.alta = false WHERE l.id = ?")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Long isbn;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private Integer anio;
    @Column(nullable = false)
    private Integer ejemplares;
    @Column(nullable = false)
    private Integer ejemplaresPrestados;
    @Column(nullable = false)
    private Integer ejemplaresRestantes;
    @Column(nullable = false)
    private Boolean alta;
    @ManyToOne
    private Autor autor;
    @ManyToOne 
    private Editorial editorial;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creacion;
    @LastModifiedDate
    private  LocalDateTime modificacion;

    public Libro(Long isbn, String titulo, Integer anio, Integer ejemplares, Boolean alta, Autor autor, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.ejemplaresPrestados = 0;
        this.ejemplaresRestantes = this.ejemplares;
        this.alta = alta;
        this.autor = autor;
        this.editorial = editorial;
    }

    public Libro() {

    }

}
