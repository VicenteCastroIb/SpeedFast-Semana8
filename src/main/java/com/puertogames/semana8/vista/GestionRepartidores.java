package com.puertogames.semana8.vista;

import com.puertogames.semana8.controlador.ControladorRepartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;


// Ventana de gestión de repartidores: registrar, editar, eliminar y listar
public class GestionRepartidores extends JFrame {

    // Componentes de la ventana
    private final JTextField txtNombre = new JTextField(20);
    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnEditar = new JButton("Editar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JTable tblRepartidores = new JTable();
    // Controlador, conectamos con BD
    private final ControladorRepartidor controlador = new ControladorRepartidor();
    // Modelo para mostrar datos de BD
    private DefaultTableModel modelo;

    // Constructor para configurar ventana
    public GestionRepartidores() {
        setTitle("SpeedFast - Gestión de Repartidores");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        armarVentana();
        inicializarTabla();
        inicializarBotones();
        cargarTabla();
    }

    // Ubico componentes
    private void armarVentana() {
        // Zona superior : Formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        // Zona inferior : Botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Ubico zonas
        setLayout(new BorderLayout());
        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(tblRepartidores), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Defino columnas de tabla
    private void inicializarTabla() {
        String[] columnas = {"ID", "Nombre"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRepartidores.setModel(modelo);
        tblRepartidores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Al seleccionar una fila, copia su nombre al formulario
        tblRepartidores.getSelectionModel().addListSelectionListener(e -> {
            int fila = tblRepartidores.getSelectedRow();
            if (fila >= 0) {
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            }
        });
    }

    // Devuelve el id de la fila seleccionada, o 0 si no hay ninguna
    private int obtenerIdSeleccionado() {
        int fila = tblRepartidores.getSelectedRow();
        if (fila == -1) {
            return 0;
        }
        return (int) modelo.getValueAt(fila, 0);
    }

    // Controlador recarga tabla
    private void cargarTabla() {
        try {
            controlador.cargarTabla(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar los repartidores.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Conecto botones
    private void inicializarBotones() {
        btnAgregar.addActionListener(e -> agregarRepartidor());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEditar.addActionListener(e -> editarRepartidor());
        btnEliminar.addActionListener(e -> eliminarRepartidor());
    }

    // Toma el nombre del formulario y registra el repartidor
    private void agregarRepartidor() {
        try {
            controlador.agregarRepartidor(txtNombre.getText());
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Repartidor registrado correctamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el repartidor.\n" + e.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Deja el form en blanco y quita seleccion
    private void limpiarFormulario() {
        txtNombre.setText("");
        tblRepartidores.clearSelection();
    }

    // Elimina el repartidor seleccionado, previa confirmación
    private void eliminarRepartidor() {
        int id = obtenerIdSeleccionado();
        if (id == 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un repartidor de la tabla.",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar este repartidor?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            controlador.eliminarRepartidor(id);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Repartidor eliminado correctamente.");
        } catch (SQLException e) {
            String mensaje = (e.getErrorCode() == 1451)
                    ? "No se puede eliminar: el repartidor tiene entregas asociadas."
                    : "No se pudo eliminar el repartidor.\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensaje,
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Toma el id de la fila seleccionada y el nombre del formulario, y actualiza el repartidor
    private void editarRepartidor() {
        try {
            controlador.editarRepartidor(obtenerIdSeleccionado(), txtNombre.getText());
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Repartidor actualizado correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar el repartidor.\n" + ex.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }


}
