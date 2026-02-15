package javaOptimo.strategies;

import javaOptimo.users.Technician;
import javaOptimo.tickets.AssignmentStrategy;
import javaOptimo.tickets.Ticket;
import java.util.Map;
import java.util.Optional;

public class SimpleAssignmentStrategy implements AssignmentStrategy {
    @Override
    public Optional<Integer> assignTechnician(Ticket ticket, Map<Integer, Technician> technicians) {
        return technicians.values().stream()
                .filter(tech -> tech.getSpecialty() == ticket.getCategory() && tech.canAcceptTicket())
                .map(Technician::getId)
                .findFirst();
    }
}
