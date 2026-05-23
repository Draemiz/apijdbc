package com.krakdev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krakdev.jdbc.Conexion;
import com.krakdev.videojuegos.entidades.Videojuegos;

public class VideojuegoJdbc {

    private static final Logger log =
            LoggerFactory.getLogger(VideojuegoJdbc.class);

    private static final String SQL_INSERT =
            "INSERT INTO videojuegos (codigo,nombre,plataforma,precio,disponible,genero) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_BUSCAR_POR_CODIGO =
            "SELECT nombre, plataforma, precio, disponible, genero "
            + "FROM videojuegos WHERE codigo = ?";

    private static final String SQL_LISTAR =
            "SELECT codigo, nombre, plataforma, precio, disponible, genero "
            + "FROM videojuegos";

    private static final String SQL_ACTUALIZAR =
            "UPDATE videojuegos SET nombre = ?, plataforma = ?, precio = ?, "
            + "disponible = ?, genero = ? "
            + "WHERE codigo = ?";

    private static final String SQL_ELIMINAR =
            "DELETE FROM videojuegos WHERE codigo = ?";

    private void cerrar(PreparedStatement ps,Connection conn ) {

        try { if(ps != null) ps.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        try { if(conn != null) conn.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public void insertar(Videojuegos v) {
        Connection conn = null;
        PreparedStatement ps = null;

        try { Videojuegos vExistente = buscar(v.getCodigo());
            if(vExistente == null) {
                conn = Conexion.getConnection();
                ps = conn.prepareStatement(SQL_INSERT);

                ps.setString(1, v.getCodigo());
                ps.setString(2, v.getNombre());
                ps.setString(3, v.getPlataforma());
                ps.setDouble(4, v.getPrecio());
                ps.setBoolean(5, v.isDisponible());
                ps.setString(6, v.getGenero());

                int filas = ps.executeUpdate();

                log.info("Videojuego insertado. Filas afectadas: {}",filas);

            } else {
                throw new RuntimeException("Esta tratando de insertar "
                        + "un codigo que ya existe");
            }

        } catch (SQLException e) {log.error(
        		"Error al insertar videojuego: {}", e.getMessage());

            throw new RuntimeException(
                    "Error general al insertar videojuego");

        } finally {
            cerrar(ps, conn);
        }
    }

    public Videojuegos buscar(String codigo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try { Videojuegos v = new Videojuegos();
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_BUSCAR_POR_CODIGO);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                v.setCodigo(codigo);
                v.setNombre(rs.getString(1));
                v.setPlataforma(rs.getString(2));
                v.setPrecio(rs.getDouble(3));
                v.setDisponible(rs.getBoolean(4));
                v.setGenero(rs.getString(5));

            } else {
                return null;
            }

            log.info("Videojuego encontrado");
            return v;

        } catch (SQLException e) {
            log.error(
                    "Error al buscar videojuego: {}", e.getMessage());

            throw new RuntimeException(
                    "Error general al buscar videojuego");

        } finally {
            cerrar(ps, conn);
        }
    }

    public ArrayList<Videojuegos> listar() {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ArrayList<Videojuegos> lista = new ArrayList<Videojuegos>();
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_LISTAR);
            rs = ps.executeQuery();
            while(rs.next()) {

                Videojuegos v = new Videojuegos();

                v.setCodigo(rs.getString(1));
                v.setNombre(rs.getString(2));
                v.setPlataforma(rs.getString(3));
                v.setPrecio(rs.getDouble(4));
                v.setDisponible(rs.getBoolean(5));
                v.setGenero(rs.getString(6));

                lista.add(v);
            }

            if(lista.isEmpty()) {
                throw new RuntimeException("No existe ningun videojuego");
            }

            log.info("Videojuegos encontrados");
            return lista;

        } catch (SQLException e) {

            log.error("Error al listar videojuegos: {}",e.getMessage());

            throw new RuntimeException("Error general al listar videojuegos");

        } finally {
            cerrar(ps, conn);
        }
    }

    public void actualizar(Videojuegos v) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            Videojuegos vExistente = buscar(v.getCodigo());
            if(vExistente != null) {

                conn = Conexion.getConnection();
                ps = conn.prepareStatement(SQL_ACTUALIZAR);

                ps.setString(1, v.getNombre());
                ps.setString(2, v.getPlataforma());
                ps.setDouble(3, v.getPrecio());
                ps.setBoolean(4, v.isDisponible());
                ps.setString(5, v.getGenero());
                ps.setString(6, v.getCodigo());

                int filas = ps.executeUpdate();

                log.info("Videojuego actualizado. Filas afectadas: {}", filas);

            } else {
                throw new RuntimeException("No existe videojuego "
                        + "con ese codigo");
            }

        } catch (SQLException e) {
            log.error("Error al actualizar videojuego: {}", e.getMessage());
            throw new RuntimeException("Error general al actualizar videojuego");

        } finally {
            cerrar(ps, conn);
        }
    }

    public void eliminar(String codigo) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            Videojuegos vExistente = buscar(codigo);

            if(vExistente != null) {
                conn = Conexion.getConnection();
                ps = conn.prepareStatement(SQL_ELIMINAR);
                ps.setString(1, codigo);
                int filas = ps.executeUpdate();

                log.info("Videojuego eliminado. Filas afectadas: {}", filas);

            } else {
                throw new RuntimeException("No existe videojuego con ese codigo");
            }

        } catch (SQLException e) {

            log.error("Error al eliminar videojuego: {}", e.getMessage());
            throw new RuntimeException("Error general al eliminar videojuego");

        } finally {
        	cerrar(ps, conn);
        }
    }
}
