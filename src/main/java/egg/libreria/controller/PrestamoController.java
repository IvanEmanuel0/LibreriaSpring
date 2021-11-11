package egg.libreria.controller;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Cliente;
import egg.libreria.model.entity.Libro;
import egg.libreria.model.entity.Prestamo;
import egg.libreria.service.ClienteService;
import egg.libreria.service.LibroService;
import egg.libreria.service.PrestamoService;
import egg.libreria.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

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
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("prestamos");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "eliminar");
        mav.addObject("titulo", "Lista de Prestamos");
        mav.addObject("prestamos", prestamoService.buscarHabilitados());
        return mav;
    }

    @GetMapping("/deshabilitados")
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("prestamos");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Lista de Prestamos deshabilitados");
        mav.addObject("prestamos", prestamoService.buscarDeshabilitados());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearPrestamo() {
        ModelAndView mav = new ModelAndView("prestamo-formulario");
        mav.addObject("prestamo", new Prestamo());
        mav.addObject("libros", libroService.buscarHabilitados());
        mav.addObject("clientes", clienteService.buscarHabilitados());
        mav.addObject("titulo", "Crear Prestamo");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarPrestamo(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("prestamo-formulario");
        try {
            mav.addObject("prestamo", prestamoService.buscarPorId(id));
        } catch (MiException e) {
            mav.addObject("error", e.getMessage());
        }
        mav.addObject("libros", libroService.buscarHabilitados());
        mav.addObject("clientes", clienteService.buscarHabilitados());
        mav.addObject("titulo", "Editar Prestamo");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaPrestamo, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDevolucion, @RequestParam Libro libro, @RequestParam Cliente cliente, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/prestamos");
        try {
            prestamoService.crear(fechaPrestamo, fechaDevolucion, libro, cliente);
            redirectAttributes.addFlashAttribute("exito", "El prestamo se efectuó de manera exitosa.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/prestamos/crear");
        }
        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaPrestamo, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDevolucion, @RequestParam Libro libro, @RequestParam Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            prestamoService.modificar(id, fechaPrestamo, fechaDevolucion, libro, cliente);
            redirectAttributes.addFlashAttribute("exito", "El prestamo se modificó correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/prestamos");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            prestamoService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El prestamo se dio de alta correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/prestamos");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            prestamoService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "El prestamo se dio de baja correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/prestamos/deshabilitados");
    }

}
