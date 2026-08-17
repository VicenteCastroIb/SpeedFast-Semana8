package com.puertogames.servicio;

public class PedidoExpress extends Pedido{



    public PedidoExpress(int idPedido, String direccionEntrega, String tipoPedido) {
        super(idPedido, direccionEntrega, tipoPedido);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Express] \n" + "Asignando repartidor... \n" + "-> Pedido asignado a repartidor \n");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[Pedido " + getTipoPedido() + " id: "+ getIdPedido() + ", direccion: "+ getDireccionEntrega()+ "] \n" + "Asignando repartidor... \n" + "-> Repartidor más cercano con disponibilidad inmediata encontrado. \n" + "-> Pedido asignado a " + nombreRepartidor + "\n");
    }

}
