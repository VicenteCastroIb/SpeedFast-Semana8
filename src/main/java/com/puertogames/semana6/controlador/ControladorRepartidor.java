package com.puertogames.semana6.controlador;

import com.puertogames.semana6.dao.RepartidorDAO;
import com.puertogames.semana6.dao.impl.RepartidorDAOImpl;
import com.puertogames.semana6.modelo.Repartidor;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador de Repartidores: valida los datos que llegan desde la vista
 * y delega las operaciones de base de datos al RepartidorDAO.
 */

public class ControladorRepartidor {

    // Iniciamos su DAO para conversar con BD
    private final RepartidorDAO repartidorDAO = new RepartidorDAOImpl();


    // Validamos y registramos nuevo repartidor
    public void agregarRepartidor(String nombre) throws SQLException {
        // Validamos datos antes que todo
        validarNombre(nombre);

        // Creamos objeto repartidor y lo mando al DAO
        Repartidor repartidor = new Repartidor(nombre.trim());
        repartidorDAO.create(repartidor);
    }


    // Traigo desde la BD los repartidores
    public List<Repartidor> listarRepartidores() throws SQLException {
        return repartidorDAO.readAll();
    }


    // Valido y mando a editar repartidor a BD
    public void editarRepartidor(int id, String nombre) throws SQLException {
        // Validamos datos
        validarId(id);
        validarNombre(nombre);

        // Creamos objeto repartidor y lo mando a DAO
        Repartidor repartidor = new Repartidor(id, nombre.trim());
        repartidorDAO.update(repartidor);
    }


    // Elimino repartidor
    public void eliminarRepartidor(int id) throws SQLException {
        validarId(id);
        repartidorDAO.delete(id);
    }

    /**
     * Recarga el modelo de la tabla con los repartidores de la BD.
     * Se llama al abrir la ventana y después de cada agregar/editar/eliminar.
     */
    public void cargarTabla(DefaultTableModel modelo) throws SQLException {
        modelo.setRowCount(0); // Vaciamos tabla
        for (Repartidor r : listarRepartidores()) { // Recorre lista de la BD
            modelo.addRow(new Object[]{             // Va creando las filas
                    r.getId(),
                    r.getNombre()
            });
        }
    }

    // Metodos de validacion para no repetir codigo
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del repartidor no puede estar vacío.");
        }
        if (nombre.trim().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres.");
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Debes seleccionar un repartidor de la tabla.");
        }
    }
}