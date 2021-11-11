package egg.libreria.model.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Prestamo p SET p.alta = false WHERE p.id = ?")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaPrestamo;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @Column(nullable = false)
    private Boolean alta;
    @ManyToOne
    private Libro libro;
    @ManyToOne
    private Cliente cliente;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creacion;
    @LastModifiedDate
    private LocalDateTime modificacion;

    public Prestamo(){

    }

    public Prestamo(Date fechaPrestamo, Date fechaDevolucion, Libro libro, Cliente cliente, Boolean alta) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.alta = alta;
        this.libro = libro;
        this.cliente = cliente;
    }
}
