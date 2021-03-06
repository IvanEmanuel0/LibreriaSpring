package egg.libreria.controller;

import egg.libreria.exception.MiException;
import egg.libreria.model.entity.Cliente;
import egg.libreria.model.entity.Usuario;
import egg.libreria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("clientes");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "eliminar");
        mav.addObject("titulo", "Lista de clientes");
        mav.addObject("clientes", clienteService.buscarHabilitados());
        return mav;
    }

    @GetMapping("/deshabilitados")
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("clientes");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Lista de clientes deshabilitados");
        mav.addObject("clientes", clienteService.buscarDeshabilitados());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearCliente(){
        ModelAndView mav = new ModelAndView("cliente-formulario");
        //mav.addObject("cliente", new Cliente());
        mav.addObject("titulo", "Crear cliente");
        mav.addObject("accion", "guardar");
        //if (principal != null) mav.setViewName("redirect:/");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarCliente(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("cliente-formulario");
        try {
            mav.addObject("cliente", clienteService.buscarPorId(id));
        } catch (MiException e) {
            mav.addObject("error", e.getMessage());
        }
        mav.addObject("titulo", "Editar cliente");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String username, @RequestParam String clave, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/clientes");
        try {
            clienteService.crear(dni, nombre, apellido, telefono, username, clave);
            redirectAttributes.addFlashAttribute("exito", "El cliente se registr?? correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("dni", dni);
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("apellido", apellido);
            redirectAttributes.addFlashAttribute("telefono", telefono);
            redirectView.setUrl("/clientes/crear");
        }
        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/clientes");
        try {
            clienteService.modificar(id, dni, nombre, apellido, telefono);
            redirectAttributes.addFlashAttribute("exito", "El cliente se modific?? correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return redirectView;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            clienteService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El cliente se elimin?? correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/clientes");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            clienteService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "El cliente se elimin?? correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/clientes/deshabilitados");
    }


}
