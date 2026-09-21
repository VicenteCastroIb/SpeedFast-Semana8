package com.puertogames.semana6.modelo;

public class Pedido {
    private int id;
    private String direccion;
    private TipoPedido tipoPedido;
    private Repartidor repartidor;


    // Constructor
    public Pedido(int id, String direccion, TipoPedido tipoPedido) {
        this.id = id;
        this.direccion = direccion;
        this.tipoPedido = tipoPedido;
    }

    // Getters
    public int getId() {return id;}
    public String getDireccion() {return direccion;}
    public TipoPedido getTipoPedido() {return tipoPedido;}
    public Repartidor getRepartidor() {return repartidor;}

    // Setters
    public void setRepartidor(Repartidor repartidor){
        this.repartidor = repartidor;
    }

    // toString
    @Override
    public String toString() {
        return "Pedido: " +
                "#" + id +
                " | direccion: " + direccion + '\'' +
                " | tipoPedido: " + tipoPedido;
    }
}