# SpeedFast

Sistema de asignación de repartidores para **SpeedFast**, una empresa de reparto a domicilio que ofrece tres tipos de servicio: comida, encomiendas y compras express.

Proyecto desarrollado como actividad formativa de la asignatura **Desarrollo Orientado a Objetos II** (Semana 1 — Sobrecarga y sobreescritura en clases derivadas).

## Descripción

El sistema modela distintos tipos de pedido mediante una jerarquía de clases y aplica **polimorfismo** para definir cómo se asigna un repartidor a cada uno:

- **Comida**: requiere repartidor con mochila térmica.
- **Encomienda**: requiere validación de peso y embalaje.
- **Compra Express**: debe asignarse al repartidor más cercano con disponibilidad inmediata.

## Estructura del proyecto

```
src/main/java/com/puertogames/
├── Main.java                     # Punto de entrada: instancia pedidos y demuestra el polimorfismo
└── servicio/
    ├── Pedido.java                # Clase base abstracta (idPedido, direccionEntrega, tipoPedido)
    ├── PedidoComida.java          # Sobrescribe y sobrecarga asignarRepartidor()
    ├── PedidoEncomienda.java      # Sobrescribe y sobrecarga asignarRepartidor()
    └── PedidoExpress.java         # Sobrescribe y sobrecarga asignarRepartidor()
```

## Conceptos aplicados

- **Herencia**: `PedidoComida`, `PedidoEncomienda` y `PedidoExpress` extienden la clase base `Pedido`.
- **Sobreescritura (override)**: cada subclase redefine `asignarRepartidor()` con un mensaje propio según el tipo de pedido.
- **Sobrecarga (overload)**: cada subclase implementa además `asignarRepartidor(String nombreRepartidor)`, que recibe el nombre del repartidor asignado e imprime las validaciones específicas de ese tipo de pedido.

## Requisitos

- JDK 25 (o superior)
- Maven (opcional, para compilar con `pom.xml`)
- IntelliJ IDEA (entorno de desarrollo recomendado)

## Cómo ejecutar

Desde IntelliJ IDEA, abrir el proyecto y ejecutar la clase `Main`.

Desde línea de comandos:

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out com.puertogames.Main
```

## Salida esperada (resumen)

El programa imprime primero la versión **sobrescrita** de `asignarRepartidor()` para cada pedido (mensaje diferenciado por tipo), y luego la versión **sobrecargada**, incluyendo el nombre del repartidor y la validación correspondiente (mochila térmica, peso/embalaje o cercanía y disponibilidad).

## Autor

Vicente Castro
