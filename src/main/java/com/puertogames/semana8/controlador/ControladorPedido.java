package com.puertogames.semana8.controlador;

import com.puertogames.semana8.dao.PedidoDAO;
import com.puertogames.semana8.dao.impl.PedidoDAOImpl;
import com.puertogames.semana8.modelo.EstadoPedido;
import com.puertogames.semana8.modelo.Pedido;
import com.puertogames.semana8.modelo.TipoPedido;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

// Controlador de Pedidos: valida los datos de la vista y delega en PedidoDAO
public class ControladorPedido {

    // Iniciamos su DAO para conversar con BD
    private final PedidoDAO pedidoDAO = new PedidoDAOImpl();


    // Validamos y agregamos pedido
    public void agregarPedido(String direccion, TipoPedido tipo, EstadoPedido estado) throws SQLException {
        // Validamos datos
        validarDatos(direccion, tipo, estado);

        // Creamos objeto pedido
        Pedido pedido = new Pedido(direccion.trim(), tipo, estado);
        // Mandamos pedido a DAO para guardar en BD
        pedidoDAO.create(pedido);
    }
    // Listar todos los pedidos
    public List<Pedido> listarPedidos() throws SQLException {
        return pedidoDAO.readAll();
    }

    // Retorna los pedidos aplicando los filtros opcionales.
    public List<Pedido> listarPedidos(EstadoPedido estado, TipoPedido tipo) throws SQLException {
        List<Pedido> pedidos;
        if (estado != null) {
            pedidos = pedidoDAO.readByEstado(estado);
        } else if (tipo != null) {
            pedidos = pedidoDAO.readByTipo(tipo);
        } else {
            pedidos = pedidoDAO.readAll();
        }
        // Si se eligieron los dos filtros, se aplica el de tipo sobre el resultado
        if (estado != null && tipo != null) {
            pedidos.removeIf(p -> p.getTipoPedido() != tipo);
        }
        return pedidos;
    }

    // Valido y actualizo un pedido existente
    public void editarPedido(int id, String direccion, TipoPedido tipo, EstadoPedido estado) throws SQLException {
        // valido datos
        validarId(id);
        validarDatos(direccion, tipo, estado);
        // Creo objeto pedido
        Pedido pedido = new Pedido(id, direccion.trim(), tipo, estado);
        // Envio pedido a DAO para q lo mande a BD
        pedidoDAO.update(pedido);
    }

    // Elimino el pedido con el id indicado
    public void eliminarPedido(int id) throws SQLException {
        // Validamos id
        validarId(id);
        // Envio a DAO para el Delete
        pedidoDAO.delete(id);
    }

    // Recarga la tabla con los pedidos de la BD, aplicando los filtros
    public void cargarTabla(DefaultTableModel modelo, EstadoPedido estado, TipoPedido tipo) throws SQLException {
        modelo.setRowCount(0); // Vaciamos tabla
        for (Pedido p : listarPedidos(estado, tipo)) { // Recorre lista de la BD
            modelo.addRow(new Object[]{    // Va creando las filas
                    p.getId(),
                    p.getDireccion(),
                    p.getTipoPedido(),
                    p.getEstado()
            });
        }
    }

    // Metodos para validar datos e Ids
    private void validarDatos(String direccion, TipoPedido tipo, EstadoPedido estado) {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección del pedido no puede estar vacía.");
        }
        if (direccion.trim().length() > 100) {
            throw new IllegalArgumentException("La dirección no puede superar los 100 caracteres.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Debes seleccionar un tipo de pedido.");
        }
        if (estado == null) {
            throw new IllegalArgumentException("Debes seleccionar un estado.");
        }
    }

    // Valido que se haya seleccionado un pedido
    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Debes seleccionar un pedido de la tabla.");
        }
    }
}

