/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Sebastian Santacruz] - ID: [000298603]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem;

import ticketsystem.adt.*;
import ticketsystem.enums.*;
import ticketsystem.interfaces.*;
import ticketsystem.strategies.*;
import ticketsystem.observers.*;

/**
 * Demostración AVANZADA del sistema con POLIMORFISMO y patrones de diseño.
 * 
 * Este demo muestra:
 * - POLIMORFISMO: Uso de interfaces (Identificable, AsignacionStrategy,
 * TicketObserver)
 * - PATRÓN STRATEGY: Diferentes algoritmos de asignación intercambiables
 * - PATRÓN OBSERVER: Notificaciones desacopladas de eventos
 * - ESCALABILIDAD: Fácil extensión sin modificar código existente
 */
public class DemoAvanzado {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  DEMOSTRACIÓN AVANZADA - POLIMORFISMO Y PATRONES DE DISEÑO   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        // ===================================================================
        System.out.println("=== 1. POLIMORFISMO CON INTERFAZ IDENTIFICABLE ===\n");

        Cliente cliente1 = new Cliente(1, "Ana López", "ana@empresa.com");
        Tecnico tecnico1 = new Tecnico(1, "Carlos Tech", Categoria.RED);
        Ticket ticket1 = new Ticket(1, "Problema de red", Categoria.RED, cliente1);

        // POLIMORFISMO: Tratar diferentes entidades de forma uniforme
        Identificable[] entidades = { cliente1, tecnico1, ticket1 };

        System.out.println("Demostrando POLIMORFISMO - todas estas entidades implementan Identificable:");
        for (Identificable entidad : entidades) {
            System.out.println("  → Entidad ID: " + entidad.getId() + " | Tipo: " + entidad.getClass().getSimpleName());
        }

        // ===================================================================
        System.out.println("\n=== 2. PATRÓN STRATEGY - ALGORITMOS INTERCAMBIABLES ===\n");

        TicketSystem sistema = new TicketSystem();

        // Crear técnicos
        Tecnico t1 = new Tecnico(10, "María Red", Categoria.RED);
        Tecnico t2 = new Tecnico(11, "Pedro Red", Categoria.RED);
        Tecnico t3 = new Tecnico(12, "Luis App", Categoria.APLICACION);
        sistema.agregarTecnico(t1);
        sistema.agregarTecnico(t2);
        sistema.agregarTecnico(t3);

        // Crear clientes y tickets
        Cliente c1 = new Cliente(100, "Juan Pérez", "juan@test.com");
        Ticket tk1 = sistema.crearTicket("Problema red 1", Categoria.RED, c1);
        Ticket tk2 = sistema.crearTicket("Problema red 2", Categoria.RED, c1);
        Ticket tk3 = sistema.crearTicket("Problema red 3", Categoria.RED, c1);

        System.out.println("Estrategia 1: Asignación por Especialidad (primera coincidencia)");
        AsignacionStrategy estrategia1 = new AsignacionPorEspecialidad();

        Tecnico asignado1 = estrategia1.asignarTecnico(tk1, sistema.getTecnicos());
        System.out.println("  → Ticket #" + tk1.getId() + " asignado a: " + asignado1.getNombre());

        System.out.println("\nEstrategia 2: Asignación Round-Robin (rotación equitativa)");
        AsignacionStrategy estrategia2 = new AsignacionRoundRobin();

        Tecnico asignado2 = estrategia2.asignarTecnico(tk2, sistema.getTecnicos());
        Tecnico asignado3 = estrategia2.asignarTecnico(tk3, sistema.getTecnicos());
        System.out.println("  → Ticket #" + tk2.getId() + " asignado a: " + asignado2.getNombre());
        System.out.println("  → Ticket #" + tk3.getId() + " asignado a: " + asignado3.getNombre());
        System.out.println("  ✓ Round-robin distribuye la carga equitativamente");

        // ===================================================================
        System.out.println("\n=== 3. PATRÓN OBSERVER - NOTIFICACIONES DESACOPLADAS ===\n");

        // Crear observadores
        LoggerObserver logger = new LoggerObserver("LOGGER");
        EstadisticasObserver stats = new EstadisticasObserver();

        System.out.println("Creando sistema con observadores...\n");

        TicketSystem sistemaConObservers = new TicketSystem();
        sistemaConObservers.agregarObservador(logger);
        sistemaConObservers.agregarObservador(stats);

        // Agregar técnicos
        Tecnico tec1 = new Tecnico(20, "Roberto Seguridad", Categoria.SEGURIDAD);
        Tecnico tec2 = new Tecnico(21, "Elena Ciber", Categoria.SEGURIDAD);
        sistemaConObservers.agregarTecnico(tec1);
        sistemaConObservers.agregarTecnico(tec2);

        // Crear cliente y tickets - los observadores serán notificados
        Cliente cliente2 = new Cliente(200, "María García", "maria@test.com");

        System.out.println("Creando tickets (observadores serán notificados):");
        Ticket ticket10 = sistemaConObservers.crearTicket(
                "Intento de acceso no autorizado",
                Categoria.SEGURIDAD,
                cliente2);

        Ticket ticket11 = sistemaConObservers.crearTicket(
                "Firewall bloqueado",
                Categoria.SEGURIDAD,
                cliente2);

        System.out.println("\nAsignando tickets (observadores serán notificados):");
        sistemaConObservers.asignarTicketAutomatico();
        sistemaConObservers.asignarTicketAutomatico();

        System.out.println("\nResolviendo tickets (observadores serán notificados):");
        sistemaConObservers.cambiarEstadoTicket(ticket10.getId(), Estado.EN_PROGRESO);
        sistemaConObservers.resolverTicket(ticket10.getId());

        sistemaConObservers.cambiarEstadoTicket(ticket11.getId(), Estado.EN_PROGRESO);
        sistemaConObservers.resolverTicket(ticket11.getId());

        // Mostrar estadísticas recolectadas por el observador
        stats.mostrarEstadisticas();

        // ===================================================================
        System.out.println("\n=== 4. ESCALABILIDAD - EXTENSIÓN SIN MODIFICACIÓN ===\n");

        System.out.println(
                "✓ Nuevas estrategias de asignación: Solo crear nueva clase que implemente AsignacionStrategy");
        System.out.println("✓ Nuevos observadores: Solo crear nueva clase que implemente TicketObserver");
        System.out.println("✓ Nuevas entidades identificables: Solo implementar interfaz Identificable");
        System.out.println("\n→ Principio Open/Closed: Abierto para extensión, cerrado para modificación");

        // ===================================================================
        System.out.println("\n=== 5. BENEFICIOS DE ESTE DISEÑO ===\n");

        System.out.println("POLIMORFISMO:");
        System.out.println("  • Tratamiento uniforme de entidades diferentes");
        System.out.println("  • Código más flexible y reutilizable");

        System.out.println("\nPATRÓN STRATEGY:");
        System.out.println("  • Algoritmos de asignación intercambiables en tiempo de ejecución");
        System.out.println("  • Fácil agregar nuevas estrategias sin modificar código existente");

        System.out.println("\nPATRÓN OBSERVER:");
        System.out.println("  • Notificaciones desacopladas");
        System.out.println("  • Múltiples observadores pueden reaccionar a eventos");
        System.out.println("  • Sistema principal no necesita conocer a los observadores");

        System.out.println("\nESCALABILIDAD:");
        System.out.println("  • Fácil agregar nuevas funcionalidades");
        System.out.println("  • Bajo acoplamiento entre componentes");
        System.out.println("  • Alto cohesión dentro de cada componente");

        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║         DEMOSTRACIÓN AVANZADA COMPLETADA EXITOSAMENTE         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }
}
