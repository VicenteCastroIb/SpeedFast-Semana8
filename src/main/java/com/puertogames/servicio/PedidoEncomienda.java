package com.puertogames.servicio;

public class PedidoEncomienda extends Pedido{

    public PedidoEncomienda(int idPedido, String direccionEntrega, String tipoPedido) {
        super(idPedido, direccionEntrega, tipoPedido);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Encomienda] \n" + "Asignando repartidor... \n" + "-> Pedido asignado a repartidor \n");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[Pedido " + getTipoPedido() + " id: "+ getIdPedido() + ", direccion: "+ getDireccionEntrega()+ "] \n" + "-> Validando peso y embalaje... OK \n" + "-> Pedido asignado a " + nombreRepartidor + "\n");
    }

}
