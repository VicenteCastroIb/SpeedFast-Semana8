package com.puertogames.semana5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        // una sola zona de carga para los 3 repartidores, por eso es un recurso compartido
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();
        System.out.println("[Zona de carga inicializada]");

        // agrego los pedidos de prueba
        zonaDeCarga.agregarPedido(new Pedido(1, "Santiago Centro"));
        zonaDeCarga.agregarPedido(new Pedido(2, "Providencia"));
        zonaDeCarga.agregarPedido(new Pedido(3, "Ñuñoa"));
        zonaDeCarga.agregarPedido(new Pedido(4, "Clinica Davila, Recoleta"));
        zonaDeCarga.agregarPedido(new Pedido(5, "Las Condes"));

        // los 3 usan la misma zonaDeCarga
        Repartidor juan = new Repartidor("Juan", zonaDeCarga);
        Repartidor camila = new Repartidor("Camila", zonaDeCarga);
        Repartidor pedro = new Repartidor("Pedro", zonaDeCarga);

        // 3 hilos, uno por repartidor
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(juan);
        executor.execute(camila);
        executor.execute(pedro);

        executor.shutdown(); // ya no recibe tareas nuevas, pero deja terminar las 3 que estan corriendo

        try {
            // espero a que los 3 repartidores terminen antes de mostrar el mensaje final
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Algunos repartidores no alcanzaron a terminar a tiempo.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }

        System.out.println("[Zona de carga vacía]");
        System.out.println("Todos los pedidos han sido entregados correctamente.");
    }
}
