package com.puertogames.semana6.modelo;

public class Repartidor {

    private String nombre;

    // Constructor
    public Repartidor(String nombre){
        this.nombre = nombre;
    }

    // Getter
    public String getNombre(){return nombre;}

    // toString
    @Override
    public String toString() {
        return  nombre;
    }
}
