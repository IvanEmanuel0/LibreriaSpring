package egg.libreria.controller;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Usuario;
import egg.libreria.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("usuarios");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("usuarios", usuarioService.buscarHabilitados());
        return mav;
    }

    @GetMapping("/deshabilitados")
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("usuarios");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("usuarios", usuarioService.buscarDeshabilitados());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearUsuario() {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        mav.addObject("usuario", new Usuario());
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarUsuario(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("usuario-formulario");
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            mav.addObject("usuario", usuario);
        } catch (MiException e) {
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String username, @RequestParam String clave, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/usuarios");
        try {
            usuarioService.crear(username, clave);
            redirectAttributes.addFlashAttribute("exito", "La cuenta fue creada correctamente.");
        } catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("usuario", username);
            redirectView.setUrl("/usuarios/crear");
        }
        return  redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam String username, @RequestParam String clave, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.modificar(id, username, clave);
            redirectAttributes.addFlashAttribute("exito", "La cuenta se modifico correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/usuarios");
    }

    @PostMapping("eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "La cuenta fue eliminada correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/usuarios");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "La cuenta se reactivo correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/usuarios/deshabilitados");
    }


}
