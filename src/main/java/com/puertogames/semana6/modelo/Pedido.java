package com.puertogames.semana6.modelo;

public class Pedido {
    private int id;
    private String direccion;
    private TipoPedido tipoPedido;
    private EstadoPedido estado;
    private Repartidor repartidor;

    // Pedido nuevo
    public Pedido(String direccion, TipoPedido tipoPedido) {
        this.direccion = direccion;
        this.tipoPedido = tipoPedido;
        this.estado = EstadoPedido.PENDIENTE;
    }

    // Pedido desde la bd
    public Pedido(int id, String direccion, TipoPedido tipoPedido, EstadoPedido estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
    }

    // antiguo para no romper la ventana
    public Pedido(int id, String direccion, TipoPedido tipoPedido) {
        this(id, direccion, tipoPedido, EstadoPedido.PENDIENTE);
    }

    // Getters
    public int getId() { return id; }
    public String getDireccion() { return direccion; }
    public TipoPedido getTipoPedido() { return tipoPedido; }
    public EstadoPedido getEstado() { return estado; }
    public Repartidor getRepartidor() { return repartidor; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
    public void setRepartidor(Repartidor repartidor) { this.repartidor = repartidor; }

    @Override
    public String toString() {
        return "Pedido #" + id + " | " + direccion + " | " + tipoPedido + " | " + estado;
    }
}