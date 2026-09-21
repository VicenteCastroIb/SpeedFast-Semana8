package com.puertogames.semana6.vista;

import com.puertogames.semana6.controlador.ControladorPedido;
import com.puertogames.semana6.controlador.ControladorRepartidor;
import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.Repartidor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaAsignarRepartidor extends JFrame {

    private final ControladorPedido controladorPedido;
    private final DefaultTableModel modeloPedidos;
    private JComboBox<Pedido> comboPedidos;
    private JComboBox<Repartidor> comboRepartidores;

    public VentanaAsignarRepartidor(ControladorPedido controladorPedido,
                                    ControladorRepartidor controladorRepartidor,
                                    DefaultTableModel modeloPedidos) {
        this.controladorPedido = controladorPedido;
        this.modeloPedidos = modeloPedidos;

        setTitle("SpeedFast - Asignar Repartidor");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        construirInterfaz(controladorRepartidor);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void construirInterfaz(ControladorRepartidor controladorRepartidor) {
        JPanel panelContenido = new JPanel(new GridLayout(3, 2, 10, 10));
        panelContenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        List<Pedido> pedidos = controladorPedido.getPedidos();
        List<Repartidor> repartidores = controladorRepartidor.getRepartidores();

        comboPedidos = new JComboBox<>(pedidos.toArray(new Pedido[0]));
        comboRepartidores = new JComboBox<>(repartidores.toArray(new Repartidor[0]));

        panelContenido.add(new JLabel("Pedido:"));
        panelContenido.add(comboPedidos);

        panelContenido.add(new JLabel("Repartidor:"));
        panelContenido.add(comboRepartidores);

        JButton btnConfirmar = new JButton("Asignar e iniciar entrega");
        btnConfirmar.addActionListener(e -> confirmarAsignacion());

        panelContenido.add(new JLabel());
        panelContenido.add(btnConfirmar);

        setContentPane(panelContenido);
    }

    private void confirmarAsignacion() {
        Pedido pedidoSeleccionado = (Pedido) comboPedidos.getSelectedItem();
        Repartidor repartidorSeleccionado = (Repartidor) comboRepartidores.getSelectedItem();

        if (pedidoSeleccionado == null || repartidorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debes tener al menos un pedido registrado.",
                    "Sin datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int index = controladorPedido.getPedidos().indexOf(pedidoSeleccionado);
        controladorPedido.asignarRepartidor(index, repartidorSeleccionado, modeloPedidos);

        JOptionPane.showMessageDialog(this,
                "Pedido #" + pedidoSeleccionado.getId() + " asignado a " + repartidorSeleccionado.getNombre(),
                "Entrega iniciada", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}