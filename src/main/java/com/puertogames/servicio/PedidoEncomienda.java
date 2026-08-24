package com.puertogames.servicio;

public class PedidoEncomienda extends Pedido{

    private double pesoKg;

    public PedidoEncomienda(int idPedido, String direccionEntrega, int distanciaKm, double pesoKg) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.pesoKg = pesoKg;
    }

    public void mostrarResumen() {
        System.out.println("[Pedido Encomienda #" + getIdPedido() + "]\n" + "- Peso: " + pesoKg + " Kg\n" + "- Direccion: " + getDireccionEntrega() + "\n" + "- Distancia: " + getDistanciaKm() + " Km\n" + "- Tiempo estimado de entrega: " + calcularTiempoEntrega(getDistanciaKm()) + " minutos");
    }

    @Override
    public int calcularTiempoEntrega(int distanciaKm){
        return (int) Math.round(20 + 1.5 * distanciaKm);
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }
}