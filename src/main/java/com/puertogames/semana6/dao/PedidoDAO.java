package com.puertogames.semana6.dao;

import com.puertogames.semana6.modelo.EstadoPedido;
import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.Repartidor;
import com.puertogames.semana6.modelo.TipoPedido;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipoPedido().name());
            ps.setString(3, pedido.getEstado().name());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido en la base de datos.");
        }
    }

    // Para listar los pedidos
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        // Subconsulta: trae el nombre del repartidor de la ultima entrega de cada pedido (o NULL si no tiene)
        String sql = "SELECT p.id, p.direccion, p.tipo, p.estado, " +
                "(SELECT r.nombre FROM entrega e " +
                " JOIN repartidor r ON r.id = e.id_repartidor " +
                " WHERE e.id_pedido = p.id " +
                " ORDER BY e.id DESC LIMIT 1) AS repartidor " +
                "FROM pedido p";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                TipoPedido tipo = TipoPedido.valueOf(rs.getString("tipo"));
                EstadoPedido estado = EstadoPedido.valueOf(rs.getString("estado"));
                Pedido pedido = new Pedido(id, direccion, tipo, estado);

                String nombreRepartidor = rs.getString("repartidor");
                if (nombreRepartidor != null) {
                    pedido.setRepartidor(new Repartidor(nombreRepartidor));
                }
                lista.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar pedidos desde la base de datos.");
        }
        return lista;
    }
}