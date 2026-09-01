package com.puertogames.servicio;

public class PedidoComida extends Pedido{

    private String accesorio;


    public PedidoComida(int idPedido, String direccionEntrega, int distanciaKm, String accesorio) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.accesorio = accesorio;
    }

    @Override
    public void mostrarResumen() {
        System.out.println("[Pedido Comida #" + getIdPedido() + "]\n" + "- "+ accesorio + "... OK \n" + "- Direccion: " + getDireccionEntrega() + "\n" + "- Distancia: " + getDistanciaKm() + " Km\n" + "- Repartidor: " + (getRepartidorAsignado() == null ? "sin asignar" : getRepartidorAsignado()) + "\n" + "- Estado: " + getEstado() + "\n" + "- Tiempo estimado de entrega: " + calcularTiempoEntrega(getDistanciaKm()) + " Minutos");
    }

    @Override
    public int calcularTiempoEntrega(int distanciakm) {
        return 15 + 2 * distanciakm;
    }

    @Override
    public void asignarRepartidor() {
        // hasta 5km alcanza en bici, mas lejos va en moto
        String repartidor;
        if (getDistanciaKm() <= 5) {
            repartidor = "Repartidor en bicicleta";
        } else {
            repartidor = "Repartidor en moto";
        }
        setRepartidorAsignado(repartidor);
        System.out.println("Asignacion automatica pedido " + getIdPedido() + " (Comida): " + repartidor);
    }

    public String getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(String accesorio) {
        this.accesorio = accesorio;
    }
}
