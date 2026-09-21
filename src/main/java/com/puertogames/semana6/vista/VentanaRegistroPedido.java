package com.puertogames.semana6.vista;

import com.puertogames.semana6.controlador.ControladorPedido;
import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaRegistroPedido extends JFrame {
    private JPanel jpVentanaRegistro;
    private JSpinner spinner1;
    private JTextField textField1;
    private JComboBox<TipoPedido> comboBox1;
    private JButton btnGuardar;
    private JLabel lblId;
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

        spinner1.setModel(new SpinnerNumberModel(1,1,99999,1));

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
        if(direccion.isEmpty()){
            JOptionPane.showMessageDialog(this, "La direccion no puede estar vacia.", "Datos invalids", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) spinner1.getValue();
        TipoPedido tipoPedido = (TipoPedido) comboBox1.getSelectedItem();

        // Valido, ya q no setie autoincremento en id
        for (Pedido p : controladorPedido.getPedidos()) {
            if (p.getId() == id){
                JOptionPane.showMessageDialog(this, "ID: " + id + " ya existente", "Datos invalidos", JOptionPane.WARNING_MESSAGE );
            return;
            }
        }
        // Creo pedidos con datos extraidos
        Pedido pedido = new Pedido(id, direccion, tipoPedido);
        controladorPedido.agregarPedido(pedido, model);
        JOptionPane.showMessageDialog(this, "Pedido Creado Correctamente", "Exitazo", JOptionPane.INFORMATION_MESSAGE);

        // Limpio form
        limpiarFormulario();
    }

    public void limpiarFormulario(){
        textField1.setText("");
        spinner1.setValue(1);
        comboBox1.setSelectedIndex(0);
        dispose();
    }
}
