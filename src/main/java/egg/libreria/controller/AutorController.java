package egg.libreria.controller;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Autor;

import egg.libreria.service.AutorService;
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
@RequestMapping("/autores")
public class AutorController {
    
    @Autowired
    private AutorService autorService;
    
    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("autores");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("autores", autorService.buscarTodos());
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearAutor(){
        ModelAndView mav = new ModelAndView("autor-formulario");

        //Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        /*if(flashMap != null){
            mav.addObject("nombre", flashMap.get("nombre"));
        }*/
        mav.addObject("autor", new Autor());
        mav.addObject("title", "Crear Autor");
        mav.addObject("action", "guardar");
        return mav;
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editarAutor(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("autor-formulario");
        try {
            Autor autor = autorService.buscarPorId(id);
            mav.addObject("autor", autor);
        } catch(MiException e) {
            mav.addObject("error", e.getMessage());
        }

        mav.addObject("title", "Editar Autor");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/autores");
        try {
            autorService.crear(nombre);
            redirectAttributes.addFlashAttribute("exito", "El autor se registró correctamente.");
        } catch (MiException e){
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/autores/crear");
        }
        return redirectView;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam String nombre, RedirectAttributes redirectAttributes){
        try {
            autorService.modificar(id, nombre);
            redirectAttributes.addFlashAttribute("exito", "El autor se modificó correctamente");
        } catch(MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/autores");
    }
    
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            autorService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El autor se elimino correctamente.");
        } catch(MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/autores");
    }
}
