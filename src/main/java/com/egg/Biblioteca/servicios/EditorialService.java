package com.egg.Biblioteca.servicios;

import com.egg.Biblioteca.entidades.Editorial;
import com.egg.Biblioteca.excepciones.MiExcepcion;
import com.egg.Biblioteca.repositorios.EditorialRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {

    @Autowired
    public EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiExcepcion{
        if (nombre.isEmpty() || nombre == null){
            throw new MiExcepcion("Nombre no puede estar vacio o ser nulo");
        }

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listaEditoriales(){
        List<Editorial> editoriales =  new ArrayList<>();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }


    public void modificarEditorial(String idEditorial, String nombre)throws MiExcepcion{

        validar(idEditorial,nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(idEditorial);

        if (respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }

    }

    public Editorial getOne(String idEditorial){
        return editorialRepositorio.getOne(idEditorial);
    }

    public void validar(String idEditorial, String nombre)throws MiExcepcion{
        if (idEditorial.isEmpty() || idEditorial == null){
            throw new MiExcepcion("idEditorial no puede estar vacio o ser nulo");
        }
        if (nombre.isEmpty() || nombre == null){
            throw new MiExcepcion("Nombre no puede estar vacio o ser nulo");
        }
    }
}
