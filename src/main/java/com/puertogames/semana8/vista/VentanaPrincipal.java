package com.puertogames.semana8.vista;

import javax.swing.*;
import java.awt.*;

// Menú principal: abre las gestiones de repartidores, pedidos y entregas
public class VentanaPrincipal extends JFrame {

    // Componentes de la ventana
    private final JButton btnRepartidores = new JButton("Gestión de Repartidores");
    private final JButton btnPedidos = new JButton("Gestión de Pedidos");
    private final JButton btnEntregas = new JButton("Gestión de Entregas");

    // Constructor para configurar ventana
    public VentanaPrincipal() {
        setTitle("SpeedFast - Menú principal");
        setSize(350, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // al cerrar esta ventana termina la aplicación

        armarVentana();
        inicializarBotones();
    }

    // Ubico componentes
    private void armarVentana() {
        // Zona superior título
        JLabel lblTitulo = new JLabel("SpeedFast", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        // Zona central un botón por cada gestión
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        panelBotones.add(btnRepartidores);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnEntregas);

        // Ubico zonas
        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }

    // Cada botón abre una ventana nueva de la gestión correspondiente
    private void inicializarBotones() {
        btnRepartidores.addActionListener(e -> new GestionRepartidores().setVisible(true));
        btnPedidos.addActionListener(e -> new GestionPedidos().setVisible(true));
        btnEntregas.addActionListener(e -> new GestionEntregas().setVisible(true));
    }
}