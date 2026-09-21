package com.puertogames.semana6.controlador;

import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.Repartidor;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ControladorPedido {
    private List<Pedido> pedidos;

    // Constructor inicializa lista y metodo defecto
    public ControladorPedido(){
        pedidos = new ArrayList<>();
    }


    // Agregar Pedidos
    public void agregarPedido(Pedido pedido, DefaultTableModel model){
        pedidos.add(pedido);
        model.addRow(new Object[]{
                pedido.getId(),
                pedido.getDireccion(),
                pedido.getTipoPedido(),
                pedido.getRepartidor() == null ? "Sin Asignar" : pedido.getRepartidor().getNombre()
        });

    }

    // Traer todos los pedidos
    public List<Pedido> getPedidos(){
        return pedidos;
    }

    // Traer pedido puntual respecto al index
    public Pedido getPedidoByIndex(int index){
        return pedidos.get(index);
    }

    // Asignar repartidor a un pedido en especifico
    public void asignarRepartidor(int index, Repartidor repartidor, DefaultTableModel model){
        Pedido pedido = pedidos.get(index);
        pedido.setRepartidor(repartidor);
        model.setValueAt(repartidor.getNombre(), index, 3);
    }

}
