# Sistema de Gestión de Tickets

## Información del Equipo

**Integrantes:**
- [Nombre Completo 1] - ID: [ID1]
- [Nombre Completo 2] - ID: [ID2]
- [Nombre Completo 3] - ID: [ID3]

**Lenguaje:** Java  
**Versión:** Java 11 o superior

---

## Descripción del Proyecto

Este proyecto implementa un **Sistema de Gestión de Tickets** para soporte técnico. El sistema permite:

- Crear tickets de soporte reportados por clientes
- Asignar tickets automáticamente a técnicos según su especialidad
- Gestionar el ciclo de vida de tickets (Nuevo → Asignado → En Progreso → Resuelto → Cerrado)
- Consultar tickets por estado o categoría usando iteradores personalizados
- Generar estadísticas del sistema

El sistema utiliza estructuras de datos personalizadas (Lista FIFO y Bag) y sigue principios de encapsulación de ADTs.

### Características Avanzadas

Este proyecto incluye **arquitectura avanzada** con patrones de diseño profesionales:

**🔷 Polimorfismo**
- Interfaz `Identificable` implementada por Cliente, Técnico y Ticket
- Tratamiento uniforme de diferentes entidades
- Código más flexible y reutilizable

**🔷 Patrón Strategy**
- Algoritmos de asignación intercambiables (`AsignacionStrategy`)
- Implementaciones: Por Especialidad, Round-Robin
- Fácil agregar nuevas estrategias sin modificar código existente

**🔷 Patrón Observer**
- Notificaciones desacopladas de eventos (`TicketObserver`)
- Observadores: Logger, Estadísticas en tiempo real
- Sistema principal no necesita conocer a los observadores

**🔷 Escalabilidad**
- Principio Open/Closed: Abierto para extensión, cerrado para modificación
- Bajo acoplamiento entre componentes
- Alta cohesión dentro de cada componente

---

## Estructura del Proyecto

```
estructurasDeDatos/
├── src/
│   └── ticketsystem/
│       ├── enums/
│       │   ├── Estado.java          # Estados de tickets (NUEVO, ASIGNADO, etc.)
│       │   └── Categoria.java       # Categorías (RED, APLICACION, etc.)
│       ├── interfaces/
│       │   ├── Identificable.java   # Interfaz para polimorfismo
│       │   ├── AsignacionStrategy.java  # Patrón Strategy
│       │   └── TicketObserver.java  # Patrón Observer
│       ├── adt/
│       │   ├── Cliente.java         # ADT Cliente con validación de email
│       │   ├── Tecnico.java         # ADT Técnico (un ticket a la vez)
│       │   ├── Ticket.java          # ADT Ticket con gestión de estados
│       │   └── TicketSystem.java    # Coordinador principal del sistema
│       ├── datastructures/
│       │   ├── List.java            # Lista enlazada FIFO para tickets pendientes
│       │   └── Bag.java             # Bolsa (multiconjunto) para colecciones
│       ├── strategies/
│       │   ├── AsignacionPorEspecialidad.java  # Estrategia: primera coincidencia
│       │   └── AsignacionRoundRobin.java       # Estrategia: rotación equitativa
│       ├── observers/
│       │   ├── LoggerObserver.java  # Observador: logging de eventos
│       │   └── EstadisticasObserver.java  # Observador: métricas en tiempo real
│       ├── iterators/
│       │   ├── TicketsByStateIterator.java     # Filtrar tickets por estado
│       │   └── TicketsByCategoryIterator.java  # Filtrar tickets por categoría
│       ├── Demo.java                # Programa de demostración básico
│       └── DemoAvanzado.java        # Demostración de patrones de diseño
├── README.md                        # Este archivo
└── prompts.md                       # Documentación de uso de IA
```

### Archivos Principales

**Enums:**
- **Estado.java / Categoria.java**: Enums que definen los valores válidos para estados y categorías

**Estructuras de Datos:**
- **List.java**: Implementación de cola FIFO para gestionar tickets pendientes en orden de llegada
- **Bag.java**: Implementación de multiconjunto para almacenar colecciones sin orden específico

**ADTs:**
- **Cliente.java**: Representa clientes con validación de email
- **Tecnico.java**: Representa técnicos con restricción de un solo ticket asignado
- **Ticket.java**: Representa tickets con validación de transiciones de estado
- **TicketSystem.java**: Coordinador que gestiona todo el sistema

**Interfaces (Polimorfismo):**
- **Identificable.java**: Interfaz para entidades con ID único
- **AsignacionStrategy.java**: Interfaz para estrategias de asignación
- **TicketObserver.java**: Interfaz para observadores de eventos

**Estrategias (Patrón Strategy):**
- **AsignacionPorEspecialidad.java**: Asignación por primera coincidencia
- **AsignacionRoundRobin.java**: Asignación con rotación equitativa

**Observadores (Patrón Observer):**
- **LoggerObserver.java**: Registra eventos en consola
- **EstadisticasObserver.java**: Mantiene métricas en tiempo real

**Iteradores:**
- **TicketsByStateIterator.java**: Filtra tickets por estado
- **TicketsByCategoryIterator.java**: Filtra tickets por categoría

**Demos:**
- **Demo.java**: Demostración end-to-end del sistema básico
- **DemoAvanzado.java**: Demostración de polimorfismo y patrones de diseño

---

## Instrucciones de Compilación/Ejecución

### Compilar el Proyecto

Desde el directorio raíz del proyecto:

```bash
cd /home/kali/Documentos/estructurasDeDatos
javac -d bin src/ticketsystem/**/*.java src/ticketsystem/*.java
```

### Ejecutar el Programa de Demostración

**Demo Básico:**
```bash
java -cp bin ticketsystem.Demo
```

**Demo Avanzado (Polimorfismo y Patrones de Diseño):**
```bash
java -cp bin ticketsystem.DemoAvanzado
```

### Ejecutar las Pruebas (Tests)

Cada clase ADT contiene un método `main` con casos de prueba usando `assert`. Para ejecutarlos:

```bash
# Habilitar assertions con -ea (enable assertions)
java -ea -cp bin ticketsystem.adt.Cliente
java -ea -cp bin ticketsystem.adt.Tecnico
java -ea -cp bin ticketsystem.adt.Ticket
java -ea -cp bin ticketsystem.adt.TicketSystem
```

**Nota:** Si no se usa `-ea`, las assertions no se ejecutarán y las pruebas pasarán silenciosamente.

### Ejecutar Todas las Pruebas

```bash
# Script para ejecutar todas las pruebas
for clase in Cliente Tecnico Ticket TicketSystem; do
    echo "=== Ejecutando pruebas de $clase ==="
    java -ea -cp bin ticketsystem.adt.$clase
    echo ""
done
```

---

## Decisiones de Diseño

### 1. Estructuras de Datos Personalizadas

**List (Lista Enlazada FIFO):**
- Implementada como lista enlazada simple con punteros `head` y `tail`
- Garantiza orden FIFO (First In, First Out) para tickets pendientes
- Operación `add()` agrega al final, `remove()` quita del inicio
- Justificación: Los tickets deben procesarse en orden de llegada

**Bag (Bolsa/Multiconjunto):**
- Implementada como lista enlazada simple
- No garantiza orden específico, permite duplicados
- Usada para colecciones donde el orden no importa (todos los tickets, técnicos, categorías)
- Justificación: Eficiente para agregar/remover sin necesidad de orden

### 2. Validaciones Estrictas

**Transiciones de Estado:**
- Un ticket no puede pasar de NUEVO a RESUELTO directamente
- Solo se puede resolver un ticket en estado EN_PROGRESO
- Solo se puede cerrar un ticket en estado RESUELTO
- Justificación: Mantener integridad del flujo de trabajo

**Restricción de Técnicos:**
- Un técnico solo puede tener un ticket asignado a la vez
- Al resolver un ticket, el técnico se libera automáticamente
- Justificación: Simula carga de trabajo realista

**Validación de Email:**
- Formato básico: debe contener `@` y al menos un `.` después del `@`
- Justificación: Asegurar datos de contacto válidos

### 3. Asignación Automática

El método `asignarTicketAutomatico()`:
1. Toma el primer ticket de la cola FIFO
2. Busca técnicos con la especialidad requerida
3. Selecciona el primero que esté disponible
4. Asigna el ticket y actualiza estados

**Alternativa considerada:** Asignar al técnico con menos carga histórica  
**Decisión:** Implementar versión simple (primer disponible) por alcance del proyecto

### 4. Iteradores Personalizados

Implementados siguiendo el patrón `Iterator<T>` de Java:
- `TicketsByStateIterator`: Filtra tickets por estado
- `TicketsByCategoryIterator`: Filtra tickets por categoría

**Ventaja:** Permite recorrer colecciones filtradas sin crear copias de datos

### 5. Inmutabilidad de IDs

Los IDs de Cliente, Técnico y Ticket son `final`:
- Se asignan en el constructor
- No pueden modificarse después
- Justificación: Los IDs deben ser únicos e inmutables para integridad referencial

### 6. Uso de Enums

Estados y categorías implementados como `enum`:
- Previene valores inválidos
- Proporciona type-safety en tiempo de compilación
- Facilita validaciones

### 7. Patrones de Diseño (Avanzado)

**Patrón Strategy:**
- Permite intercambiar algoritmos de asignación en tiempo de ejecución
- Fácil agregar nuevas estrategias sin modificar código existente
- Justificación: Escalabilidad y flexibilidad

**Patrón Observer:**
- Desacopla el sistema principal de los componentes que reaccionan a eventos
- Permite múltiples observadores independientes
- Justificación: Extensibilidad sin modificar el core del sistema

**Interfaz Identificable:**
- Permite tratamiento polimórfico de diferentes entidades
- Código más genérico y reutilizable
- Justificación: Flexibilidad y abstracción

---

## Casos de Prueba Implementados

**Total de assertions:** Más de 30 distribuidas en 4 clases

### Cliente (6 tests)
- Creación válida de cliente
- Cliente con atributos opcionales
- Validación de email inválido
- Validación de nombre vacío
- Modificación de email
- Método `toString()`

### Técnico (5 tests)
- Creación válida de técnico
- Estado inicial disponible
- Cambio de disponibilidad
- Validación de nombre vacío
- Validación de especialidad null

### Ticket (10 tests)
- Creación válida de ticket
- Asignación de técnico
- Cambio a EN_PROGRESO
- Resolución de ticket
- Cierre de ticket
- No se puede resolver sin técnico
- No se puede cerrar sin resolver
- Cálculo de tiempo transcurrido
- Validación de descripción vacía
- Validación de transición inválida

### TicketSystem (10 tests)
- Creación de ticket
- Ticket agregado a cola pendientes
- Asignación automática FIFO
- Cola vacía después de asignación
- Cambio de estado
- Resolver ticket libera técnico
- Cerrar ticket
- Filtrar tickets por estado
- Filtrar tickets por categoría
- Generación de estadísticas

---

## Características Destacadas

✅ **Encapsulación completa:** Todos los atributos son privados con getters/setters apropiados  
✅ **Validaciones exhaustivas:** Cada operación valida precondiciones  
✅ **Manejo de excepciones:** Mensajes descriptivos para errores  
✅ **Código documentado:** Javadoc en todas las clases y métodos públicos  
✅ **Pruebas integradas:** Más de 30 assertions distribuidas  
✅ **Demostración completa:** Programa Demo muestra flujo end-to-end  
✅ **Iteradores estándar:** Siguen el patrón Iterator de Java  
✅ **FIFO garantizado:** Cola de pendientes procesa en orden de llegada  
✅ **Polimorfismo avanzado:** Interfaces y múltiples implementaciones  
✅ **Patrones de diseño:** Strategy y Observer implementados  
✅ **Escalabilidad:** Fácil extensión sin modificar código existente

---

## Contacto

Para preguntas sobre este proyecto, contactar a los integrantes del equipo listados arriba.
