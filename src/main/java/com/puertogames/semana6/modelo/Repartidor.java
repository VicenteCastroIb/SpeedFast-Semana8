package com.puertogames.semana6.modelo;

// Repartidor de SpeedFast
public class Repartidor {

    private int id;
    private String nombre;

    // Repartidor nuevo
    public Repartidor(String nombre){
        this.nombre = nombre;
    }

    // Repartidor existente, leído desde la BD o editado
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }

    // Texto que se muestra en los combos y tablas: "id - nombre"
    @Override
    public String toString() {
        return  id + " - " +  nombre;
    }

    // Dos repartidores son el mismo si tienen el mismo id
    @Override
    public boolean equals(Object o) {
        return o instanceof Repartidor && ((Repartidor) o).id == id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
