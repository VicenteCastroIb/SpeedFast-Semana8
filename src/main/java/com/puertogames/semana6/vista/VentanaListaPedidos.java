package com.puertogames.semana6.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaListaPedidos extends JFrame {
    private JPanel jpPanelPrincipal;
    private JLabel lblTitulo;
    private JTable tblListaPedidos;

    public VentanaListaPedidos(DefaultTableModel model){
        setTitle("SpeedFast - Lista de Pedidos");
        setContentPane(jpPanelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Le paso la lista del papa al hijo, para q este actualizado
        tblListaPedidos.setModel(model);
        pack();
        setLocationRelativeTo(null);

    }
}
