package com.egg.Biblioteca.controladores;

import com.egg.Biblioteca.entidades.Editorial;
import com.egg.Biblioteca.excepciones.MiExcepcion;
import com.egg.Biblioteca.servicios.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialService editorialService;

    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_formulario.html";
    }
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre){
        try {
            editorialService.crearEditorial(nombre);
        } catch (MiExcepcion e) {
            return "editorial_formulario.html";
        }
        return "editorial_formulario.html";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        List<Editorial> editoriales = editorialService.listaEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "listar_editoriales";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        Editorial editorial = editorialService.getOne(id);
        modelo.put("editorial", editorial);
        return "modificar_editorial.html";
    }

    @PostMapping("/modificar/{idEditorial}")
    public String modificar(@PathVariable String idEditorial,@RequestParam String nombre, ModelMap modelo){
        try {
            editorialService.modificarEditorial(idEditorial,nombre);
        } catch (MiExcepcion e) {
            Editorial editorial = editorialService.getOne(idEditorial);
            modelo.put("editorial", editorial);
            modelo.put("error", e.getMessage());
            return "modificar_editorial.html";
        }
        return "redirect:../listar";
    }
}
