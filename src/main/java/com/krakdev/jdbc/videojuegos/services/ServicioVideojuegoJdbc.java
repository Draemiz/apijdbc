package com.krakdev.jdbc.videojuegos.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.krakdev.jdbc.videojuegos.VideojuegoJdbc;
import com.krakdev.videojuegos.entidades.Videojuegos;

@Service
public class ServicioVideojuegoJdbc {

    private VideojuegoJdbc jdbc =
            new VideojuegoJdbc();

    public void crear(Videojuegos v) {

        jdbc.insertar(v);
    }

    public Videojuegos buscarPorCodigo(
            String codigo
    ) {

        return jdbc.buscar(codigo);
    }

    public ArrayList<Videojuegos> listar() {

        return jdbc.listar();
    }

    public void actualizar(Videojuegos v) {

        jdbc.actualizar(v);
    }

    public void eliminar(String codigo) {

        jdbc.eliminar(codigo);
    }
}
