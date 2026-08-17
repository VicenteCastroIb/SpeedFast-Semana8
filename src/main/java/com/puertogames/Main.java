package com.puertogames;

import com.puertogames.servicio.*;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ArrayList<Pedido> listaPedidos = new ArrayList<>();

        PedidoComida pedidoComida = new PedidoComida(11, "acacias 1231", "comida");
        PedidoExpress pedidoExpress = new PedidoExpress(311, "casablanca 124", "express");
        PedidoEncomienda pedidoEncomienda = new PedidoEncomienda(41, "ortuzar 321", "encomienda");

        listaPedidos.add(pedidoComida);
        listaPedidos.add(pedidoExpress);
        listaPedidos.add(pedidoEncomienda);

        System.out.println("============= VERSION SOBRESCRITA =============");
        for(Pedido p : listaPedidos){
            p.asignarRepartidor();
        }

        System.out.println("============= VERSION SOBRECARGADA =============");
        pedidoComida.asignarRepartidor("Rodolfo");
        pedidoExpress.asignarRepartidor("Camila");
        pedidoEncomienda.asignarRepartidor("Catalina");
    }
}
