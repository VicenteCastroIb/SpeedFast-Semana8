package com.puertogames.servicio;

public class PedidoExpress extends Pedido{

    private String cercania;


    public PedidoExpress(int idPedido, String direccionEntrega, int distanciaKm, String cercania) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.cercania = cercania;
    }

    @Override
    public void mostrarResumen() {
        System.out.println("[Pedido Express #" + getIdPedido() + "]\n" + "- Zona de cercania: " + cercania + "\n" + "- Direccion: " + getDireccionEntrega() + "\n" + "- Distancia: " + getDistanciaKm() + " Km\n" + "- Repartidor: " + (getRepartidorAsignado() == null ? "sin asignar" : getRepartidorAsignado()) + "\n" + "- Estado: " + getEstado() + "\n" + "- Tiempo estimado de entrega: " + calcularTiempoEntrega(getDistanciaKm()) + " minutos");
    }

    @Override
    public int calcularTiempoEntrega(int distanciaKm) {
        int tiempoBase = 10;
        int tiempoExtra = 5;
        if (distanciaKm > 5){
            return tiempoBase + tiempoExtra;
        } else {
            return tiempoBase;
        }


    }

    @Override
    public void asignarRepartidor() {
        // en express ya se sabe quien esta mas cerca, no hay que calcular nada
        setRepartidorAsignado(cercania);
        System.out.println("Asignacion automatica pedido " + getIdPedido() + " (Express): " + cercania);
    }

    public String getCercania() {
        return cercania;
    }

    public void setCercania(String cercania) {
        this.cercania = cercania;
    }
}
