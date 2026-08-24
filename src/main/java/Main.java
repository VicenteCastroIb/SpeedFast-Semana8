import com.puertogames.servicio.Pedido;
import com.puertogames.servicio.PedidoComida;
import com.puertogames.servicio.PedidoEncomienda;
import com.puertogames.servicio.PedidoExpress;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Pedido pedidoComida = new PedidoComida(134, "acacias 123", 13, "Mochila termica");
        Pedido pedidoEncomienda = new PedidoEncomienda(414, "Marisoles 412", 22, 8.5);
        Pedido pedidoExpress = new PedidoExpress(82, "Acantos 12", 17, "Juan Perez (2.3 km)");

        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        listaPedidos.add(pedidoComida);
        listaPedidos.add(pedidoEncomienda);
        listaPedidos.add(pedidoExpress);

        System.out.println("================ PEDIDOS ================ \n");

        for(Pedido p : listaPedidos){
            System.out.println("====================================");
            p.mostrarResumen();
        }


        System.out.println("================ CALCULAR TIEMPOS ================");

        for (Pedido p : listaPedidos) {
            System.out.println("- " + p.getClass().getSimpleName() + ": " + p.calcularTiempoEntrega(p.getDistanciaKm()) + " min");
        }

        System.out.println();
    }
}