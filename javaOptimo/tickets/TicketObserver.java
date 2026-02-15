package javaOptimo.tickets;

public interface TicketObserver {
    void onTicketCreated(Ticket ticket);

    void onTicketAssigned(Ticket ticket, String technicianName);

    void onTicketResolved(Ticket ticket);
}
