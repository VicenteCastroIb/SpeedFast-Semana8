package com.puertogames.controlador;

import com.puertogames.interfaces.Rastreable;
import com.puertogames.servicio.Pedido;

import java.util.ArrayList;

public class ControladorDeEnvios implements Rastreable {

    private ArrayList<Pedido> pedidosReservados;
    private ArrayList<Pedido> historialEntregas;

    public ControladorDeEnvios() {
        this.pedidosReservados = new ArrayList<>();
        this.historialEntregas = new ArrayList<>();
    }

    public void reservarPedido(Pedido pedido) {
        pedidosReservados.add(pedido);
        System.out.println("Pedido " + pedido.getIdPedido() + " reservado. Total en cola: " + pedidosReservados.size());
    }

    public void despacharPedido(Pedido pedido) {
        pedido.despachar();
        if (pedido.getEstado().equals("Despachado")) {
            historialEntregas.add(pedido);
            pedidosReservados.remove(pedido);
        }
    }

    public void cancelarPedido(Pedido pedido) {
        pedido.cancelar();
        if (pedido.getEstado().equals("Cancelado")) {
            pedidosReservados.remove(pedido);
        }
    }

    // igual que cancelarPedido pero guardando el motivo que dio el cliente
    public void cancelarPedido(Pedido pedido, String motivo) {
        pedido.cancelar(motivo);
        if (pedido.getEstado().equals("Cancelado")) {
            pedidosReservados.remove(pedido);
        }
    }

    @Override
    public void verHistorial() {
        // aca solo quedan los pedidos que ya se despacharon
        if (historialEntregas.isEmpty()) {
            System.out.println("Todavia no hay pedidos despachados.");
            return;
        }
        for (Pedido pedido : historialEntregas) {
            System.out.println("- Pedido " + pedido.getIdPedido() + " (" + pedido.getClass().getSimpleName() + ") -> " + pedido.getEstado());
        }
    }

    public ArrayList<Pedido> getPedidosReservados() {
        return pedidosReservados;
    }

    public ArrayList<Pedido> getHistorialEntregas() {
        return historialEntregas;
    }
}
