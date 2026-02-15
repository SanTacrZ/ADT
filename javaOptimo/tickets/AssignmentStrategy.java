package javaOptimo.tickets;

import javaOptimo.users.Technician;
import java.util.Map;
import java.util.Optional;

@FunctionalInterface
public interface AssignmentStrategy {
    Optional<Integer> assignTechnician(Ticket ticket, Map<Integer, Technician> technicians);
}
