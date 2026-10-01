package com.puertogames.semana6.dao;

import com.puertogames.semana6.modelo.Repartidor;

import java.sql.SQLException;
import java.util.List;

// Operaciones CRUD para entidad Repartidor
public interface RepartidorDAO {

    // Inserta un Repartidor nuevo
    void create(Repartidor repartidor) throws SQLException;

    // Retorno lista de todos los Repartidores
    List<Repartidor> readAll() throws SQLException;

    // Actualizo objeto Repartidor
    void update(Repartidor repartidor) throws SQLException;

    // Borro objeto Repartidor
    void delete(int id) throws SQLException;
}