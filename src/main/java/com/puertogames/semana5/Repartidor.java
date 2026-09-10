package com.puertogames.semana5;

import java.util.Random;

// cada Repartidor corre en su propio hilo
public class Repartidor implements Runnable {

    private String nombre;
    private ZonaDeCarga zonaDeCarga; // esta referencia es la msima para los 3 repartidores
    private final Random random = new Random();

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga){
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {
        // sigue pidiendo pedidos hasta que la zona de carga se quede sin nada
        while(true) {
            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break; // ya no hay mas pedidos, se corta el while
            }

            System.out.println("[Repartidor - " + nombre + "] Retirando pedido #" + pedido.getId() + "...");
            pedido.setEstado("EN_REPARTO");
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
            System.out.println("[Repartidor - " + nombre + "] Entregando pedido #" + pedido.getId() + "...");

            try {
                // simulo el tiempo que se demoraria en llegar (entre 1 y 3 segundos)
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
