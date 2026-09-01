import com.puertogames.controlador.ControladorDeEnvios;
import com.puertogames.servicio.Pedido;
import com.puertogames.servicio.PedidoComida;
import com.puertogames.servicio.PedidoEncomienda;
import com.puertogames.servicio.PedidoExpress;

public class Main {
    public static void main(String[] args) {

        ControladorDeEnvios controlador = new ControladorDeEnvios();

        Pedido pedidoComida = new PedidoComida(134, "Acacias 123", 13, "Mochila termica");
        Pedido pedidoEncomienda = new PedidoEncomienda(414, "Marisoles 412", 22, 8.5);
        Pedido pedidoExpress = new PedidoExpress(82, "Acantos 12", 3, "Juan Perez (2.3 km)");

        System.out.println("================ RESERVA DE PEDIDOS ================\n");
        controlador.reservarPedido(pedidoComida);
        controlador.reservarPedido(pedidoEncomienda);
        controlador.reservarPedido(pedidoExpress);

        System.out.println("\n================ ASIGNACION DE REPARTIDORES ================\n");
        pedidoComida.asignarRepartidor();
        pedidoEncomienda.asignarRepartidor();
        pedidoExpress.asignarRepartidor("Camila Rojas"); // este lo pidio el cliente a mano

        System.out.println("\n================ RESUMEN Y TIEMPOS DE ENTREGA ================\n");
        pedidoComida.mostrarResumen();
        System.out.println();
        pedidoEncomienda.mostrarResumen();
        System.out.println();
        pedidoExpress.mostrarResumen();

        System.out.println("\n================ DESPACHO DE PEDIDOS ================\n");
        controlador.despacharPedido(pedidoComida);
        controlador.despacharPedido(pedidoEncomienda);

        System.out.println("\n================ CANCELACION DE PEDIDOS ================\n");
        controlador.cancelarPedido(pedidoExpress, "el cliente ya no lo necesita");
        controlador.despacharPedido(pedidoExpress); // no deberia poder despachar, ya esta cancelado

        System.out.println("\n================ HISTORIAL DE ENTREGAS ================\n");
        controlador.verHistorial();

        System.out.println();
    }
}
