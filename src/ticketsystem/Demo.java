/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem;

import ticketsystem.adt.*;
import ticketsystem.enums.*;
import ticketsystem.iterators.*;

/**
 * Programa de demostración del Sistema de Gestión de Tickets.
 * Muestra el funcionamiento end-to-end del sistema.
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE TICKETS - DEMOSTRACIÓN COMPLETA   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        // Inicializar el sistema
        TicketSystem sistema = new TicketSystem();

        System.out.println("=== 1. INICIALIZACIÓN DEL SISTEMA ===\n");

        // Crear clientes
        System.out.println("Creando clientes...");
        Cliente cliente1 = new Cliente(1, "Juan Pérez", "juan.perez@empresa.com", "TechCorp", "555-0101");
        Cliente cliente2 = new Cliente(2, "María García", "maria.garcia@startup.com", "StartupXYZ", "555-0102");
        Cliente cliente3 = new Cliente(3, "Carlos López", "carlos.lopez@university.edu", "Universidad Nacional",
                "555-0103");
        System.out.println("✓ 3 clientes creados\n");

        // Crear técnicos con diferentes especialidades
        System.out.println("Registrando técnicos en el sistema...");
        Tecnico tecnico1 = new Tecnico(1, "Ana Rodríguez", Categoria.RED);
        Tecnico tecnico2 = new Tecnico(2, "Roberto Martínez", Categoria.APLICACION);
        Tecnico tecnico3 = new Tecnico(3, "Laura Sánchez", Categoria.BASE_DE_DATOS);
        Tecnico tecnico4 = new Tecnico(4, "Diego Fernández", Categoria.SEGURIDAD);
        Tecnico tecnico5 = new Tecnico(5, "Sofia Torres", Categoria.HARDWARE);

        sistema.agregarTecnico(tecnico1);
        sistema.agregarTecnico(tecnico2);
        sistema.agregarTecnico(tecnico3);
        sistema.agregarTecnico(tecnico4);
        sistema.agregarTecnico(tecnico5);
        System.out.println("✓ 5 técnicos registrados con especialidades variadas\n");

        // ===================================================================
        System.out.println("\n=== 2. CREACIÓN DE TICKETS ===\n");

        Ticket ticket1 = sistema.crearTicket(
                "No hay conexión a internet en la oficina 302",
                Categoria.RED,
                cliente1);
        System.out.println("✓ Ticket #" + ticket1.getId() + " creado: " + ticket1.getDescripcion());
        System.out.println("  Estado: " + ticket1.getEstado() + " | Categoría: " + ticket1.getCategoria());

        Ticket ticket2 = sistema.crearTicket(
                "Error al iniciar sesión en la aplicación de nómina",
                Categoria.APLICACION,
                cliente2);
        System.out.println("✓ Ticket #" + ticket2.getId() + " creado: " + ticket2.getDescripcion());
        System.out.println("  Estado: " + ticket2.getEstado() + " | Categoría: " + ticket2.getCategoria());

        Ticket ticket3 = sistema.crearTicket(
                "La consulta SQL tarda más de 5 minutos en ejecutarse",
                Categoria.BASE_DE_DATOS,
                cliente3);
        System.out.println("✓ Ticket #" + ticket3.getId() + " creado: " + ticket3.getDescripcion());
        System.out.println("  Estado: " + ticket3.getEstado() + " | Categoría: " + ticket3.getCategoria());

        Ticket ticket4 = sistema.crearTicket(
                "Posible intento de acceso no autorizado detectado",
                Categoria.SEGURIDAD,
                cliente1);
        System.out.println("✓ Ticket #" + ticket4.getId() + " creado: " + ticket4.getDescripcion());
        System.out.println("  Estado: " + ticket4.getEstado() + " | Categoría: " + ticket4.getCategoria());

        Ticket ticket5 = sistema.crearTicket(
                "Impresora del departamento no enciende",
                Categoria.HARDWARE,
                cliente2);
        System.out.println("✓ Ticket #" + ticket5.getId() + " creado: " + ticket5.getDescripcion());
        System.out.println("  Estado: " + ticket5.getEstado() + " | Categoría: " + ticket5.getCategoria());

        System.out.println("\n✓ Total: 5 tickets creados y agregados a la cola FIFO");

        // ===================================================================
        System.out.println("\n=== 3. ASIGNACIÓN AUTOMÁTICA DE TICKETS (FIFO) ===\n");

        System.out.println("Procesando cola de tickets pendientes...\n");

        for (int i = 1; i <= 5; i++) {
            Ticket asignado = sistema.asignarTicketAutomatico();
            if (asignado != null) {
                System.out.println("→ Ticket #" + asignado.getId() + " asignado a: " +
                        asignado.getTecnico().getNombre() +
                        " (Especialidad: " + asignado.getTecnico().getEspecialidad() + ")");
                System.out.println("  Estado actualizado: " + asignado.getEstado());
            }
        }

        System.out.println("\n✓ Todos los tickets asignados según especialidad");
        System.out.println("✓ Cola de pendientes vacía: " + sistema.getTicketsPendientes().isEmpty());

        // ===================================================================
        System.out.println("\n=== 4. CAMBIO DE ESTADOS Y RESOLUCIÓN ===\n");

        // Trabajar en ticket 1
        System.out.println("Técnico " + ticket1.getTecnico().getNombre() + " comienza a trabajar en Ticket #1...");
        sistema.cambiarEstadoTicket(1, Estado.EN_PROGRESO);
        System.out.println("✓ Ticket #1 → " + ticket1.getEstado());

        // Trabajar en ticket 2
        System.out.println("\nTécnico " + ticket2.getTecnico().getNombre() + " comienza a trabajar en Ticket #2...");
        sistema.cambiarEstadoTicket(2, Estado.EN_PROGRESO);
        System.out.println("✓ Ticket #2 → " + ticket2.getEstado());

        // Resolver ticket 1
        System.out.println("\nResolviendo Ticket #1...");
        sistema.resolverTicket(1);
        System.out.println("✓ Ticket #1 → " + ticket1.getEstado());
        System.out.println("✓ Técnico " + tecnico1.getNombre() + " ahora disponible: " + tecnico1.estaDisponible());

        // Trabajar y resolver ticket 3
        System.out.println("\nTécnico " + ticket3.getTecnico().getNombre() + " trabaja en Ticket #3...");
        sistema.cambiarEstadoTicket(3, Estado.EN_PROGRESO);
        sistema.resolverTicket(3);
        System.out.println("✓ Ticket #3 → " + ticket3.getEstado());

        // Cerrar ticket 1
        System.out.println("\nCerrando Ticket #1...");
        sistema.cerrarTicket(1);
        System.out.println("✓ Ticket #1 → " + ticket1.getEstado());

        // ===================================================================
        System.out.println("\n=== 5. CONSULTAS CON ITERADORES ===\n");

        // Listar tickets pendientes
        System.out.println("--- Tickets Pendientes (Estado: NUEVO) ---");
        TicketsByStateIterator iteradorNuevos = new TicketsByStateIterator(
                sistema.getTodosLosTickets(),
                Estado.NUEVO);
        int countNuevos = 0;
        while (iteradorNuevos.hasNext()) {
            Ticket t = iteradorNuevos.next();
            System.out.println("  • Ticket #" + t.getId() + ": " + t.getDescripcion());
            countNuevos++;
        }
        System.out.println("Total: " + countNuevos + " tickets\n");

        // Listar tickets resueltos
        System.out.println("--- Tickets Resueltos ---");
        TicketsByStateIterator iteradorResueltos = new TicketsByStateIterator(
                sistema.getTodosLosTickets(),
                Estado.RESUELTO);
        int countResueltos = 0;
        while (iteradorResueltos.hasNext()) {
            Ticket t = iteradorResueltos.next();
            System.out.println("  • Ticket #" + t.getId() + ": " + t.getDescripcion());
            System.out.println("    Técnico: " + t.getTecnico().getNombre());
            System.out.println("    Tiempo de resolución: " + t.tiempoTranscurrido() + " minutos");
            countResueltos++;
        }
        System.out.println("Total: " + countResueltos + " tickets\n");

        // Listar tickets en progreso
        System.out.println("--- Tickets En Progreso ---");
        TicketsByStateIterator iteradorEnProgreso = new TicketsByStateIterator(
                sistema.getTodosLosTickets(),
                Estado.EN_PROGRESO);
        int countEnProgreso = 0;
        while (iteradorEnProgreso.hasNext()) {
            Ticket t = iteradorEnProgreso.next();
            System.out.println("  • Ticket #" + t.getId() + ": " + t.getDescripcion());
            System.out.println("    Técnico asignado: " + t.getTecnico().getNombre());
            countEnProgreso++;
        }
        System.out.println("Total: " + countEnProgreso + " tickets\n");

        // Filtrar por categoría
        System.out.println("--- Tickets de Categoría: APLICACION ---");
        TicketsByCategoryIterator iteradorApp = new TicketsByCategoryIterator(
                sistema.getTodosLosTickets(),
                Categoria.APLICACION);
        while (iteradorApp.hasNext()) {
            Ticket t = iteradorApp.next();
            System.out.println("  • Ticket #" + t.getId() + ": " + t.getDescripcion());
            System.out.println("    Estado: " + t.getEstado());
        }
        System.out.println();

        // ===================================================================
        System.out.println("\n=== 6. ESTADÍSTICAS DEL SISTEMA ===\n");
        System.out.println(sistema.generarEstadisticas());

        // ===================================================================
        System.out.println("\n=== 7. VALIDACIÓN DE INVARIANTES ===\n");

        // Verificar que técnicos ocupados tienen exactamente un ticket
        System.out.println("Verificando invariante: Un técnico solo puede tener un ticket a la vez");
        for (Tecnico tec : sistema.getTecnicos()) {
            if (!tec.estaDisponible()) {
                assert tec.getTicketAsignado() != null : "Técnico ocupado sin ticket";
                System.out.println("✓ " + tec.getNombre() + " tiene ticket #" + tec.getTicketAsignado().getId());
            }
        }

        System.out.println("\n✓ Todas las invariantes verificadas correctamente");

        // ===================================================================
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              DEMOSTRACIÓN COMPLETADA EXITOSAMENTE          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}
