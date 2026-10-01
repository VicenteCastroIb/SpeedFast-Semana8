package com.puertogames.semana6.dao;

import com.puertogames.semana6.modelo.Entrega;

import java.sql.SQLException;
import java.util.List;

// Operaciones CRUD para entidad Entrega
public interface EntregaDAO {

    // Inserta una Entrega nueva
    void create(Entrega entrega) throws SQLException;

    // Retorno lista de todas las Entregas
    List<Entrega> readAll() throws SQLException;

    // Retorno las Entregas asociadas a un Pedido
    List<Entrega> readByPedido(int idPedido) throws SQLException;

    // Retorno las Entregas realizadas por un Repartidor
    List<Entrega> readByRepartidor(int idRepartidor) throws SQLException;

    // Actualizo objeto Entrega
    void update(Entrega entrega) throws SQLException;

    // Borro objeto Entrega
    void delete(int id) throws SQLException;

}