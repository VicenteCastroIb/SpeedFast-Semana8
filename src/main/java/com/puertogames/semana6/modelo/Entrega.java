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

    // Entrega nueva (id 0) o existente, leída desde la BD o editada
    public Entrega(int id, Pedido pedido, Repartidor repartidor, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters
    public int getId() {return id;}
    public Pedido getPedido() {return pedido;}
    public Repartidor getRepartidor() {return repartidor;}
    public LocalDate getFecha() {return fecha;}
    public LocalTime getHora() {return hora;}
}