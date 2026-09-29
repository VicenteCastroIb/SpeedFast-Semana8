package com.puertogames.semana6.dao;

import com.puertogames.semana6.modelo.Repartidor;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public List<Repartidor> listarTodos() {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM repartidor";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                lista.add(new Repartidor(id, nombre));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar repartidores desde la base de datos.");
        }
        return lista;
    }
    public void guardar(Repartidor repartidor) {
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, repartidor.getNombre());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el repartidor en la base de datos.");
        }
    }

}