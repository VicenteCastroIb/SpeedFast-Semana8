package com.puertogames.semana6.dao.impl;

import com.puertogames.semana6.dao.RepartidorDAO;
import com.puertogames.semana6.modelo.Repartidor;
import com.puertogames.semana6.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Implementación JDBC de RepartidorDAO.
 * Ejecuta las operaciones CRUD sobre la tabla repartidores.
 */
public class RepartidorDAOImpl implements RepartidorDAO{

    private static final Logger LOGGER = Logger.getLogger(RepartidorDAOImpl.class.getName());

    // Inserta un repartidor nuevo
    @Override
    public void create(Repartidor repartidor) throws SQLException {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,repartidor.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al registrar repartidor", e);
            throw e;
        }
    }

    // Retorna todos los repartidores
    @Override
    public List<Repartidor> readAll() throws SQLException {
        List<Repartidor> listaRepartidores = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidores";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                listaRepartidores.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al traer repartidores", e);
            throw e;
        }
        return listaRepartidores;
    }

    // Actualiza el nombre de un repartidor según su id
    @Override
    public void update(Repartidor repartidor) throws SQLException {
        String sql = "UPDATE repartidores SET nombre=? WHERE id=?";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, repartidor.getNombre());
            ps.setInt(2, repartidor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al editar repartidor", e);
            throw e;
        }
    }

    // Elimina el repartidor con el id indicado
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM repartidores WHERE id=?";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar repartidor", e);
            throw e;
        }
    }
}
