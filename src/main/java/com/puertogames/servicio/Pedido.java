package com.puertogames.servicio;

public abstract class Pedido {
    private int idPedido;
    private String direccionEntrega;
    private String tipoPedido;


    public Pedido(int idPedido, String direccionEntrega, String tipoPedido){
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
    }

    public void asignarRepartidor() {
        System.out.println("Asignando repartidor a pedido id: " + idPedido + " " + tipoPedido + " direccion: " + direccionEntrega);
    }



    //getters-setters

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    // to-string

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", tipoPedido='" + tipoPedido + '\'' +
                '}';
    }
}
