# SpeedFast — Semana 7: Conectando aplicaciones Java con bases de datos mediante JDBC

Actividad formativa individual de la asignatura **Desarrollo Orientado a Objetos II**, Semana 7.

## Caso: persistencia de pedidos en SpeedFast

La aplicación de escritorio (Java Swing) construida en la semana 6 ahora **guarda y consulta la información en una base de datos MySQL** mediante JDBC. Los pedidos, repartidores y entregas se registran desde los formularios y se mantienen aunque se cierre la aplicación.

## Requisitos

- JDK 21 o superior
- IntelliJ IDEA (el proyecto usa formularios del GUI Designer de IntelliJ)
- MySQL Server 8.4 y MySQL Workbench
- Maven (incluido en IntelliJ). El driver se descarga automáticamente:
  `com.mysql:mysql-connector-j:8.4.0`

## Cómo ejecutar

1. **Crear la base de datos:** abrir `database/speedfast_db.sql` en MySQL Workbench y ejecutarlo completo. Crea `speedfast_db`, las tablas `repartidor`, `pedido` y `entrega` (con sus claves foráneas) y carga datos de prueba.
2. **Configurar la conexión:** en `src/main/java/com/puertogames/semana6/dao/ConexionBD.java` ajustar el usuario y la contraseña de MySQL:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
   private static final String USER = "root";
   private static final String PASSWORD = "tu_contraseña";
   ```
3. **Probar la conexión (opcional):** ejecutar `main/PruebaConexion`. Debe imprimir "Conexion exitosa".
4. **Ejecutar la aplicación:** ejecutar `main/Main`.

## Modelo de base de datos

| Tabla | Columnas | Relación |
|---|---|---|
| `repartidor` | id (PK, AUTO_INCREMENT), nombre | Un repartidor puede realizar muchas entregas |
| `pedido` | id (PK, AUTO_INCREMENT), direccion, tipo, estado | Un pedido puede tener una o varias entregas |
| `entrega` | id (PK), id_pedido (FK), id_repartidor (FK), fecha, hora | Cada entrega se asocia a un pedido y a un repartidor |

## Estructura del proyecto (`com.puertogames.semana6`)

- **dao** — acceso a datos con JDBC:
  - `ConexionBD` — gestiona la conexión con `DriverManager` (`conectar()` y `cerrar()`).
  - `PedidoDAO` — `guardar(Pedido)` con `PreparedStatement` y `listarTodos()` con `ResultSet`.
  - `RepartidorDAO` — `listarTodos()` devuelve una `List<Repartidor>` usando `ResultSet`, y `guardar(Repartidor)`.
  - `EntregaDAO` — `guardar(Entrega)` registra la relación entre pedido y repartidor con fecha y hora.
- **modelo** — `Pedido`, `Repartidor`, `Entrega`, y los enums `TipoPedido` y `EstadoPedido`.
- **controlador** — `ControladorPedido` y `ControladorRepartidor`: conectan las vistas con los DAO.
- **vista** — ventanas Swing:
  - `VentanaPrincipal` — menú con Registrar Pedido, Listar Pedidos, Asignar Repartidor y Registrar Repartidor.
  - `VentanaRegistroPedido` — registra un pedido en la BD (el ID lo asigna MySQL).
  - `VentanaListaPedidos` — muestra los pedidos almacenados en un `JTable` (ID, Dirección, Tipo, Estado, Repartidor).
  - `VentanaAsignarRepartidor` — elige un pedido y un repartidor desde la BD y registra una entrega.
  - `VentanaRegistroRepartidor` — registra un repartidor en la BD.
- **main** — `Main` (inicia la app) y `PruebaConexion` (prueba JDBC con `try-catch-finally`).

## Buenas prácticas aplicadas

- `PreparedStatement` con parámetros `?` en todas las consultas (previene inyección SQL).
- Cierre de recursos con `try-with-resources` en los DAO y `try-catch-finally` en la prueba de conexión.
- Manejo de `SQLException` con mensaje en consola (`printStackTrace`) y aviso al usuario (`JOptionPane`).
- Enums guardados con `name()` y leídos con `valueOf()` para mantener consistencia con la BD.
- Separación en capas: vista → controlador → DAO → MySQL.
