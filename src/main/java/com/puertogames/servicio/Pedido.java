package com.puertogames.servicio;

public abstract class Pedido {
    private int idPedido;
    private String direccionEntrega;
    private int distanciaKm;


    public Pedido(int idPedido, String direccionEntrega, int distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;

    }

    public void mostrarResumen() {
        System.out.println("Pedido " + idPedido + "\n" + "Direccion: " + direccionEntrega + "\n" + "Distancia: " + distanciaKm + "\n" + "Tiempo estimado de entrega: " + calcularTiempoEntrega(distanciaKm));
    }

    public abstract int calcularTiempoEntrega(int distanciakm);



    // Gettesrs - Setters

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

    public int getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(int distanciaKm) {
        this.distanciaKm = distanciaKm;
    }


}


