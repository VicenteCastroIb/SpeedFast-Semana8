package com.puertogames.semana6.modelo;

public class Repartidor {

    private int id;
    private String nombre;

    // Constructor
    public Repartidor(String nombre){
        this.nombre = nombre;
    }

    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }

    // toString
    @Override
    public String toString() {
        return  id + " - " +  nombre;
    }
}
