package egg.libreria.controller;

import egg.libreria.model.entity.Cliente;
import egg.libreria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("clientes");
        mav.addObject("clientes", clienteService.buscarTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearCliente(){
        ModelAndView mav = new ModelAndView("cliente-formulario");
        mav.addObject("cliente", new Cliente());
        mav.addObject("titulo", "Crear cliente");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarCliente(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("cliente-formulario");
        mav.addObject("cliente", clienteService.buscarPorId(id));
        mav.addObject("titulo", "Editar cliente");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono){
        clienteService.crear(dni, nombre, apellido, telefono);
        return new RedirectView("/clientes");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono){
        clienteService.modificar(id, dni, nombre, apellido, telefono);
        return new RedirectView("/clientes");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id){
        clienteService.eliminar(id);
        return new RedirectView("/clientes");
    }



}
