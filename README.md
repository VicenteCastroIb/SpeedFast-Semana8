# SpeedFast — Semana 6: Interfaz gráfica para gestión de entregas

Actividad formativa individual de la asignatura **Desarrollo Orientado a Objetos II**, Semana 6.

## Caso: interfaz gráfica para gestión de entregas en SpeedFast

En las semanas anteriores se modeló para SpeedFast la gestión de pedidos y la ejecución de entregas. En esta entrega, la empresa necesita una **interfaz gráfica de escritorio** (Java Swing) que permita a los usuarios registrar nuevos pedidos, visualizar los pedidos existentes en una tabla y asignar repartidores para simular el inicio de las entregas.

La aplicación se construye con Java Swing siguiendo una organización por capas (modelo, vista, controlador) y almacena los datos en listas en memoria compartidas mediante controladores.

## Estructura del proyecto

El código se organiza en el paquete `com.puertogames.semana6`, separado en:

- **modelo** — clases de datos:
  - `Pedido` — id, dirección, tipo de pedido (`TipoPedido`) y repartidor asignado.
  - `TipoPedido` — enum con los tipos disponibles: `COMIDA`, `ENCOMIENDA`, `EXPRESS`.
  - `Repartidor` — nombre del repartidor.
- **controlador** — lógica de negocio y datos en memoria:
  - `ControladorPedido` — mantiene la lista de pedidos, los agrega a la tabla (`DefaultTableModel`) y asigna repartidores.
  - `ControladorRepartidor` — precarga la lista de repartidores disponibles.
- **vista** — interfaces gráficas (Swing / GUI Designer de IntelliJ):
  - `VentanaPrincipal` — ventana inicial (JFrame) con botones para Registrar Pedido, Listar Pedidos y Asignar Repartidor.
  - `VentanaRegistroPedido` — formulario con ID (JSpinner), Dirección (JTextField) y Tipo (JComboBox), con validación y botón Guardar.
  - `VentanaListaPedidos` — muestra los pedidos en un JTable usando el `DefaultTableModel` compartido.
  - `VentanaAsignarRepartidor` — permite seleccionar un pedido y un repartidor y confirmar la asignación.
- **main** — punto de entrada:
  - `Main` — inicia la aplicación abriendo `VentanaPrincipal` en el hilo de eventos de Swing.

## Funcionalidades

- **Registrar pedido:** valida que la dirección no esté vacía y que el ID no esté duplicado antes de agregarlo a la lista en memoria. Muestra confirmación con `JOptionPane`.
- **Listar pedidos:** visualiza todos los pedidos en un `JTable` (ID, Dirección, Tipo, Repartidor), reflejando la información actualizada mediante `DefaultTableModel`.
- **Asignar repartidor:** selecciona un pedido y un repartidor desde combos y actualiza la tabla con la asignación.
- **Navegación:** todas las ventanas se abren desde `VentanaPrincipal` y comparten el mismo controlador y modelo de tabla, manteniendo los datos consistentes.

## Requisitos

- JDK 21 (o superior)
- IntelliJ IDEA
- Maven (opcional)

## Cómo ejecutar

Desde IntelliJ IDEA: abrir el proyecto y ejecutar la clase `com.puertogames.semana6.main.Main`.

> Nota: las ventanas se diseñaron con el GUI Designer de IntelliJ (archivos `.form`), por lo que la aplicación debe ejecutarse desde IntelliJ IDEA para que los formularios se instrumenten correctamente.

## Autor

Vicente Castro
