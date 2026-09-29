package com.puertogames.semana6.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public int getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public Repartidor getRepartidor() { return repartidor; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
}