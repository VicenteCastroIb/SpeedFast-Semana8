import com.puertogames.servicio.Pedido;
import com.puertogames.servicio.PedidoComida;
import com.puertogames.servicio.PedidoEncomienda;
import com.puertogames.servicio.PedidoExpress;
import com.puertogames.servicio.Repartidor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        // pedidos de Camila
        List<Pedido> pedidosCamila = new ArrayList<>();
        pedidosCamila.add(new PedidoComida(101, "Acacias 123", 4, "Bebida grande"));
        pedidosCamila.add(new PedidoExpress(104, "Los Alerces 55", 2, "Zona centro"));

        // pedidos de Luis
        List<Pedido> pedidosLuis = new ArrayList<>();
        pedidosLuis.add(new PedidoExpress(102, "Marisoles 412", 6, "Zona norte"));
        pedidosLuis.add(new PedidoComida(105, "Las Rosas 89", 7, "Cubiertos"));

        // pedidos de Fernanda
        List<Pedido> pedidosFernanda = new ArrayList<>();
        pedidosFernanda.add(new PedidoEncomienda(103, "Acantos 12", 15, 12.5));
        pedidosFernanda.add(new PedidoEncomienda(106, "Los Boldos 34", 9, 3.2));

        Repartidor camila = new Repartidor("Camila", pedidosCamila);
        Repartidor luis = new Repartidor("Luis", pedidosLuis);
        Repartidor fernanda = new Repartidor("Fernanda", pedidosFernanda);

        System.out.println("================ INICIO DE ENTREGAS SIMULTANEAS ================\n");

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(camila);
        executor.execute(luis);
        executor.execute(fernanda);

        executor.shutdown(); // no se aceptan mas tareas, pero deja terminar las que ya estan corriendo

        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Algunos repartidores no alcanzaron a terminar a tiempo.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }

        System.out.println("\n================ TODOS LOS REPARTIDORES TERMINARON SUS ENTREGAS ================");
    }
}
