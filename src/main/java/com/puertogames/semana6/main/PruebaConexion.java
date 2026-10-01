package com.puertogames.semana6.main;

import com.puertogames.semana6.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;

// Prueba la conexion JDBC usando try-catch-finally (Paso 2)
public class PruebaConexion {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = ConexionBD.conectar();
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        } finally {
            ConexionBD.cerrar(connection);
            System.out.println("Conexion cerrada");
        }
    }
}