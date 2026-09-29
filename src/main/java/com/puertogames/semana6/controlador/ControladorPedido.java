package com.puertogames.semana6.controlador;

import com.puertogames.semana6.dao.PedidoDAO;
import com.puertogames.semana6.modelo.Pedido;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorPedido {

    private final PedidoDAO pedidoDAO = new PedidoDAO();

    // Guarda en la BD y recarga tabla
    public void agregarPedido(Pedido pedido, DefaultTableModel model) {
        pedidoDAO.guardar(pedido);
        cargarPedidosDesdeBD(model);
    }

    // Llena el JTable con lo que hay en MySQL
    public void cargarPedidosDesdeBD(DefaultTableModel model) {
        model.setRowCount(0); // limpiar tabla
        for (Pedido p : pedidoDAO.listarTodos()) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getDireccion(),
                    p.getTipoPedido(),
                    p.getEstado(),
                    p.getRepartidor() == null ? "Sin asignar" : p.getRepartidor().getNombre()
            });
        }
    }

    // Para los combos de otras ventanas
    public List<Pedido> getPedidos() {
        return pedidoDAO.listarTodos();
    }
}

