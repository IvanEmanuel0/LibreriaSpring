package egg.libreria.controller;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Autor;
import egg.libreria.model.entity.Editorial;
import egg.libreria.model.entity.Libro;
import egg.libreria.service.AutorService;
import egg.libreria.service.EditorialService;
import egg.libreria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private AutorService autorService;

    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("libros");
        Map<String, ?> flashMap= RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "eliminar");
        mav.addObject("titulo", "Lista de Libros");
        mav.addObject("libros", libroService.buscarHabilitados());
        return mav;
    }

    @GetMapping("/deshabilitados")
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("libros");
        Map<String, ?> flashMap= RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Libros de Baja");
        mav.addObject("libros", libroService.buscarDeshabilitados());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearLibro(){
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", new Libro());
        mav.addObject("editoriales", editorialService.buscarHabilitados());
        mav.addObject("autores", autorService.buscarHabilitados());
        mav.addObject("titulo", "Crear Libro");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLibro(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("libro-formulario");
        try {
            mav.addObject("libro", libroService.buscarPorId(id));
        } catch(MiException e) {
            mav.addObject("error", e.getMessage());
        }
        mav.addObject("editoriales", editorialService.buscarHabilitados());
        mav.addObject("autores", autorService.buscarHabilitados());
        mav.addObject("titulo", "Editar Libro");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Autor autor, @RequestParam Editorial editorial, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/libros");
        try {
            libroService.crear(isbn, titulo, anio, ejemplares, autor, editorial);
            redirectAttributes.addFlashAttribute("exito", "El libro se registro correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("isbn",isbn);
            redirectAttributes.addFlashAttribute("titulo", titulo);
            redirectAttributes.addFlashAttribute("anio", anio);
            redirectAttributes.addFlashAttribute("ejemplares", ejemplares);
            redirectAttributes.addFlashAttribute("editorial", editorial);
            redirectAttributes.addFlashAttribute("autor", autor);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/libros/crear");
        }
        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Autor autor, @RequestParam Editorial editorial, RedirectAttributes redirectAttributes){
        try {
            libroService.modificar(id, isbn, titulo, anio, ejemplares, autor, editorial);
            redirectAttributes.addFlashAttribute("existe", "El libro se modificó correctamente");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/libros");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            libroService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El libro se eliminó correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/libros");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            libroService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "El libro se eliminó correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/libros/deshabilitados");
    }

}
