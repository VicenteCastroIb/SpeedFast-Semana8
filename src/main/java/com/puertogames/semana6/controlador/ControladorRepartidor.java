package com.puertogames.semana6.controlador;

import com.puertogames.semana6.modelo.Repartidor;

import java.util.ArrayList;
import java.util.List;

public class ControladorRepartidor {
    private List<Repartidor> repartidores;

    // Contructor donde inicio lista y cargo metodo
    public ControladorRepartidor(){
        repartidores = new ArrayList<>();
        cargarRepartidoresDefault();
    }

    // Metodo para precargar repartidores
    private void cargarRepartidoresDefault(){
        repartidores.add(new Repartidor("Juan"));
        repartidores.add(new Repartidor("Melisa"));
        repartidores.add(new Repartidor("Alejandra"));
        repartidores.add(new Repartidor("Pedro"));
        repartidores.add(new Repartidor("Diego"));
        repartidores.add(new Repartidor("Rodrigo"));
    }

    // Getter
    public List<Repartidor> getRepartidores() {return repartidores;}

    // toString
    @Override
    public String toString() {
        return "ControladorRepartidor{" +
                "repartidores=" + repartidores +
                '}';
    }
}
