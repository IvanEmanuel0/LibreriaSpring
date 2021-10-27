package egg.libreria.controller;

import egg.libreria.model.entity.Editorial;
import egg.libreria.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/editoriales")
public class EditorialController {
    
    @Autowired
    private EditorialService editorialService;
    
    @GetMapping
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("editoriales");
        mav.addObject("titulo", "Lista de Editoriales");
        mav.addObject("editoriales", editorialService.buscarTodos());
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearAutor(){
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", new Editorial());
        mav.addObject("titulo", "Crear Editorial");
        mav.addObject("accion", "guardar");
        return mav;
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editarEditorial(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", editorialService.buscarPorId(id));
        mav.addObject("titulo", "Editar Editorial");
        mav.addObject("accion", "modificar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre){
        editorialService.crear(nombre);
        return new RedirectView("/editoriales");
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam String nombre){
        editorialService.modificar(id, nombre);
        return new RedirectView("/editoriales");
    }
    
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id){
        editorialService.eliminar(id);
        return new RedirectView("/editoriales");
    }
}
