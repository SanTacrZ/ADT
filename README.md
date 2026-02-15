# ☕ Implementación Java Estándar (ADT Puro)

Esta rama contiene la implementación base del Sistema de Tickets, enfocada en la creación manual de Tipos de Datos Abstractos (ADT) y estructuras de datos fundamentales.

## 🏗️ Estructuras de Datos
- **`List.java`**: Implementación de una cola FIFO mediante una lista enlazada simple.
- **`Bag.java`**: Multiconjunto para colecciones donde el orden no es relevante.

## ⚙️ Características
- Basado en los requisitos originales del Taller 01.
- Validaciones manuales de email y transiciones de estado.
- Pruebas unitarias integradas en los métodos `main` de cada ADT.

## 🚀 Ejecución
Desde el directorio raíz:
```bash
javac -d bin src/ticketsystem/**/*.java src/ticketsystem/*.java
java -cp bin ticketsystem.DemoAvanzado
```

## 📝 Ejemplo de Funcionamiento (Reporte)
Al ejecutar el demo avanzado, el sistema demuestra el uso de ADTs y polimorfismo:

```text
=== 1. POLIMORFISMO CON INTERFAZ IDENTIFICABLE ===
Demostrando POLIMORFISMO:
  → Entidad ID: 100 | Tipo: Cliente
  → Entidad ID: 10 | Tipo: Tecnico
  → Entidad ID: 1 | Tipo: Ticket

=== 2. PATRÓN STRATEGY - ALGORITMOS INTERCAMBIABLES ===
Estrategia: Asignación por Especialidad
  → Ticket #1 asignado a: María Red
Estrategia: Asignación Round-Robin
  → Ticket #2 asignado a: Carlos Tech
  → Ticket #3 asignado a: María Red

=== 3. PATRÓN OBSERVER - NOTIFICACIONES ===
[LOGGER] 📝 Ticket #10 creado: Intento de acceso no autorizado
[LOGGER] 👤 Ticket #10 asignado a: Roberto Seguridad
[LOGGER] ✅ Ticket #10 resuelto por: Roberto Seguridad
```
javac -d bin src/ticketsystem/**/*.java src/ticketsystem/*.java
java -cp bin ticketsystem.DemoAvanzado
```
