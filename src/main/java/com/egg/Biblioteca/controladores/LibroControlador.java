package com.egg.Biblioteca.controladores;

import com.egg.Biblioteca.entidades.Autor;
import com.egg.Biblioteca.entidades.Editorial;
import com.egg.Biblioteca.entidades.Libro;
import com.egg.Biblioteca.excepciones.MiExcepcion;
import com.egg.Biblioteca.servicios.AutorService;
import com.egg.Biblioteca.servicios.EditorialService;
import com.egg.Biblioteca.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        List<Editorial> editoriales = editorialService.listaEditoriales();
        List<Autor> autores = autorService.listarAutores();

        modelo.addAttribute("editoriales", editoriales);
        modelo.addAttribute("autores", autores);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares,
                           @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo){

        try {
            libroService.crearLibro(isbn,titulo,ejemplares,idAutor,idEditorial);
            modelo.put("exito", "Su libro se a cargado correctamente");

        } catch (MiExcepcion ex) {
            List<Editorial> editoriales = editorialService.listaEditoriales();
            List<Autor> autores = autorService.listarAutores();
            modelo.addAttribute("editoriales", editoriales);
            modelo.addAttribute("autores", autores);
            modelo.put("error", ex.getMessage());
            return "libro_form.html";
        }
        return "libro_form.html";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo){
    List<Libro> libros = libroService.listarLibro();
    modelo.addAttribute("libros", libros);
    return "listar_libros.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo){
        Libro libro = libroService.getOne(isbn);
        modelo.put("libro", libro);
        return "modificar_libro.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, @RequestParam String titulo, @RequestParam Integer ejemplares
    , @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo
    ){

        try {
            libroService.modificarLibro(isbn,titulo,ejemplares,idAutor,idEditorial);
        } catch (MiExcepcion e) {
            Libro libro = libroService.getOne(isbn);
            modelo.put("libro", libro);
            modelo.put("error", e.getMessage());
            return "modificar_libro.html";
        }


        return "redirect:../listar";
    }
}
