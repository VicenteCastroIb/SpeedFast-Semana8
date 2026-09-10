package com.puertogames.semana5;

import java.util.LinkedList;
import java.util.List;

public class ZonaDeCarga {

    private final List<Pedido> listaPedidos = new LinkedList<>();

    public synchronized void agregarPedido(Pedido pedido) {
        listaPedidos.add(pedido);
        System.out.println("Pedido: " + pedido.getId() + " agregado. |" + " Destino: " + pedido.getDireccionEntrega());
    }

    public synchronized Pedido retirarPedido() {
        if (listaPedidos.isEmpty()){
            System.out.println("No hay pedidos en cola...");
            return null;
        }
        return listaPedidos.remove(0);
    }

    public synchronized boolean estaVacia() {
        return listaPedidos.isEmpty();
    }
}
