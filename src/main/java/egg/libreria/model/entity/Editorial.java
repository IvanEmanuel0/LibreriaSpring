package egg.libreria.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@SQLDelete(sql = "UPDATE Editorial e SET e.alta = false WHERE e.id = ?")
public class Editorial {
    
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
    @OneToMany(mappedBy = "editorial", fetch = FetchType.EAGER)
    private List<Libro> libros;
    
    public Editorial(){
        
    }

    public Editorial(String nombre, Boolean alta) {
        this.nombre = nombre;
        this.alta = alta;
    }


}
