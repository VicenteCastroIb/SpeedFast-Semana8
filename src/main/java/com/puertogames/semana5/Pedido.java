package com.puertogames.semana5;

public class Pedido {

    // Atributos
    private int id;
    private String direccionEntrega;
    private EstadoPedido estado;


    // Constructor
    public Pedido(int id, String direccionEntrega) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(String nuevoEstado) {
        this.estado = EstadoPedido.valueOf(nuevoEstado.toUpperCase());
    }

    // toString
    @Override
    public String toString() {
        return "Pedido{id=" + id +
                ", direccionEntrega='" + direccionEntrega + "'" +
                ", estado='" + estado + "'}";
    }
}
