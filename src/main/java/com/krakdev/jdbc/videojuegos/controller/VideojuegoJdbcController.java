package com.krakdev.jdbc.videojuegos.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakdev.jdbc.videojuegos.services.ServicioVideojuegoJdbc;
import com.krakdev.videojuegos.entidades.Videojuegos;

@RestController
@RequestMapping("/jdbc/videojuegos")
public class VideojuegoJdbcController {

    @Autowired
    private ServicioVideojuegoJdbc service;

    @PostMapping
    public void crear(
            @RequestBody Videojuegos v
    ) {

        service.crear(v);
    }

    @GetMapping("/{codigo}")
    public Videojuegos buscar(
            @PathVariable String codigo
    ) {

        return service.buscarPorCodigo(codigo);
    }

    @GetMapping
    public ArrayList<Videojuegos> listar() {

        return service.listar();
    }

    @PutMapping
    public void actualizar(
            @RequestBody Videojuegos v
    ) {

        service.actualizar(v);
    }

    @DeleteMapping("/{codigo}")
    public void eliminar(
            @PathVariable String codigo
    ) {

        service.eliminar(codigo);
    }
}