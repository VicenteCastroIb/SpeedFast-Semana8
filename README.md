# SpeedFast — Semana 5: Sincronizando procesos en sistemas concurrentes

Actividad formativa individual de la asignatura **Desarrollo Orientado a Objetos II**, Semana 5.

## Caso: coordinación de entregas en SpeedFast

SpeedFast detectó que, durante el despacho, varios repartidores podían acceder al mismo tiempo a la zona de carga y retirar el mismo pedido, provocando errores y entregas duplicadas. Esta entrega implementa un sistema concurrente en Java que sincroniza el acceso a la zona de carga compartida, para que cada pedido sea retirado y entregado por un único repartidor.

## Clases

- `Pedido` — id, dirección de entrega y estado (`EstadoPedido`). Nace en `PENDIENTE`.
- `EstadoPedido` — enum con los tres estados: `PENDIENTE`, `EN_REPARTO`, `ENTREGADO`.
- `ZonaDeCarga` — recurso compartido entre los repartidores. Guarda los pedidos pendientes y expone `agregarPedido()` y `retirarPedido()` como métodos `synchronized`, para que dos repartidores no puedan sacar el mismo pedido al mismo tiempo.
- `Repartidor` — implementa `Runnable`. Retira pedidos de la `ZonaDeCarga` uno a uno, los marca `EN_REPARTO`, simula el viaje con `Thread.sleep()` y los marca `ENTREGADO`, hasta que ya no quedan más.
- `Main` — crea la `ZonaDeCarga`, agrega 5 pedidos, lanza 3 repartidores en paralelo con un `ExecutorService` y espera a que todos terminen.

## Cómo se controla la concurrencia

Los 3 repartidores comparten la misma instancia de `ZonaDeCarga`. Al ser `synchronized`, sus métodos evitan que dos hilos revisen y saquen un pedido al mismo tiempo (que era el problema que reportó la empresa). Si un hilo es interrumpido durante `Thread.sleep()`, se maneja con `try/catch` sin afectar a los demás repartidores.

## Requisitos

- JDK 25 (o superior)
- Maven (opcional)
- IntelliJ IDEA

## Cómo ejecutar

Desde IntelliJ: abrir el proyecto y ejecutar `com.puertogames.semana5.Main`.

Desde línea de comandos:

```
javac -d out $(find src/main/java -name "*.java")
java -cp out com.puertogames.semana5.Main
```

## Salida esperada (resumen)

Se agregan 5 pedidos a la zona de carga y se lanzan 3 repartidores en paralelo. Cada uno retira pedidos hasta que no quedan más. Como corren al mismo tiempo, el orden exacto de las líneas puede variar entre ejecuciones:

```
[Zona de carga inicializada]
Pedido: 1 agregado. | Destino: Santiago Centro
...
[Repartidor - Juan] Retirando pedido #1...
[Repartidor - Juan] Estado: EN_REPARTO
[Repartidor - Juan] Entregando pedido #1...
[Repartidor - Juan] Estado: ENTREGADO
...
[Zona de carga vacía]
Todos los pedidos han sido entregados correctamente.
```

## Autor

Vicente Castro
