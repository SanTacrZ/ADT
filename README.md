# 🎫 Sistema de Gestión de Tickets - Proyecto ADT

¡Bienvenido al repositorio **ADT (Abstract Data Types)**! Este proyecto implementa un sistema robusto para la gestión de tickets de soporte técnico, explorando diferentes paradigmas de programación, optimizaciones y patrones de diseño.

## 📋 Resumen del Proyecto

El sistema permite coordinar la interacción entre **Clientes**, **Técnicos** y **Tickets**. Utiliza estructuras de datos personalizadas para demostrar el manejo de lógica de negocios e invariantes de estado.

### Características Clave
- **Gestión FIFO**: Los tickets se procesan en el orden estricto de llegada.
- **Asignación Inteligente**: Filtros por especialidad y disponibilidad del técnico.
- **Validación de Estados**: Ciclo de vida controlado (Nuevo → Asignado → En Progreso → Resuelto → Cerrado).

---

## 📂 Organización del Repositorio

El proyecto está organizado en ramas para cada tipo de implementación:

| Rama | Implementación | Características |
| :--- | :--- | :--- |
| `main` | 🏠 **Portal Principal** | Documentación, comparativas y guías. |
| `java` | ☕ **Java Estándar** | ADT puro, estructuras de datos manuales (List, Bag). |
| `javaOptimo` | ⚡ **Java Optimizado** | Patrones Strategy/Observer, colecciones nativas. |
| `rust` | 🦀 **Rust** | Funcional, seguro en memoria y con alto rendimiento. |

---

## 📊 Comparativa de Rendimiento (Basemark)

Hemos sometido a las versiones de Java a una prueba de estrés (**Basemark**) con:
- **1,000** Clientes
- **500** Técnicos
- **5,000** Tickets

| Operación | Java Estándar (`java`) | Java Optimizado (`javaOptimo`) | Rust (`rust`) |
| :--- | :---: | :---: | :---: |
| **Creación (5k)** | 108 ms | 44 ms | *Pendiente* |
| **Asignación (5k)** | 20 ms | 50 ms | *Pendiente* |

> [!TIP]
> **Rust** ofrece la mejor seguridad de memoria sin recolector de basura (GC), mientras que **Java Optimizado** balancea flexibilidad con un rendimiento superior a la versión estándar gracias al uso de patrones de diseño.

## 🛠️ Cómo Probar (Reporte Rápido)

Cada rama contiene un ejemplo real de lo que verás al ejecutar el código. Por ejemplo, en Java Estándar verás el flujo de estados y en Java Optimizado recibirás notificaciones reactivas.

---
*Organizado profesionalmente por: SanTacrZ*
