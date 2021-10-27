package egg.libreria.controller;

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
import org.springframework.web.servlet.view.RedirectView;

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
    public ModelAndView mostrarTodos(){
        ModelAndView mav = new ModelAndView("libros");
        mav.addObject("libros", libroService.buscarTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearLibro(){
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", new Libro());
        mav.addObject("editoriales", editorialService.buscarTodos());
        mav.addObject("autores", autorService.buscarTodos());
        mav.addObject("titulo", "Crear Libro");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLibro(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", libroService.buscarPorId(id));
        mav.addObject("editoriales", editorialService.buscarTodos());
        mav.addObject("autores", autorService.buscarTodos());
        mav.addObject("titulo", "Editar Libro");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Autor autor, @RequestParam Editorial editorial){
        libroService.crear(isbn, titulo, anio, ejemplares, autor, editorial);
        return new RedirectView("/libros");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Autor autor, @RequestParam Editorial editorial){
        libroService.modificar(id, isbn, titulo, anio, ejemplares, autor, editorial);
        return new RedirectView("/libros");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id){
        libroService.eliminar(id);
        return new RedirectView("/libros");
    }

}
