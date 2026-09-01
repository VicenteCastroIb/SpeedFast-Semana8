package com.puertogames.servicio;

import com.puertogames.interfaces.Cancelable;
import com.puertogames.interfaces.Despachable;

public abstract class Pedido implements Despachable, Cancelable {
    private int idPedido;
    private String direccionEntrega;
    private int distanciaKm;
    private String repartidorAsignado;
    private String estado;


    public Pedido(int idPedido, String direccionEntrega, int distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.repartidorAsignado = null;
        this.estado = "Pendiente";

    }

    public void mostrarResumen() {
        System.out.println("Pedido " + idPedido + "\n" + "Direccion: " + direccionEntrega + "\n" + "Distancia: " + distanciaKm + "\n" + "Tiempo estimado de entrega: " + calcularTiempoEntrega(distanciaKm));
    }

    public abstract int calcularTiempoEntrega(int distanciakm);

    public abstract void asignarRepartidor();

    // para cuando el cliente pide un repartidor en especifico
    public void asignarRepartidor(String nombre) {
        this.repartidorAsignado = nombre;
        System.out.println("Repartidor asignado manualmente al pedido " + idPedido + ": " + nombre);
    }

    @Override
    public void despachar() {
        if (repartidorAsignado == null) {
            System.out.println("No se puede despachar el pedido " + idPedido + ": falta asignar un repartidor.");
            return;
        }
        if (estado.equals("Cancelado")) {
            System.out.println("No se puede despachar el pedido " + idPedido + ": el pedido fue cancelado.");
            return;
        }
        estado = "Despachado";
        System.out.println("Pedido " + idPedido + " despachado con " + repartidorAsignado + ". Tiempo estimado: " + calcularTiempoEntrega(distanciaKm) + " min.");
    }

    @Override
    public void cancelar() {
        if (estado.equals("Despachado")) {
            System.out.println("No se puede cancelar el pedido " + idPedido + ": ya fue despachado.");
            return;
        }
        estado = "Cancelado";
        System.out.println("Pedido " + idPedido + " cancelado.");
    }

    // por si el cliente quiere dejar un motivo al cancelar
    public void cancelar(String motivo) {
        cancelar();
        if (estado.equals("Cancelado")) {
            System.out.println("Motivo: " + motivo);
        }
    }


    // Getters - Setters

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

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    protected void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    public String getEstado() {
        return estado;
    }

}
