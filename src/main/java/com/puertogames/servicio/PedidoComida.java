package com.puertogames.servicio;

public class PedidoComida extends Pedido{


    public PedidoComida(int idPedido, String direccionEntrega, String tipoPedido) {
        super(idPedido, direccionEntrega, tipoPedido);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Comida] \n" + "Asignando repartidor... \n" + "-> Pedido asignado a repartidor \n");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[Pedido " + getTipoPedido() + " id: "+ getIdPedido() + ", direccion: "+ getDireccionEntrega()+ "] \n" + "-> Verificando mochila termica... OK \n" + "-> Pedido asignado a " + nombreRepartidor + "\n");
    }
}
