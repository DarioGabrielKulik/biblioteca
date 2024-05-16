package com.egg.Biblioteca.controladores;

import com.egg.Biblioteca.entidades.Autor;
import com.egg.Biblioteca.excepciones.MiExcepcion;
import com.egg.Biblioteca.servicios.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorService autorService;

    @GetMapping("/registrar")
    public String formulario(){
        return "autor_formulario.html";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo){
       List<Autor> autores = autorService.listarAutores();
       modelo.addAttribute("autores", autores);
       return "listar_autores";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre){

        try {
            autorService.crearAutor(nombre);
        } catch (MiExcepcion e) {
           // throw new RuntimeException(e);
            return "autor_formulario.html";
        }
        return "autor_formulario.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        Autor autor = autorService.getOne(id);
        modelo.addAttribute("autor",autor);
        return "modificar_autor.html";
    }

    @PostMapping("/modificar/{idAutor}")
    public String modificar(@PathVariable String idAutor, @RequestParam String nombre, ModelMap modelo){
        try {
            autorService.modificarAutor(idAutor, nombre);
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            Autor autor = autorService.getOne(idAutor);
            modelo.addAttribute("autor",autor);
            return "modificar_autor.html";
        }
        return "redirect:../listar";
    }
}
