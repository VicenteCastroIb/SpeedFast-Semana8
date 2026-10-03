package com.puertogames.semana6.vista;

import com.puertogames.semana6.controlador.ControladorEntrega;
import com.puertogames.semana6.controlador.ControladorPedido;
import com.puertogames.semana6.controlador.ControladorRepartidor;
import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Ventana de gestión de entregas, permite registrar, editar, eliminar y listar
 * entregas
 */
public class GestionEntregas extends JFrame {

    // Componentes del formulario
    private final JComboBox<Pedido> cmbPedido = new JComboBox<>();
    private final JComboBox<Repartidor> cmbRepartidor = new JComboBox<>();
    private final JTextField txtFecha = new JTextField(10);
    private final JTextField txtHora = new JTextField(10);
    // Filtros de la tabla
    private final JComboBox<Object> cmbFiltroPedido = new JComboBox<>();
    private final JComboBox<Object> cmbFiltroRepartidor = new JComboBox<>();

    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnEditar = new JButton("Editar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JTable tblEntregas = new JTable();

    // Controladores, el de entregas, los otros dos para llenar los combos
    private final ControladorEntrega controlador = new ControladorEntrega();
    private final ControladorPedido controladorPedido = new ControladorPedido();
    private final ControladorRepartidor controladorRepartidor = new ControladorRepartidor();

    // Modelo para mostrar datos de BD
    private DefaultTableModel modelo;
    // Indica que los combos se están recargando, para no refrescar la tabla a medias
    private boolean cargandoCombos = false;

    // Constructor para configurar ventana
    public GestionEntregas() {
        setTitle("SpeedFast - Gestión de Entregas");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        armarVentana();
        inicializarTabla();
        inicializarBotones();
        cargarCombos();
        limpiarFormulario();
        cargarTabla();
    }

    // Ubico componentes
    private void armarVentana() {
        // Zona superior formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelFormulario.add(new JLabel("Pedido:"));
        panelFormulario.add(cmbPedido);
        panelFormulario.add(new JLabel("Repartidor:"));
        panelFormulario.add(cmbRepartidor);
        panelFormulario.add(new JLabel("Fecha (AAAA-MM-DD):"));
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Hora (HH:MM):"));
        panelFormulario.add(txtHora);

        // Filtros de la tabla
        JPanel panelFiltros = new JPanel();
        panelFiltros.add(new JLabel("Filtrar por pedido:"));
        panelFiltros.add(cmbFiltroPedido);
        panelFiltros.add(new JLabel("Filtrar por repartidor:"));
        panelFiltros.add(cmbFiltroRepartidor);

        // Zona superior completa, formulario arriba y filtros debajo
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelFiltros, BorderLayout.SOUTH);

        // Zona inferior, botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Ubico zonas
        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tblEntregas), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Defino columnas de tabla
    private void inicializarTabla() {
        String[] columnas = {"ID", "Pedido", "Repartidor", "Fecha", "Hora"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblEntregas.setModel(modelo);
        tblEntregas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Al seleccionar una fila, copia sus datos al formulario
        tblEntregas.getSelectionModel().addListSelectionListener(e -> {
            int fila = tblEntregas.getSelectedRow();
            if (fila >= 0) {
                cmbPedido.setSelectedItem(modelo.getValueAt(fila, 1));
                cmbRepartidor.setSelectedItem(modelo.getValueAt(fila, 2));
                txtFecha.setText(modelo.getValueAt(fila, 3).toString());
                txtHora.setText(modelo.getValueAt(fila, 4).toString());
            }
        });
    }

    // Conecto botones y filtros
    private void inicializarBotones() {
        btnAgregar.addActionListener(e -> agregarEntrega());
        btnEditar.addActionListener(e -> editarEntrega());
        btnEliminar.addActionListener(e -> eliminarEntrega());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Al cambiar un filtro, se recarga la tabla
        cmbFiltroPedido.addActionListener(e -> cargarTabla());
        cmbFiltroRepartidor.addActionListener(e -> cargarTabla());
    }

    /**
     * Carga en los combos los pedidos y repartidores que existen en la BD.
     * Conserva lo que estaba seleccionado antes de recargar.
     */
    private void cargarCombos() {
        cargandoCombos = true;
        try {
            // Guardamos la selección actual para restaurarla después
            Object pedidoActual = cmbPedido.getSelectedItem();
            Object repartidorActual = cmbRepartidor.getSelectedItem();
            Object filtroPedidoActual = cmbFiltroPedido.getSelectedItem();
            Object filtroRepartidorActual = cmbFiltroRepartidor.getSelectedItem();

            cmbPedido.removeAllItems();
            cmbFiltroPedido.removeAllItems();
            cmbFiltroPedido.addItem("Todos");
            for (Pedido p : controladorPedido.listarPedidos()) {
                cmbPedido.addItem(p);
                cmbFiltroPedido.addItem(p);
            }

            cmbRepartidor.removeAllItems();
            cmbFiltroRepartidor.removeAllItems();
            cmbFiltroRepartidor.addItem("Todos");
            for (Repartidor r : controladorRepartidor.listarRepartidores()) {
                cmbRepartidor.addItem(r);
                cmbFiltroRepartidor.addItem(r);
            }

            // Restauramos la selección
            restaurarSeleccion(cmbPedido, pedidoActual);
            restaurarSeleccion(cmbRepartidor, repartidorActual);
            restaurarSeleccion(cmbFiltroPedido, filtroPedidoActual);
            restaurarSeleccion(cmbFiltroRepartidor, filtroRepartidorActual);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar los pedidos y repartidores.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        } finally {
            cargandoCombos = false;
        }
    }

    // Vuelve a seleccionar en el combo lo que estaba elegido antes de recargarlo
    private void restaurarSeleccion(JComboBox<?> combo, Object seleccionAnterior) {
        if (seleccionAnterior != null) {
            combo.setSelectedItem(seleccionAnterior);
        }
    }

    // Lee los filtros y pide al controlador que recargue la tabla
    private void cargarTabla() {
        if (cargandoCombos) {
            return; // los combos se están recargando
        }
        // Si el combo está en "Todos", el filtro queda en null (sin filtrar)
        Object pedidoElegido = cmbFiltroPedido.getSelectedItem();
        Object repartidorElegido = cmbFiltroRepartidor.getSelectedItem();
        Pedido pedido = (pedidoElegido instanceof Pedido) ? (Pedido) pedidoElegido : null;
        Repartidor repartidor = (repartidorElegido instanceof Repartidor) ? (Repartidor) repartidorElegido : null;

        try {
            controlador.cargarTabla(modelo, pedido, repartidor);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar las entregas.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Devuelve el id de la fila seleccionada, o 0 si no hay ninguna
    private int obtenerIdSeleccionado() {
        int fila = tblEntregas.getSelectedRow();
        if (fila == -1) {
            return 0;
        }
        return (int) modelo.getValueAt(fila, 0);
    }

    // Toma los datos del formulario y registra la entrega
    private void agregarEntrega() {
        try {
            controlador.agregarEntrega(
                    (Pedido) cmbPedido.getSelectedItem(),
                    (Repartidor) cmbRepartidor.getSelectedItem(),
                    txtFecha.getText(),
                    txtHora.getText());
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Entrega registrada correctamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la entrega.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Toma el id de la fila seleccionada y los datos del formulario, y actualiza la entrega
    private void editarEntrega() {
        try {
            controlador.editarEntrega(
                    obtenerIdSeleccionado(),
                    (Pedido) cmbPedido.getSelectedItem(),
                    (Repartidor) cmbRepartidor.getSelectedItem(),
                    txtFecha.getText(),
                    txtHora.getText());
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Entrega actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar la entrega.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Elimina la entrega seleccionada, previa confirmación
    private void eliminarEntrega() {
        int id = obtenerIdSeleccionado();
        if (id == 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una entrega de la tabla.",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar esta entrega?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            controlador.eliminarEntrega(id);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Entrega eliminada correctamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar la entrega.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Deja el formulario en su estado inicial
    private void limpiarFormulario() {
        if (cmbPedido.getItemCount() > 0) {
            cmbPedido.setSelectedIndex(0);
        }
        if (cmbRepartidor.getItemCount() > 0) {
            cmbRepartidor.setSelectedIndex(0);
        }
        txtFecha.setText(LocalDate.now().toString());
        txtHora.setText(LocalTime.now().withSecond(0).withNano(0).toString());
        tblEntregas.clearSelection();
    }
}