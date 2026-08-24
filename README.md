# SpeedFast

Sistema de cálculo de tiempos de entrega para **SpeedFast**, una empresa de reparto a domicilio que ofrece tres tipos de servicio: comida, encomiendas y compras express.

Proyecto desarrollado como actividad formativa de la asignatura **Desarrollo Orientado a Objetos II** (Semana 2 — Clase abstracta y jerarquía de herencia).

## Descripción

El sistema modela distintos tipos de pedido mediante una clase abstracta `Pedido` y tres subclases que heredan de ella, aplicando polimorfismo para calcular el tiempo estimado de entrega según el tipo de servicio y la distancia en kilómetros:

- Comida: 15 min base + 2 min por kilómetro.
- Encomienda: 20 min base + 1.5 min por kilómetro (redondeado a entero).
- Express: 10 min base, +5 min extra si la distancia supera los 5 km.

## Estructura del proyecto

- `src/main/java/Main.java` — Punto de entrada: instancia pedidos y demuestra el polimorfismo.
- `src/main/java/com/puertogames/servicio/Pedido.java` — Clase base abstracta (idPedido, direccionEntrega, distanciaKm).
- `src/main/java/com/puertogames/servicio/PedidoComida.java` — Sobrescribe `calcularTiempoEntrega()`.
- `src/main/java/com/puertogames/servicio/PedidoEncomienda.java` — Sobrescribe `calcularTiempoEntrega()`.
- `src/main/java/com/puertogames/servicio/PedidoExpress.java` — Sobrescribe `calcularTiempoEntrega()`.

## Conceptos aplicados

- Clase abstracta: `Pedido` define los atributos comunes (`idPedido`, `direccionEntrega`, `distanciaKm`) y el comportamiento común, implementando `mostrarResumen()` de forma reutilizable — llama a `calcularTiempoEntrega()` de forma polimórfica según el tipo real del objeto.
- Método abstracto: `calcularTiempoEntrega()` se declara en `Pedido` sin implementación, obligando a cada subclase a definir su propia lógica.
- Herencia: `PedidoComida`, `PedidoEncomienda` y `PedidoExpress` extienden `Pedido` y reutilizan sus atributos, constructor y `mostrarResumen()`.
- Sobrescritura (override): cada subclase redefine `calcularTiempoEntrega()` con la fórmula correspondiente a su tipo de servicio.
- Polimorfismo: en `Main`, los pedidos se almacenan como una lista de `Pedido` y se recorren de forma uniforme, aunque cada uno ejecuta su propia lógica de cálculo.
- Atributos propios: cada subclase agrega un atributo propio además de los heredados — `PedidoComida.accesorio` (accesorio necesario para el reparto), `PedidoEncomienda.pesoKg` (peso del paquete) y `PedidoExpress.cercania` (repartidor asignado por cercanía).

## Requisitos

- JDK 25 (o superior)
- Maven (opcional, para compilar con `pom.xml`)
- IntelliJ IDEA (entorno de desarrollo recomendado)

## Cómo ejecutar

Desde IntelliJ IDEA, abrir el proyecto y ejecutar la clase `Main`.

Desde línea de comandos:

```
javac -d out $(find src/main/java -name "*.java")
java -cp out Main
```

## Salida esperada (resumen)

El programa crea un pedido de cada tipo (Comida, Encomienda, Express), imprime el resumen de cada uno con su dirección, distancia y tiempo estimado de entrega calculado según la fórmula propia de cada subclase.

## Autor

Vicente Castro