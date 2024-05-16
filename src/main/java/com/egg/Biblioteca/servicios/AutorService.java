package com.egg.Biblioteca.servicios;

import com.egg.Biblioteca.entidades.Autor;
import com.egg.Biblioteca.excepciones.MiExcepcion;
import com.egg.Biblioteca.repositorios.AutorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre)throws MiExcepcion{
        if (nombre.isEmpty() || nombre == null){
            throw new MiExcepcion("Nombre no puede ser nulo o estar vacio");
        }

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList<>();
        autores = autorRepositorio.findAll();
        return autores;
    }


    public void modificarAutor(String idAutor, String nombre)throws MiExcepcion{

        validar(idAutor, nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(idAutor);

        if (respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }

    public Autor getOne(String idAutor){
        return autorRepositorio.getOne(idAutor);
    }


    public void validar(String idAutor, String nombre)throws MiExcepcion{
        if (idAutor.isEmpty() || idAutor == null){
            throw new MiExcepcion("idAutor no puede estar vacio o ser nulo");
        }
        if (nombre.isEmpty() || nombre == null){
            throw new MiExcepcion("Nombre no puede estar vacio o ser nulo");
        }
    }
}
