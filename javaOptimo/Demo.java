package javaOptimo;

import javaOptimo.tickets.*;
import javaOptimo.users.*;
import javaOptimo.strategies.SimpleAssignmentStrategy;
import java.util.Optional;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== DEMO: SISTEMA DE TICKETS OPTIMIZADO (JAVA 21) ===\n");

        TicketSystem system = new TicketSystem();

        // 1. Setup Observer
        system.addObserver(new TicketObserver() {
            @Override
            public void onTicketCreated(Ticket ticket) {
                System.out.println(
                        "[NOTIFICACIÓN] Nuevo ticket creado: ID " + ticket.getId() + " - " + ticket.getDescription());
            }

            @Override
            public void onTicketAssigned(Ticket ticket, String technicianName) {
                System.out.println("[NOTIFICACIÓN] Ticket " + ticket.getId() + " asignado a: " + technicianName);
            }

            @Override
            public void onTicketResolved(Ticket ticket) {
                System.out.println("[NOTIFICACIÓN] Ticket " + ticket.getId() + " RESUELTO. Tiempo transcurrido: "
                        + ticket.getTimeElapsed().toSeconds() + " segundos.");
            }
        });

        // 2. Add Clients
        Client c1 = new Client(1, "Juan Pérez", "juan@example.com", Optional.of("TechCorp"), Optional.empty());
        Client c2 = new Client(2, "María García", "maria@example.com", Optional.empty(), Optional.of("555-0123"));
        system.addClient(c1);
        system.addClient(c2);

        // 3. Add Technicians
        Technician t1 = new Technician(1, "Carlos Redes", Category.RED);
        Technician t2 = new Technician(2, "Ana Dev", Category.APLICACION);
        system.addTechnician(t1);
        system.addTechnician(t2);

        // 4. Create Tickets
        System.out.println("--- Creando tickets ---");
        system.createTicket("No funciona el internet", Category.RED, 1);
        system.createTicket("Error en la base de datos", Category.APLICACION, 2);
        system.createTicket("Fallo de hardware", Category.HARDWARE, 1);

        System.out.println("\n" + system.getStatistics() + "\n");

        // 5. Assign Tickets
        System.out.println("--- Asignando tickets ---");
        AssignmentStrategy strategy = new SimpleAssignmentStrategy();

        system.assignNextTicket(strategy).ifPresent(id -> System.out.println("Ticket " + id + " procesado."));
        system.assignNextTicket(strategy).ifPresent(id -> System.out.println("Ticket " + id + " procesado."));
        system.assignNextTicket(strategy).ifPresent(id -> System.out
                .println("Ticket " + id + " procesado (no debería pasar si no hay técnicos para Hardware)."));

        System.out.println("\n" + system.getStatistics() + "\n");

        // 6. Update States
        System.out.println("--- Actualizando estados ---");
        system.updateTicketState(1, State.EN_PROGRESO);
        system.updateTicketState(1, State.RESUELTO);

        System.out.println("\n" + system.getStatistics() + "\n");

        // 7. Verify FIFO and Correct Logic
        System.out.println("--- Verificando liberación de técnicos ---");
        // Now t1 (RED) should be available again
        system.assignNextTicket(strategy).ifPresent(id -> System.out
                .println("No debería haber tickets pendientes para t1 o t2 en este punto (Hardware sigue pendiente)."));

        System.out.println("\n" + system.getStatistics());
        System.out.println("\n=== FIN DE LA DEMO ===");
    }
}
