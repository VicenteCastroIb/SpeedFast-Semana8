package com.puertogames.servicio;

public class PedidoEncomienda extends Pedido{

    private double pesoKg;

    public PedidoEncomienda(int idPedido, String direccionEntrega, int distanciaKm, double pesoKg) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.pesoKg = pesoKg;
    }

    @Override
    public void mostrarResumen() {
        System.out.println("[Pedido Encomienda #" + getIdPedido() + "]\n" + "- Peso: " + pesoKg + " Kg\n" + "- Direccion: " + getDireccionEntrega() + "\n" + "- Distancia: " + getDistanciaKm() + " Km\n" + "- Repartidor: " + (getRepartidorAsignado() == null ? "sin asignar" : getRepartidorAsignado()) + "\n" + "- Estado: " + getEstado() + "\n" + "- Tiempo estimado de entrega: " + calcularTiempoEntrega(getDistanciaKm()) + " minutos");
    }

    @Override
    public int calcularTiempoEntrega(int distanciaKm){
        return (int) Math.round(20 + 1.5 * distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        // los paquetes pesados necesitan camioneta, los livianos van en furgon
        String repartidor;
        if (pesoKg > 10) {
            repartidor = "Repartidor con camioneta de carga";
        } else {
            repartidor = "Repartidor con furgon";
        }
        setRepartidorAsignado(repartidor);
        System.out.println("Asignacion automatica pedido " + getIdPedido() + " (Encomienda): " + repartidor);
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }
}
