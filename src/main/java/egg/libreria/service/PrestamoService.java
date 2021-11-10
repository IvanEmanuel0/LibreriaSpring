package egg.libreria.service;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Cliente;
import egg.libreria.model.entity.Libro;
import egg.libreria.model.entity.Prestamo;
import egg.libreria.repository.PrestamoRepository;
import egg.libreria.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Transactional(readOnly = true)
    public List<Prestamo> buscarTodos() {
        return prestamoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Prestamo buscarPorId(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
        } catch(MiException e) {
            throw e;
        } catch(Exception e) {
            throw e;
        }
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(id);
        return prestamoOptional.orElse(null);
    }

    @Transactional
    public void crear(Date fechaPrestamo, Date fechaDevolucion, Libro libro, Cliente cliente) throws MiException {
        try {
            Util.validarCronologia(fechaPrestamo, fechaDevolucion);
            Util.hayStock(libro);
            prestarLibro(libro);
            prestamoRepository.save(new Prestamo(fechaPrestamo, fechaDevolucion, libro, cliente, true));
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificar(Integer id, Date fechaPrestamo, Date fechaDevolucion, Libro libro, Cliente cliente) throws MiException {
        try {
            Util.validarCronologia(fechaPrestamo, fechaDevolucion);
            Prestamo prestamo = buscarPorId(id);
            if(prestamo != null) {
                prestamo.setFechaPrestamo(fechaPrestamo);
                prestamo.setFechaDevolucion(fechaDevolucion);
                prestamo.setCliente(cliente);
                prestamo.setLibro(libro);
                prestamoRepository.save(prestamo);
            }
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void eliminar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            prestamoRepository.deleteById(id);
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /*private Boolean hayStock(Libro libro){
        return libro.getEjemplaresRestantes() > 0;
    }*/

    public void prestarLibro(Libro libro){
        libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()-1);
        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()+1);

    }



}
