# SpeedFast — Semana 8: Gestionando datos mediante operaciones CRUD

Actividad sumativa individual de la asignatura **Desarrollo Orientado a Objetos II**, Semana 8.

## Caso: gestión de pedidos en SpeedFast

Aplicación de escritorio en **Java Swing** que permite gestionar **repartidores, pedidos y entregas** de la empresa SpeedFast de forma persistente. Cada entidad tiene su ventana con las cuatro operaciones CRUD (registrar, listar, editar y eliminar), conectadas a una base de datos **MySQL** mediante **JDBC** y el **patrón DAO**.

## Requisitos

- JDK 21 o superior
- IntelliJ IDEA
- MySQL Server 8.4 y MySQL Workbench
- Maven (incluido en IntelliJ). El driver se descarga automáticamente:
  `com.mysql:mysql-connector-j:8.4.0`

## Cómo ejecutar

1. **Crear la base de datos:** abrir `database/speedfast_db.sql` en MySQL Workbench y ejecutarlo completo. Crea `speedfast_db` y las tablas `repartidores`, `pedidos` y `entregas` con sus claves foráneas.
2. **Configurar la conexión:** en `src/main/java/com/puertogames/semana6/util/ConexionDB.java` ajustar el usuario y la contraseña de MySQL:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
   private static final String USER = "root";
   private static final String PASSWORD = "tu_contraseña";
   ```
3. **Ejecutar la aplicación:** ejecutar la clase `main/Main`. Se abre el menú principal, desde el que se accede a cada gestión.

## Modelo de base de datos

| Tabla | Columnas | Relación |
|---|---|---|
| `repartidores` | id (PK, AUTO_INCREMENT), nombre | Un repartidor puede realizar muchas entregas |
| `pedidos` | id (PK, AUTO_INCREMENT), direccion, tipo (ENUM), estado (ENUM) | Un pedido puede tener una o varias entregas |
| `entregas` | id (PK), id_pedido (FK), id_repartidor (FK), fecha, hora | Cada entrega asocia un pedido con un repartidor |

- **Tipo de pedido:** COMIDA, ENCOMIENDA, EXPRESS.
- **Estado de pedido:** PENDIENTE, EN_REPARTO, ENTREGADO.

## Funcionalidades

- **Gestión de Repartidores:** registrar (nombre), editar, eliminar y listar en tabla.
- **Gestión de Pedidos:** registrar (dirección, tipo, estado), editar, eliminar y listar en tabla, con filtros opcionales por estado y por tipo.
- **Gestión de Entregas:** registrar una entrega asociando un pedido y un repartidor con fecha y hora, editar, eliminar y listar en tabla, con filtros por pedido y por repartidor. El pedido y el repartidor se eligen en combos cargados desde la base de datos, que muestran `id - dirección` e `id - nombre`.

## Estructura del proyecto (`com.puertogames.semana6`)

El proyecto está separado en capas: **vista → controlador → DAO → MySQL**.

- **modelo** — `Repartidor`, `Pedido`, `Entrega`, y los enums `TipoPedido` y `EstadoPedido`.
- **dao** — interfaces `RepartidorDAO`, `PedidoDAO` y `EntregaDAO`, con los métodos `create()`, `readAll()`, `update()` y `delete()`. `PedidoDAO` agrega `readByEstado()` y `readByTipo()`; `EntregaDAO` agrega `readByPedido()` y `readByRepartidor()`.
- **dao.impl** — `RepartidorDAOImpl`, `PedidoDAOImpl` y `EntregaDAOImpl`: implementaciones JDBC con `PreparedStatement` y `ResultSet`. `EntregaDAOImpl` usa `JOIN` para traer cada entrega con su pedido y su repartidor.
- **util** — `ConexionDB`: entrega la conexión a MySQL con `DriverManager`.
- **controlador** — `ControladorRepartidor`, `ControladorPedido` y `ControladorEntrega`: validan los datos que llegan desde la vista, llaman al DAO y cargan las tablas.
- **vista** — ventanas Swing escritas en código:
  - `VentanaPrincipal` — menú con acceso a las tres gestiones.
  - `GestionRepartidores`, `GestionPedidos` y `GestionEntregas` — formulario, tabla (`JTable`) y botones Agregar, Editar, Eliminar y Limpiar.
- **main** — `Main`: inicia la aplicación.

## Validaciones y manejo de errores

- **Validación de entradas** (en los controladores, antes de ir a la base de datos): campos obligatorios, largo máximo de 100 caracteres en nombre y dirección, selección obligatoria en los combos y en la tabla, y formato de fecha (`AAAA-MM-DD`) y hora (`HH:MM`).
- **Errores de base de datos:** los DAO registran la `SQLException` con `Logger` y la relanzan; la vista la atrapa y muestra un mensaje claro con `JOptionPane`. Por ejemplo, al eliminar un repartidor o un pedido que tiene entregas asociadas se informa que no se puede eliminar.
- **Confirmación** antes de eliminar un registro.

## Buenas prácticas aplicadas

- `PreparedStatement` con parámetros `?` en todas las consultas (previene inyección SQL).
- Cierre de recursos con `try-with-resources` (conexión, sentencia y resultado).
- Enums guardados con `name()` y leídos con `valueOf()` para mantener consistencia con las columnas `ENUM`.
- Código reutilizado en métodos privados (`mapearEntrega`, `validarNombre`, `validarId`, `convertirFecha`, entre otros).
- Separación por capas y comentarios en clases y métodos clave.

## Nota sobre las instrucciones

El Paso 2 de las instrucciones menciona una clase `ClienteDAO`. Como el esquema relacional entregado no incluye una tabla de clientes, ese rol lo cumple `RepartidorDAO`, que corresponde a la primera de las tres entidades del caso.
