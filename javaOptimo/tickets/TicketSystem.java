package javaOptimo.tickets;

import javaOptimo.users.*;
import java.time.Instant;
import java.util.*;

public class TicketSystem {
    private final Map<Integer, Ticket> tickets;
    private final Map<Integer, Technician> technicians;
    private final Map<Integer, Client> clients;
    private final Deque<Integer> pendingQueue;
    private int nextTicketId;
    private final List<TicketObserver> observers;

    public TicketSystem() {
        this.tickets = new HashMap<>();
        this.technicians = new HashMap<>();
        this.clients = new HashMap<>();
        this.pendingQueue = new ArrayDeque<>();
        this.nextTicketId = 1;
        this.observers = new ArrayList<>();
    }

    public void addTechnician(Technician tech) {
        technicians.put(tech.getId(), tech);
    }

    public void addClient(Client client) {
        clients.put(client.id(), client);
    }

    public void addObserver(TicketObserver observer) {
        observers.add(observer);
    }

    public Optional<Integer> createTicket(String description, Category category, int clientId) {
        if (!clients.containsKey(clientId)) {
            System.err.println("Cliente no encontrado: " + clientId);
            return Optional.empty();
        }

        int id = nextTicketId++;
        Ticket ticket = new Ticket(id, description, category, clientId);

        // Notify observers
        observers.forEach(obs -> obs.onTicketCreated(ticket));

        tickets.put(id, ticket);
        pendingQueue.addLast(id);

        return Optional.of(id);
    }

    public Optional<Integer> assignNextTicket(AssignmentStrategy strategy) {
        Integer ticketId = pendingQueue.peekFirst();
        if (ticketId == null)
            return Optional.empty();

        Ticket ticket = tickets.get(ticketId);
        if (ticket == null)
            return Optional.empty();

        return strategy.assignTechnician(ticket, technicians).map(techId -> {
            pendingQueue.removeFirst();

            Technician tech = technicians.get(techId);

            // Link technician to ticket
            ticket.setTechnicianId(Optional.of(techId));
            ticket.setState(State.ASIGNADO);

            // Link ticket to technician
            tech.setAssignedTicketId(Optional.of(ticketId));
            tech.setAvailable(false);

            // Notify observers
            observers.forEach(obs -> obs.onTicketAssigned(ticket, tech.getName()));

            return ticketId;
        });
    }

    public boolean updateTicketState(int ticketId, State newState) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket == null)
            return false;

        // Simple state machine validation
        State currentState = ticket.getState();
        boolean validTransition = false;

        if (currentState == State.ASIGNADO && newState == State.EN_PROGRESO) {
            validTransition = true;
        } else if (currentState == State.EN_PROGRESO && newState == State.RESUELTO) {
            validTransition = true;
            ticket.setResolvedAt(Optional.of(Instant.now()));

            // Free the technician
            ticket.getTechnicianId().ifPresent(techId -> {
                Technician tech = technicians.get(techId);
                if (tech != null) {
                    tech.setAssignedTicketId(Optional.empty());
                    tech.setAvailable(true);
                }
            });

            // Notify observers
            observers.forEach(obs -> obs.onTicketResolved(ticket));
        } else if (currentState == State.RESUELTO && newState == State.CERRADO) {
            validTransition = true;
        }

        if (validTransition) {
            ticket.setState(newState);
            return true;
        }

        System.err.println("Transición de estado inválida: " + currentState + " -> " + newState);
        return false;
    }

    public String getStatistics() {
        long total = tickets.size();
        long pending = pendingQueue.size();
        long enProgreso = tickets.values().stream().filter(t -> t.getState() == State.EN_PROGRESO).count();
        long resueltos = tickets.values().stream().filter(t -> t.getState() == State.RESUELTO).count();
        long disponibles = technicians.values().stream().filter(Technician::isAvailable).count();

        return String.format(
                "=== ESTADÍSTICAS DEL SISTEMA (JAVA OPTIMIZADO) ===\n" +
                        "Total de tickets:      %d\n" +
                        "Tickets pendientes:    %d\n" +
                        "Tickets en progreso:   %d\n" +
                        "Tickets resueltos:     %d\n" +
                        "Técnicos disponibles:  %d\n" +
                        "==================================================",
                total, pending, enProgreso, resueltos, disponibles);
    }

    public Optional<String> getTechnicianName(int id) {
        return Optional.ofNullable(technicians.get(id)).map(Technician::getName);
    }
}
