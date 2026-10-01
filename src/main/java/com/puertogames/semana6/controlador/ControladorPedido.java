package com.puertogames.semana6.controlador;

import com.puertogames.semana6.dao.PedidoDAO;
import com.puertogames.semana6.dao.impl.PedidoDAOImpl;
import com.puertogames.semana6.modelo.EstadoPedido;
import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.TipoPedido;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

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

    public void editarPedido(int id, String direccion, TipoPedido tipo, EstadoPedido estado) throws SQLException {
        // valido datos
        validarId(id);
        validarDatos(direccion, tipo, estado);
        // Creo objeto pedido
        Pedido pedido = new Pedido(id, direccion.trim(), tipo, estado);
        // Envio pedido a DAO para q lo mande a BD
        pedidoDAO.update(pedido);
    }

    public void eliminarPedido(int id) throws SQLException {
        // Validamos id
        validarId(id);
        // Envio a DAO para el Delete
        pedidoDAO.delete(id);
    }

    /**
     * Recarga el modelo de la tabla con los pedidos de la BD.
     * Se llama al abrir la ventana y después de cada agregar/editar/eliminar.
     */
    public void cargarTabla(DefaultTableModel modelo) throws SQLException {
        modelo.setRowCount(0); // Vaciamos tabla
        for (Pedido p : listarPedidos()) { // Recorre lista de la BD
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
            throw new IllegalArgumentException("La direccion del pedido no puede estar vacia.");
        }
        if (direccion.trim().length() > 100) {
            throw new IllegalArgumentException("La direccion no puede superar los 100 caracteres.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Debes seleccionar un tipo de pedido.");
        }
        if (estado == null) {
            throw new IllegalArgumentException("Debes seleccionar un estado");
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Debes seleccionar un pedido de la tabla.");
        }
    }
}

