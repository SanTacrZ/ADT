# 🦀 Implementación en Rust

Una versión de alto rendimiento y segura en memoria del Sistema de Gestión de Tickets.

## 🛠️ Características de Rust
- **Ownership & Borrowing**: Gestión de memoria sin GC y sin fugas.
- **Pattern Matching**: Manejo robusto de estados y errores (`Result`, `Option`).
- **Rasgos (Traits)**: Implementación de comportamientos compartidos (análogo a interfaces).
- **Abstracciones de Cero Costo**: Alto rendimiento comparable a C++.

## 🚀 Ejecución
Requiere tener instalado Rust y Cargo.
```bash
cd rust
cargo run
```

## 📋 Reporte de Funcionamiento (Ejemplo)
Al ejecutar la versión en Rust, el sistema muestra una gestión de memoria segura y un flujo de eventos reactivo:

```text
╔═══════════════════════════════════════════════════════════════╗
║   DEMOSTRACIÓN AVANZADA RUST - FUNCIONAL Y OPTIMIZADA         ║
╚═══════════════════════════════════════════════════════════════╝

=== PROCESANDO TICKETS CON ESTRATEGIA ROUND-ROBIN ===

[LOGGER] [EVENTO CREACIÓN] Ticket #1 creado: Problema red 1
[LOGGER] [EVENTO ASIGNACIÓN] Ticket #1 asignado a: María Red
  ✓ Ticket #1 asignado a: María Red

[LOGGER] [EVENTO CREACIÓN] Ticket #2 creado: Problema red 2
[LOGGER] [EVENTO ASIGNACIÓN] Ticket #2 asignado a: Pedro Red
  ✓ Ticket #2 asignado a: Pedro Red

=== RESOLVIENDO TICKETS ===

[LOGGER] [EVENTO RESOLUCIÓN] Ticket #1 resuelto en 0 minutos
[LOGGER] [EVENTO RESOLUCIÓN] Ticket #2 resuelto en 0 minutos

=== ESTADÍSTICAS DEL SISTEMA (RUST) ===
Total de tickets:      3
Tickets pendientes:    0
Tickets en progreso:   0
Tickets resueltos:     2
=======================================
```
cd rust
cargo run
```
