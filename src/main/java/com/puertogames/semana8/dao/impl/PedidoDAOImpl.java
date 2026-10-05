package com.puertogames.semana8.dao.impl;

import com.puertogames.semana8.dao.PedidoDAO;
import com.puertogames.semana8.modelo.EstadoPedido;
import com.puertogames.semana8.modelo.Pedido;
import com.puertogames.semana8.modelo.TipoPedido;
import com.puertogames.semana8.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Implementación JDBC de PedidoDAO: CRUD sobre la tabla pedidos
public class PedidoDAOImpl implements PedidoDAO {

    private static final Logger LOGGER = Logger.getLogger(PedidoDAOImpl.class.getName());

    // Inserta un pedido nuevo
    @Override
    public void create(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipoPedido().name());
            ps.setString(3, pedido.getEstado().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al ingresar pedido", e);
            throw e;
        }
    }

    // Retorna todos los pedidos
    @Override
    public List<Pedido> readAll() throws SQLException {
        List<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                listaPedidos.add(new Pedido(rs.getInt("id"), rs.getString("direccion"), TipoPedido.valueOf(rs.getString("tipo")), EstadoPedido.valueOf(rs.getString("estado"))));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al traer lista de pedidos", e);
            throw e;
        }
        return listaPedidos;
    }

    // Retorna los pedidos con el estado indicado
    @Override
    public List<Pedido> readByEstado(EstadoPedido estadoPedido) throws SQLException {
        List<Pedido> listaPedidosPorEstado = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos WHERE estado=?";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, estadoPedido.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listaPedidosPorEstado.add(new Pedido(rs.getInt("id"), rs.getString("direccion"), TipoPedido.valueOf(rs.getString("tipo")), EstadoPedido.valueOf(rs.getString("estado"))));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar pedidos por estado", e);
            throw e;
        }
        return listaPedidosPorEstado;
    }

    // Retorna los pedidos del tipo indicado
    @Override
    public List<Pedido> readByTipo(TipoPedido tipoPedido) throws SQLException {
        List<Pedido> listaPedidosPorTipo = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos WHERE tipo=?";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, tipoPedido.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listaPedidosPorTipo.add(new Pedido(rs.getInt("id"), rs.getString("direccion"), TipoPedido.valueOf(rs.getString("tipo")), EstadoPedido.valueOf(rs.getString("estado"))));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar pedidos por tipo", e);
            throw e;
        }
        return listaPedidosPorTipo;
    }

    // Actualiza los datos de un pedido según su id
    @Override
    public void update(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedidos SET direccion=?, tipo=?, estado=? WHERE id=?";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipoPedido().name());
            ps.setString(3, pedido.getEstado().name());
            ps.setInt(4, pedido.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar pedido", e);
            throw e;
        }
    }

    // Elimina el pedido con el id indicado
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM pedidos WHERE id=?";
        try (Connection connection = ConexionDB.conectar(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar un pedido", e);
            throw e;
        }
    }
}
