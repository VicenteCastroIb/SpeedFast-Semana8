package com.puertogames.servicio;

import java.util.List;
import java.util.Random;

public class Repartidor implements Runnable {

    private String nombre;
    private List<Pedido> pedidosAsignados;
    private final Random random = new Random();

    public Repartidor(String nombre, List<Pedido> pedidosAsignados) {
        this.nombre = nombre;
        this.pedidosAsignados = pedidosAsignados;
    }

    @Override
    public void run() {
        for (Pedido pedido : pedidosAsignados) {
            try {
                pedido.asignarRepartidor(nombre);

                String tipoPedido = pedido.getClass().getSimpleName();
                System.out.println("[Repartidor: " + nombre + "] Entregando " + tipoPedido + " #" + pedido.getIdPedido() + "...");

                int demoraViaje = 1000 + random.nextInt(2000); // entre 1 y 3 segundos, simula el trayecto
                Thread.sleep(demoraViaje);

                pedido.despachar();
                System.out.println("[Repartidor: " + nombre + "] Pedido #" + pedido.getIdPedido() + " entregado.");

            } catch (InterruptedException e) {
                // si interrumpen el hilo cortamos la entrega de este repartidor sin tumbar el programa completo
                Thread.currentThread().interrupt();
                System.out.println("[Repartidor: " + nombre + "] Entrega interrumpida.");
                return;
            } catch (Exception e) {
                // un problema con un pedido puntual no deberia frenar al repartidor, sigue con el resto
                System.out.println("[Repartidor: " + nombre + "] No se pudo entregar el pedido #" + pedido.getIdPedido() + ": " + e.getMessage());
            }
        }
        System.out.println("[Repartidor: " + nombre + "] Termino todas sus entregas.");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pedido> getPedidosAsignados() {
        return pedidosAsignados;
    }
}
