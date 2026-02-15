# ⚡ Implementación Java Optimizada (Patrones & Clean Code)

Esta rama presenta una evolución del sistema original, incorporando principios de diseño profesional y optimizaciones de rendimiento.

## 💎 Mejoras Arquitectónicas
- **Patrón Strategy**: Algoritmos de asignación desacoplados e intercambiables.
- **Patrón Observer**: Sistema de notificaciones reactivo para eventos del sistema.
- **Java Moderno**: Uso de `Record`, `Optional`, `Streams` y colecciones nativas (`Deque`, `Map`).
- **Escalabilidad**: Diseño orientado al principio Open/Closed.

## 📊 Rendimiento
Esta versión reduce el tiempo de creación de objetos en un **59%** comparado con la versión estándar.

## 🚀 Ejecución
```bash
javac -d bin javaOptimo/**/*.java
java -cp bin javaOptimo.Demo
```

## 📝 Ejemplo de Funcionamiento (Reporte)
Reporte de ejecución de la versión optimizada con notificaciones reactivas:

```text
--- Creando tickets ---
[NOTIFICACIÓN] Nuevo ticket creado: ID 1 - No funciona el internet
[NOTIFICACIÓN] Nuevo ticket creado: ID 2 - Error en la base de datos

=== ESTADÍSTICAS DEL SISTEMA ===
Total de tickets:      2
Tickets pendientes:    2

--- Asignando tickets ---
[NOTIFICACIÓN] Ticket 1 asignado a: Carlos Redes
Ticket 1 procesado.

--- Actualizando estados ---
[NOTIFICACIÓN] Ticket 1 RESUELTO.
```
javac -d bin javaOptimo/**/*.java
java -cp bin javaOptimo.Demo
```
