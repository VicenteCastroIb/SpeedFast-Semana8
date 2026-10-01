package com.puertogames.semana6.dao.impl;

import com.puertogames.semana6.dao.EntregaDAO;
import com.puertogames.semana6.modelo.*;
import com.puertogames.semana6.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación JDBC de EntregaDAO.
 * Gestiona las operaciones CRUD sobre la tabla entregas, que asocia
 * un Pedido con un Repartidor en una fecha y hora determinadas.
 */
public class EntregaDAOImpl implements EntregaDAO {

    private static final Logger LOGGER = Logger.getLogger(EntregaDAOImpl.class.getName());
    // SELECT base con JOIN, reutilizado por readAll, readByPedido y readByRepartidor
    private static final String SELECT_BASE =
            "SELECT e.id AS id_entrega, e.fecha, e.hora, " +
                    "p.id AS id_pedido, p.direccion, p.tipo, p.estado, " +
                    "r.id AS id_repartidor, r.nombre " +
                    "FROM entregas e " +
                    "JOIN pedidos p ON p.id = e.id_pedido " +
                    "JOIN repartidores r ON r.id = e.id_repartidor";


    /**
     * Registra una nueva entrega en la base de datos.
     * Guarda solo los id del pedido y del repartidor (claves foráneas).
     */
    @Override
    public void create(Entrega entrega) throws SQLException {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConexionBD.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entrega.getPedido().getId());
            ps.setInt(2, entrega.getRepartidor().getId());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al agregar entrega", e);
            throw e;
        }
    }

    /**
     * Retorna todas las entregas con los datos de su pedido y repartidor,
     * ordenadas de la más reciente a la más antigua.
     */
    @Override
    public List<Entrega> readAll() throws SQLException {
        List<Entrega> listaEntregas = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY e.fecha DESC, e.hora DESC";
        try (Connection connection = ConexionBD.conectar() ; PreparedStatement ps = connection.prepareStatement(sql) ; ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                listaEntregas.add(mapearEntrega(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar entregas", e);
            throw e;
        }
        return listaEntregas;
    }

    /**
     * Retorna las entregas asociadas al pedido indicado.
     */
    @Override
    public List<Entrega> readByPedido(int idPedido) throws SQLException {
        List<Entrega> listaEntregasPorPedido = new ArrayList<>();
        String sql = SELECT_BASE + " WHERE p.id=? ORDER BY e.fecha DESC, e.hora DESC";
        try (Connection connection = ConexionBD.conectar() ; PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    listaEntregasPorPedido.add(mapearEntrega(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar entregas por pedidos", e);
            throw e;
        }
        return listaEntregasPorPedido;
    }

    /**
     * Retorna las entregas realizadas por el repartidor indicado.
     */
    @Override
    public List<Entrega> readByRepartidor(int idRepartidor) throws SQLException {
        List<Entrega> listaEntregaPorRepartidor = new ArrayList<>();
        String sql = SELECT_BASE + " WHERE r.id=? ORDER BY e.fecha DESC, e.hora DESC";
        try (Connection connection = ConexionBD.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRepartidor);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listaEntregaPorRepartidor.add(mapearEntrega(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar entregas por repartidor", e);
            throw e;
        }
        return listaEntregaPorRepartidor;
    }

    /**
     * Actualiza el pedido, repartidor, fecha y hora de una entrega existente
     * identificada por su id.
     */
    @Override
    public void update(Entrega entrega) throws SQLException {
        String sql = "UPDATE entregas SET id_pedido=?, id_repartidor=?, fecha=?, hora=? WHERE id=?";
        try(Connection connection = ConexionBD.conectar() ; PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entrega.getPedido().getId());
            ps.setInt(2, entrega.getRepartidor().getId());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));
            ps.setInt(5, entrega.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar entrega", e);
            throw e;
        }
    }

    /**
     * Elimina la entrega con el id indicado.
     */
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM entregas WHERE id=?";
        try (Connection connection = ConexionBD.conectar() ; PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al borrar entrega", e);
            throw e;
        }
    }

    /**
     * Convierte la fila actual del ResultSet en una Entrega,
     * construyendo también su Pedido y Repartidor a partir del JOIN.
     */
    private Entrega mapearEntrega(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido(
                rs.getInt("id_pedido"),
                rs.getString("direccion"),
                TipoPedido.valueOf(rs.getString("tipo")),
                EstadoPedido.valueOf(rs.getString("estado"))
        );

        Repartidor repartidor = new Repartidor(
                rs.getInt("id_repartidor"),
                rs.getString("nombre")
        );

        return new Entrega(
                rs.getInt("id_entrega"),
                pedido,
                repartidor,
                rs.getDate("fecha").toLocalDate(),
                rs.getTime("hora").toLocalTime()
        );
    }
}
