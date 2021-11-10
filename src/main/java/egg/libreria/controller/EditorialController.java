package egg.libreria.controller;

import egg.libreria.exception.MiException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/editoriales")
public class EditorialController {
    
    @Autowired
    private EditorialService editorialService;
    
    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("editoriales");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("titulo", "Lista de Editoriales");
        mav.addObject("editoriales", editorialService.buscarTodos());
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearAutor(){
        ModelAndView mav = new ModelAndView("editorial-formulario");

        /*Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("nombre", flashMap.get("nombre"));
        }*/ 

        mav.addObject("editorial", new Editorial());
        mav.addObject("titulo", "Crear Editorial");
        mav.addObject("accion", "guardar");
        return mav;
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editarEditorial(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("editorial-formulario");
        try {
            mav.addObject("editorial", editorialService.buscarPorId(id));
        } catch(MiException e) {
            mav.addObject("error", e.getMessage());
        }
        mav.addObject("titulo", "Editar Editorial");
        mav.addObject("accion", "modificar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/editoriales");
        try {
            editorialService.crear(nombre);
            redirectAttributes.addFlashAttribute("exito", "La editorial se creó correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/editoriales/crear");
        }
        return redirectView;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam String nombre, RedirectAttributes redirectAttributes){
        try {
            editorialService.modificar(id, nombre);
            redirectAttributes.addFlashAttribute("exito", "La editorial se modificó correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/editoriales");
    }
    
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            editorialService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "La editorial se eliminó correctamente.");
        } catch(MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/editoriales");
    }
}
