package com.puertogames.semana6.vista;

import com.puertogames.semana6.controlador.ControladorPedido;
import com.puertogames.semana6.controlador.ControladorRepartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame {
    private JPanel jpVentanaPrincipal;
    private JButton btnRegistrar;
    private JButton btnListar;
    private JButton btnAsignar;
    private JLabel lblTitulo;

    // Únicas instancias para toda la app: se crean una sola vez acá
    private final ControladorPedido controladorPedido = new ControladorPedido();
    private final ControladorRepartidor controladorRepartidor = new ControladorRepartidor();
    private final DefaultTableModel modeloPedidos =
            new DefaultTableModel(new Object[]{"ID", "Dirección", "Tipo", "Repartidor"}, 0);

    public VentanaPrincipal() {
        setTitle("Ventana Principal SpeedFast");
        setContentPane(jpVentanaPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        btnRegistrar.addActionListener(e ->
                new VentanaRegistroPedido(controladorPedido, modeloPedidos).setVisible(true));

        btnListar.addActionListener(e ->
                new VentanaListaPedidos(modeloPedidos).setVisible(true));

        btnAsignar.addActionListener(e ->
                new VentanaAsignarRepartidor(controladorPedido, controladorRepartidor, modeloPedidos).setVisible(true));
    }
}