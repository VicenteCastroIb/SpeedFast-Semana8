package com.puertogames.semana8.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Entrega la conexión a la base de datos MySQL de SpeedFast
public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USER = "root";
    private static final String PASSWORD = "speed";

    // Abre y retorna una conexión nueva
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
