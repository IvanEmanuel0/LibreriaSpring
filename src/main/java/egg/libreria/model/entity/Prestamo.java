package egg.libreria.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
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
