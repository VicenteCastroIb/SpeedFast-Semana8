package com.puertogames.semana6.vista;

import com.puertogames.semana6.controlador.ControladorPedido;
import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaRegistroPedido extends JFrame {
    private JPanel jpVentanaRegistro;
    private JTextField textField1;
    private JComboBox<TipoPedido> comboBox1;
    private JButton btnGuardar;
    private JLabel lblDireccion;
    private JLabel lblTipo;
    private JLabel lblTitulo;

    private final ControladorPedido controladorPedido;
    private final DefaultTableModel model;

    public VentanaRegistroPedido(ControladorPedido controladorPedido, DefaultTableModel model){
        this.controladorPedido = controladorPedido;
        this.model = model;

        setTitle("SpeedFast - Registro de Pedido");
        setContentPane(jpVentanaRegistro);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        // Cargo combobox
        for (TipoPedido tipo : TipoPedido.values()) {
            comboBox1.addItem(tipo);
        }

        btnGuardar.addActionListener(e -> guardarPedido());

        // Se acomoda al tamanio de los componentes
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public void guardarPedido() {
        String direccion = textField1.getText().trim();
        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La direccion no puede estar vacia.", "Datos invalidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TipoPedido tipoPedido = (TipoPedido) comboBox1.getSelectedItem();

        // Pedido nuevo: sin id (lo asigna MySQL) y con estado PENDIENTE
        Pedido pedido = new Pedido(direccion, tipoPedido);
        controladorPedido.agregarPedido(pedido, model);

        JOptionPane.showMessageDialog(this, "Pedido guardado en la base de datos", "Exitazo", JOptionPane.INFORMATION_MESSAGE);
        limpiarFormulario();
    }

    public void limpiarFormulario() {
        textField1.setText("");
        comboBox1.setSelectedIndex(0);
        dispose();
    }
}
