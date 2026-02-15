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
Para compilar y ejecutar el demo avanzado:
```bash
javac -d bin src/ticketsystem/**/*.java src/ticketsystem/*.java
java -cp bin ticketsystem.DemoAvanzado
```
