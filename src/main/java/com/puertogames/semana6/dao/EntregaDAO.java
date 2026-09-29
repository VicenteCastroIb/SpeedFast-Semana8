package com.puertogames.semana6.dao;

import com.puertogames.semana6.modelo.Entrega;

import javax.swing.*;
import java.sql.*;

public class EntregaDAO {

    public void guardar(Entrega entrega) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, entrega.getPedido().getId());
            ps.setInt(2, entrega.getRepartidor().getId());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la entrega en la base de datos.");
        }
    }
}