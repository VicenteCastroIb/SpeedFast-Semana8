package com.puertogames.semana6.modelo;

public enum TipoPedido {
    COMIDA,
    ENCOMIENDA,
    EXPRESS;

    @Override
    public String toString() {
        String texto = name().toLowerCase();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }
}
