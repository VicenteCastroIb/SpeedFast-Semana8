package com.puertogames.semana6.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

// Entrega: asocia un pedido con un repartidor en una fecha y hora
public class Entrega {
    private int id;
    private Pedido pedido;
    private Repartidor repartidor;
    private LocalDate fecha;
    private LocalTime hora;

    // Entrega nueva, toma fecha y hora actuales
    public Entrega(Pedido pedido, Repartidor repartidor) {
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
    }
    // Entrega traida desde BD
    public Entrega(int id, Pedido pedido, Repartidor repartidor, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public LocalTime getHora() {return hora;}
    public void setHora(LocalTime hora) {this.hora = hora;}
    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
    public Repartidor getRepartidor() {return repartidor;}
    public void setRepartidor(Repartidor repartidor) {this.repartidor = repartidor;}
    public Pedido getPedido() {return pedido;}
    public void setPedido(Pedido pedido) {this.pedido = pedido;}
}