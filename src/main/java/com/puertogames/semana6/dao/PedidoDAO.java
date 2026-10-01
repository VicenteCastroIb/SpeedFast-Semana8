package com.puertogames.semana6.dao;

import com.puertogames.semana6.modelo.EstadoPedido;
import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.TipoPedido;

import java.sql.SQLException;
import java.util.List;


// Operaciones CRUD para entidad Pedido
public interface PedidoDAO {

    // Inserta un Pedido nuevo
    void create(Pedido pedido) throws SQLException;

    // Retorno lista de todos los Pedidos
    List<Pedido> readAll() throws SQLException;

    // Retorno los Pedidos con el estado indicado
    List<Pedido> readByEstado(EstadoPedido estadoPedido) throws SQLException;

    // Retorno los Pedidos del tipo indicado
    List<Pedido> readByTipo(TipoPedido tipoPedido) throws SQLException;

    // Actualizo objeto Pedido
    void update(Pedido pedido) throws SQLException;

    // Borro objeto Pedido
    void delete(int id) throws SQLException;
}