package com.puertogames.semana5;

import java.util.Random;

public class Repartidor implements Runnable {

    private String nombre;
    private ZonaDeCarga zonaDeCarga;
    private final Random random = new Random();

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga){
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {
        while(true) {
            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }

            System.out.println("[Repartidor - " + nombre + "] Retirando pedido #" + pedido.getId() + "...");
            pedido.setEstado("EN_REPARTO");
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
            System.out.println("[Repartidor - " + nombre + "] Entregando pedido #" + pedido.getId() + "...");

            try {
                int demoraEntrega = 1000 + random.nextInt(2000);
                Thread.sleep(demoraEntrega);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Repartidor - " + nombre + "] Entrega interrumpida.");
                return;
            }

            pedido.setEstado("ENTREGADO");
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
        }
        System.out.println("[Repartidor - " + nombre + "] No quedan más pedidos. Termina su turno.");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
