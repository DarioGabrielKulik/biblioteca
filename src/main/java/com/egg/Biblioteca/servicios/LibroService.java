package com.egg.Biblioteca.servicios;

import com.egg.Biblioteca.entidades.Autor;
import com.egg.Biblioteca.entidades.Editorial;
import com.egg.Biblioteca.entidades.Libro;
import com.egg.Biblioteca.excepciones.MiExcepcion;
import com.egg.Biblioteca.repositorios.AutorRepositorio;
import com.egg.Biblioteca.repositorios.EditorialRepositorio;
import com.egg.Biblioteca.repositorios.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepcion {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();


        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibro(){
        List<Libro> libros = new ArrayList<>();
        libros =  libroRepositorio.findAll();
        return libros;
    }


    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)throws MiExcepcion{

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }
        if (respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }
        if(respuestaLibro.isPresent()){
            Libro libro = respuestaLibro.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroRepositorio.save(libro);
        }
    }

    public Libro getOne(Long isbn){
        return libroRepositorio.getOne(isbn);
    }

    public void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)throws MiExcepcion{


        if (isbn == null){
            throw new MiExcepcion("el isbn no puede ser nulo");
        }
        if (titulo.isEmpty() || titulo == null){
            throw new MiExcepcion("El titulo no puede estar vacio ni ser nulo");
        }
        if (ejemplares == null){
            throw new MiExcepcion("Los ejemplares no puede ser nulo o estar vacio");
        }
        if (idAutor.isEmpty() || idAutor == null){
            throw new MiExcepcion("Autor no puede estar vacio ni ser nulo");
        }
        if (idEditorial.isEmpty() || idEditorial == null){
            throw new MiExcepcion("Editorial no pùede estar vacio ni ser nulo");
        }
    }
}
