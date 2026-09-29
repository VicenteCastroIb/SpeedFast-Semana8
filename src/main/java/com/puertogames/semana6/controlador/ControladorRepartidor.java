package com.puertogames.semana6.controlador;

import com.puertogames.semana6.dao.RepartidorDAO;
import com.puertogames.semana6.modelo.Repartidor;

import java.util.List;

public class ControladorRepartidor {

    private final RepartidorDAO repartidorDAO = new RepartidorDAO();

    // Ahora los repartidores vienen de MySQL (con su id real)
    public List<Repartidor> getRepartidores() {
        return repartidorDAO.listarTodos();
    }

    // Para registrar repartidores nuevos (paso 4.5)
    public void agregarRepartidor(Repartidor repartidor) {
        repartidorDAO.guardar(repartidor);
    }
}