package egg.libreria.controller;

import egg.libreria.model.entity.Cliente;
import egg.libreria.model.entity.Libro;
import egg.libreria.model.entity.Prestamo;
import egg.libreria.service.ClienteService;
import egg.libreria.service.LibroService;
import egg.libreria.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private LibroService libroService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("prestamos");
        mav.addObject("prestamos", prestamoService.buscarTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearPrestamo() {
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("prestamo", new Prestamo());
        mav.addObject("libros", libroService.buscarTodos());
        mav.addObject("clientes", libroService.buscarTodos());
        mav.addObject("titulo", "Crear Prestamo");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarPrestamo(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("prestamo-formulario");
        mav.addObject("prestamo", prestamoService.buscarPorId(id));
        mav.addObject("libros", libroService.buscarTodos());
        mav.addObject("clientes", libroService.buscarTodos());
        mav.addObject("titulo", "Editar Prestamo");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam LocalDate fechaPrestamo, @RequestParam LocalDate fechaDevolucion, @RequestParam Libro libro, @RequestParam Cliente cliente) {
        prestamoService.crear(fechaPrestamo, fechaDevolucion, libro, cliente);
        return new RedirectView("/prestamos");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam LocalDate fechaPrestamo, @RequestParam LocalDate fechaDevolucion, @RequestParam Libro libro, @RequestParam Cliente cliente) {
        prestamoService.modificar(id, fechaPrestamo, fechaDevolucion, libro, cliente);
        return new RedirectView("/prestamos");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id){
        prestamoService.eliminar(id);
        return new RedirectView("/prestamos");
    }

}
