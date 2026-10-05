package com.puertogames.semana8.modelo;

// Pedido de SpeedFast: dirección, tipo y estado
public class Pedido {
    private int id;
    private String direccion;
    private TipoPedido tipoPedido;
    private EstadoPedido estado;

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

    // Getters
    public int getId() { return id; }
    public String getDireccion() { return direccion; }
    public TipoPedido getTipoPedido() { return tipoPedido; }
    public EstadoPedido getEstado() { return estado; }

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