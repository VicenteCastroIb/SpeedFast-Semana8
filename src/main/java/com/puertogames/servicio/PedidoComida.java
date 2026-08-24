package com.puertogames.servicio;

public class PedidoComida extends Pedido{

    private String accesorio;


    public PedidoComida(int idPedido, String direccionEntrega, int distanciaKm, String accesorio) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.accesorio = accesorio;
    }

    public void mostrarResumen() {
        System.out.println("[Pedido Comida #" + getIdPedido() + "]\n" + "- "+ accesorio + "... OK \n" + "- Direccion: " + getDireccionEntrega() + "\n" + "- Distancia: " + getDistanciaKm() + " Km\n" + "- Tiempo estimado de entrega: " + calcularTiempoEntrega(getDistanciaKm()) + " Minutos");
    }

    @Override
    public int calcularTiempoEntrega(int distanciakm) {
        return 15 + 2 * distanciakm;
    }

    public String getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(String accesorio) {
        this.accesorio = accesorio;
    }
}