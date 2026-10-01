package com.puertogames.semana6.controlador;

import com.puertogames.semana6.dao.EntregaDAO;
import com.puertogames.semana6.dao.impl.EntregaDAOImpl;
import com.puertogames.semana6.modelo.Entrega;
import com.puertogames.semana6.modelo.Pedido;
import com.puertogames.semana6.modelo.Repartidor;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ControladorEntrega {

    // Iniciamos su DAO para conversar con BD
    private final EntregaDAO entregaDAO = new EntregaDAOImpl();

    // Validamos y agregamos Entrega
    public void agregarEntrega(Pedido pedido, Repartidor repartidor, String fecha, String hora) throws SQLException {
        validarDatos(pedido, repartidor);
        LocalDate fechaEntrega = convertirFecha(fecha);
        LocalTime horaEntrega = convertirHora(hora);

        Entrega entrega = new Entrega(0, pedido, repartidor, fechaEntrega, horaEntrega);
        entregaDAO.create(entrega);
    }
    // Listar todas las entregas
    public List<Entrega> listarEntregas() throws SQLException {
        return entregaDAO.readAll();
    }

    public void editarEntrega(int id, Pedido pedido, Repartidor repartidor, String fecha, String hora) throws SQLException {
        // Validamos
        validarId(id);
        validarDatos(pedido, repartidor);
        // Transformamos fecha y hora
        LocalDate fechaEntrega = convertirFecha(fecha);
        LocalTime horaEntrega = convertirHora(hora);
        // Armo objeto entrega
        Entrega entrega = new Entrega(id, pedido, repartidor, fechaEntrega, horaEntrega);
        // Se lo paso a DAO para que lo mande a BD
        entregaDAO.update(entrega);
    }

    public void eliminarEntrega(int id) throws SQLException {
        // Valido id
        validarId(id);
        // Paso a DAO
        entregaDAO.delete(id);
    }

    /**
     * Recarga el modelo de la tabla con las entregas de la BD.
     * Se llama al abrir la ventana y después de cada agregar/editar/eliminar.
     */
    public void cargarTabla(DefaultTableModel modelo) throws SQLException {
        modelo.setRowCount(0);
        for (Entrega e : listarEntregas()) {
            modelo.addRow(new Object[]{
                    e.getId(),
                    e.getPedido(),
                    e.getRepartidor(),
                    e.getFecha(),
                    e.getHora()
            });
        }
    }

    private void validarDatos(Pedido pedido, Repartidor repartidor) {
        if (pedido == null) {
            throw new IllegalArgumentException("Debes seleccionar un pedido.");
        }
        if (repartidor == null) {
            throw new IllegalArgumentException("Debes seleccionar un repartidor.");
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Debes seleccionar una entrega.");
        }
    }

    // Convierte el texto a fecha
    private LocalDate convertirFecha(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        try {
            return LocalDate.parse(texto.trim());            // espera AAAA-MM-DD
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha inválida. Usa el formato AAAA-MM-DD (ej: 2026-10-01).");
        }
    }

    // Convierte el texto a hora;
    private LocalTime convertirHora(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora es obligatoria.");
        }
        try {
            return LocalTime.parse(texto.trim());            // espera HH:MM
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Hora inválida. Usa el formato HH:MM (ej: 14:30).");
        }
    }
}
