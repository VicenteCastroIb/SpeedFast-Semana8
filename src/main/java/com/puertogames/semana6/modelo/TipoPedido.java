package com.puertogames.semana6.modelo;

// Tipos de pedido que maneja SpeedFast
public enum TipoPedido {
    COMIDA,
    ENCOMIENDA,
    EXPRESS;

    // Texto para mostrar en pantalla: "Comida", "Encomienda", "Express"
    @Override
    public String toString() {
        String texto = name().toLowerCase();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }
}
