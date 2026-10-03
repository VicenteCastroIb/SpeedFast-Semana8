package com.puertogames.semana6.modelo;

// Pedido de SpeedFast: dirección, tipo y estado
public class Pedido {
    private int id;
    private String direccion;
    private TipoPedido tipoPedido;
    private EstadoPedido estado;
    private Repartidor repartidor;

    // Pedido nuevo, parte en estado PENDIENTE
    public Pedido(String direccion, TipoPedido tipoPedido) {
        this.direccion = direccion;
        this.tipoPedido = tipoPedido;
        this.estado = EstadoPedido.PENDIENTE;
    }

    // Pedido nuevo con el estado elegido
    public Pedido(String direccion, TipoPedido tipoPedido, EstadoPedido estado) {
        this.direccion = direccion;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
    }

    // Pedido existente, leído desde la BD o editado
    public Pedido(int id, String direccion, TipoPedido tipoPedido, EstadoPedido estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
    }

    // Pedido existente en estado PENDIENTE
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

    // Texto que se muestra en los combos y tablas: "id - dirección"
    @Override
    public String toString() {
        return id + " - " + direccion;
    }

    // Dos pedidos son el mismo si tienen el mismo id
    @Override
    public boolean equals(Object o) {
        return o instanceof Pedido && ((Pedido) o).id == id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}