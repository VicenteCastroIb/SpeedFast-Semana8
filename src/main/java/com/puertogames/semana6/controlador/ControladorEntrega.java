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

/**
 * Controlador de Entregas: valida los datos que llegan desde la vista
 * y delega las operaciones de base de datos al EntregaDAO.
 */
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


    // Retorna las entregas aplicando los filtros opcionales.
    public List<Entrega> listarEntregas(Pedido pedido, Repartidor repartidor) throws SQLException {
        List<Entrega> entregas;
        if (pedido != null) {
            entregas = entregaDAO.readByPedido(pedido.getId());
        } else if (repartidor != null) {
            entregas = entregaDAO.readByRepartidor(repartidor.getId());
        } else {
            entregas = entregaDAO.readAll();
        }
        // Si se eligieron los dos filtros, se aplica el de repartidor sobre el resultado
        if (pedido != null && repartidor != null) {
            entregas.removeIf(e -> !e.getRepartidor().equals(repartidor));
        }
        return entregas;
    }


    // Recarga el modelo de la tabla con las entregas de la BD, aplicando los filtros.
    public void cargarTabla(DefaultTableModel modelo, Pedido pedido, Repartidor repartidor) throws SQLException {
        modelo.setRowCount(0); // Vaciamos tabla
        for (Entrega e : listarEntregas(pedido, repartidor)) {
            modelo.addRow(new Object[]{
                    e.getId(),
                    e.getPedido(),
                    e.getRepartidor(),
                    e.getFecha(),
                    e.getHora()
            });
        }
    }

    // Valido y actualizo una entrega existente
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

    // Elimino la entrega con el id indicado
    public void eliminarEntrega(int id) throws SQLException {
        // Valido id
        validarId(id);
        // Paso a DAO
        entregaDAO.delete(id);
    }

    // Valido que se haya elegido un pedido y un repartidor
    private void validarDatos(Pedido pedido, Repartidor repartidor) {
        if (pedido == null) {
            throw new IllegalArgumentException("Debes seleccionar un pedido.");
        }
        if (repartidor == null) {
            throw new IllegalArgumentException("Debes seleccionar un repartidor.");
        }
    }

    // Valido que se haya seleccionado una entrega
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

    // Convierte el texto a hora
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
