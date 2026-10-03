package com.puertogames.semana6.main;

import com.puertogames.semana6.vista.VentanaPrincipal;

import javax.swing.SwingUtilities;


// Punto de entrada de la aplicación SpeedFast.
public class Main {
    public static void main(String[] args) {
        // Abre la ventana principal en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}