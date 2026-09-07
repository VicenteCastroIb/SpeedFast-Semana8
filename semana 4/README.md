# SpeedFast — Semana 4

Actividad formativa individual **"Ejecutando tareas en paralelo con hilos en Java"**, de la asignatura **Desarrollo Orientado a Objetos II**.

## Descripción

Sobre el sistema de gestión de pedidos de SpeedFast (clase abstracta `Pedido`, sus subclases `PedidoComida`, `PedidoEncomienda` y `PedidoExpress`, y las interfaces `Despachable`, `Cancelable` y `Rastreable`), esta semana se agrega programación concurrente: se simula cómo varios repartidores entregan sus pedidos al mismo tiempo, cada uno funcionando como un hilo independiente.

## Qué se agregó esta semana

- La clase `Repartidor`, que implementa `Runnable` y representa a un repartidor recorriendo su propia lista de pedidos de forma secuencial.
- Simulación del tiempo de viaje con `Thread.sleep()` usando una demora aleatoria por pedido.
- Ejecución de varios repartidores en paralelo mediante `ExecutorService`, cada uno corriendo como un hilo independiente.

## Estructura del proyecto

- `src/main/java/Main.java` — Punto de entrada: crea tres repartidores con sus pedidos y los lanza en paralelo con un `ExecutorService`.
- `src/main/java/com/puertogames/servicio/Repartidor.java` — Hilo que entrega, en orden, los pedidos que se le asignaron.
- `src/main/java/com/puertogames/servicio/Pedido.java` — Clase base abstracta (de semanas anteriores), reutilizada tal cual.
- `src/main/java/com/puertogames/servicio/PedidoComida.java`, `PedidoEncomienda.java`, `PedidoExpress.java` — Subclases de `Pedido` (de semanas anteriores), reutilizadas tal cual.
- `src/main/java/com/puertogames/interfaces/Despachable.java`, `Cancelable.java`, `Rastreable.java` — Interfaces (de semanas anteriores), reutilizadas tal cual.
- `src/main/java/com/puertogames/controlador/ControladorDeEnvios.java` — Controlador de reserva/despacho/cancelación (de semanas anteriores), reutilizado tal cual.

## Cómo se organizó la concurrencia

- Cada `Repartidor` recibe su propio nombre y su propia lista de `Pedido` en el constructor, por lo que no hay memoria compartida entre hilos: cada uno trabaja únicamente sobre sus propios objetos y no hace falta sincronizar nada.
- El método `run()` recorre la lista de pedidos y, por cada uno, asigna el repartidor (reutilizando `asignarRepartidor(String)` de `Pedido`), imprime el mensaje de avance, simula el viaje con una pausa aleatoria de entre 1 y 3 segundos y llama a `despachar()` al terminar.
- En `Main`, se instancian tres repartidores (Camila, Luis y Fernanda) con dos pedidos cada uno y se ejecutan con `Executors.newFixedThreadPool(3)`. El programa espera con `executor.awaitTermination()` hasta que todos los repartidores terminen sus entregas antes de finalizar.
- Si un hilo es interrumpido mientras espera (`Thread.sleep`), se captura `InterruptedException`, se restaura el flag de interrupción con `Thread.currentThread().interrupt()` y se corta únicamente la entrega de ese repartidor, sin afectar a los demás hilos ni caer el programa.
- Se agregó además un `catch` genérico dentro del `run()` para que, si un pedido puntual falla por cualquier otro motivo, el repartidor continúe con el resto de sus pedidos en vez de terminar el hilo abruptamente.

## Cómo ejecutar

Desde IntelliJ IDEA, abrir el proyecto y ejecutar la clase `Main`.

Desde línea de comandos:

```
javac -d out $(find src/main/java -name "*.java")
java -cp out Main
```

## Ejemplo de salida por consola

Como los repartidores corren en paralelo, el orden exacto de las líneas cambia en cada ejecución (eso es justamente lo que se busca simular). Un fragmento típico se ve así:

```
================ INICIO DE ENTREGAS SIMULTANEAS ================

Repartidor asignado manualmente al pedido 101: Camila
[Repartidor: Camila] Entregando PedidoComida #101...
Repartidor asignado manualmente al pedido 102: Luis
[Repartidor: Luis] Entregando PedidoExpress #102...
Repartidor asignado manualmente al pedido 103: Fernanda
[Repartidor: Fernanda] Entregando PedidoEncomienda #103...
Pedido 103 despachado con Fernanda. Tiempo estimado: 43 min.
[Repartidor: Fernanda] Pedido #103 entregado.
...
[Repartidor: Camila] Termino todas sus entregas.
[Repartidor: Luis] Termino todas sus entregas.
[Repartidor: Fernanda] Termino todas sus entregas.

================ TODOS LOS REPARTIDORES TERMINARON SUS ENTREGAS ================
```

## Requisitos

- JDK 25 (o superior)
- Maven (opcional, para compilar con `pom.xml`)
- IntelliJ IDEA (entorno de desarrollo recomendado)

## Autor

Vicente Castro
