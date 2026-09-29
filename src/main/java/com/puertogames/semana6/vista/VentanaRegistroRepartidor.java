package com.puertogames.semana6.vista;

import com.puertogames.semana6.controlador.ControladorRepartidor;
import com.puertogames.semana6.modelo.Repartidor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaRegistroRepartidor extends JFrame {

    private final ControladorRepartidor controladorRepartidor;
    private JTextField txtNombre;

    public VentanaRegistroRepartidor(ControladorRepartidor controladorRepartidor) {
        this.controladorRepartidor = controladorRepartidor;

        setTitle("SpeedFast - Registro de Repartidor");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        construirInterfaz();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        JPanel panelContenido = new JPanel(new GridLayout(2, 2, 10, 10));
        panelContenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtNombre = new JTextField(20);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarRepartidor());

        panelContenido.add(new JLabel("Nombre:"));
        panelContenido.add(txtNombre);
        panelContenido.add(new JLabel());
        panelContenido.add(btnGuardar);

        setContentPane(panelContenido);
    }

    private void guardarRepartidor() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.",
                    "Datos invalidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Repartidor nuevo
        controladorRepartidor.agregarRepartidor(new Repartidor(nombre));

        JOptionPane.showMessageDialog(this, "Repartidor guardado en la base de datos",
                "Exitazo", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
