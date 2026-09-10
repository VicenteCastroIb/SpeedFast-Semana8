package com.puertogames.semana5;

import java.util.LinkedList;
import java.util.List;

public class ZonaDeCarga {

    // uso LinkedList porque solo agrego al final y saco del principio
    private final List<Pedido> listaPedidos = new LinkedList<>();

    // synchronized para que dos repartidores no entren a la vez a tocar la lista
    public synchronized void agregarPedido(Pedido pedido) {
        listaPedidos.add(pedido);
        System.out.println("Pedido: " + pedido.getId() + " agregado. |" + " Destino: " + pedido.getDireccionEntrega());
    }

    // Evita choque de hilos el synchronized
    public synchronized Pedido retirarPedido() {
        if (listaPedidos.isEmpty()){
            System.out.println("No hay pedidos en cola...");
            return null; // con esto el repartidor sabe que ya no queda nada y puede parar
        }
        return listaPedidos.remove(0);
    }

    public synchronized boolean estaVacia() {
        return listaPedidos.isEmpty();
    }
}
