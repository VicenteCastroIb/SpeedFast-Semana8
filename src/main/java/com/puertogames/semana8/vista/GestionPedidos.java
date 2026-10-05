package com.puertogames.semana8.vista;

import com.puertogames.semana8.controlador.ControladorPedido;
import com.puertogames.semana8.modelo.EstadoPedido;
import com.puertogames.semana8.modelo.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;


/**
 * Ventana de gestión de Pedidos, permite registrar, editar,
 * eliminar y listar Pedidos.
 */
public class GestionPedidos extends JFrame {

    // Componentes de la ventana
    private final JTextField txtDireccion = new JTextField(20);
    private final JComboBox<TipoPedido> cmbTipo = new JComboBox<>(TipoPedido.values());
    private final JComboBox<EstadoPedido> cmbEstado = new JComboBox<>(EstadoPedido.values());
    // Filtros de la tabla
    private final JComboBox<Object> cmbFiltroEstado = new JComboBox<>();
    private final JComboBox<Object> cmbFiltroTipo = new JComboBox<>();

    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnEditar = new JButton("Editar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JTable tblPedidos = new JTable();
    // Controlador, conectamos con BD
    private final ControladorPedido controlador = new ControladorPedido();
    // Modelo para mostrar datos de BD
    private DefaultTableModel modelo;

    // Contructor para configurar ventana
    public GestionPedidos() {
        setTitle("SpeedFast - Gestión de Pedidos");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        armarVentana();
        inicializarTabla();
        inicializarBotones();
        cargarTabla();
    }

    // Ubico componentes
    private void armarVentana() {
        // Zona superior: formulario en grilla (3 filas, 2 columnas)
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(cmbTipo);
        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(cmbEstado);

        // Filtros: opción "Todos" más cada valor del enum
        cmbFiltroEstado.addItem("Todos");
        for (EstadoPedido estado : EstadoPedido.values()) {
            cmbFiltroEstado.addItem(estado);
        }
        cmbFiltroTipo.addItem("Todos");
        for (TipoPedido tipo : TipoPedido.values()) {
            cmbFiltroTipo.addItem(tipo);
        }

        JPanel panelFiltros = new JPanel();
        panelFiltros.add(new JLabel("Filtrar por estado:"));
        panelFiltros.add(cmbFiltroEstado);
        panelFiltros.add(new JLabel("Filtrar por tipo:"));
        panelFiltros.add(cmbFiltroTipo);

        // Zona superior completa: formulario arriba y filtros debajo
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelFiltros, BorderLayout.SOUTH);

        // Zona inferior: botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Ubico zonas
        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tblPedidos), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Defino columnas de tabla
    private void inicializarTabla() {
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPedidos.setModel(modelo);
        tblPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Al seleccionar una fila, copia su nombre al formulario
        tblPedidos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tblPedidos.getSelectedRow();
            if (fila >= 0) {
                txtDireccion.setText(modelo.getValueAt(fila, 1).toString());
                cmbTipo.setSelectedItem(modelo.getValueAt(fila, 2));
                cmbEstado.setSelectedItem(modelo.getValueAt(fila, 3));
            }
        });
    }

    // Devuelve el id de la fila seleccionada, o 0 si no hay ninguna
    private int obtenerIdSeleccionado() {
        int fila = tblPedidos.getSelectedRow();
        if (fila == -1) {
            return 0;
        }
        return (int) modelo.getValueAt(fila, 0);
    }

    // Lee los filtros y pide al controlador que recargue la tabla
    private void cargarTabla() {
        // Si el combo está en "Todos", el filtro queda en null (sin filtrar)
        Object estadoElegido = cmbFiltroEstado.getSelectedItem();
        Object tipoElegido = cmbFiltroTipo.getSelectedItem();
        EstadoPedido estado = (estadoElegido instanceof EstadoPedido) ? (EstadoPedido) estadoElegido : null;
        TipoPedido tipo = (tipoElegido instanceof TipoPedido) ? (TipoPedido) tipoElegido : null;

        try {
            controlador.cargarTabla(modelo, estado, tipo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar los pedidos.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Conecto botones
    private void inicializarBotones() {
        btnAgregar.addActionListener(e -> agregarPedido());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEditar.addActionListener(e -> editarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());
        // Al cambiar un filtro, se recarga la tabla
        cmbFiltroEstado.addActionListener(e -> cargarTabla());
        cmbFiltroTipo.addActionListener(e -> cargarTabla());
    }

    // Toma los datos del formulario y registra el pedido
    private void agregarPedido() {
        try {
            controlador.agregarPedido(txtDireccion.getText(), (TipoPedido) cmbTipo.getSelectedItem(), (EstadoPedido) cmbEstado.getSelectedItem());
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el pedido.\n" + e.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Deja el form en blanco y quita seleccion
    private void limpiarFormulario() {
        txtDireccion.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        tblPedidos.clearSelection();
    }

    // Elimina el pedido seleccionado, previa confirmación
    private void eliminarPedido() {
        int id = obtenerIdSeleccionado();
        if (id == 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un pedido de la tabla.",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar este pedido?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            controlador.eliminarPedido(id);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente.");
        } catch (SQLException e) {
            String mensaje = (e.getErrorCode() == 1451)
                    ? "No se puede eliminar: el pedido tiene entregas asociadas."
                    : "No se pudo eliminar el pedido.\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensaje,
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Toma el id de la fila seleccionada y el nombre del formulario, y actualiza el Pedido
    private void editarPedido() {
        try {
            controlador.editarPedido(obtenerIdSeleccionado() ,txtDireccion.getText(), (TipoPedido) cmbTipo.getSelectedItem(), (EstadoPedido) cmbEstado.getSelectedItem());
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Pedido actualizado correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar el pedido.\n" + ex.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }


}
