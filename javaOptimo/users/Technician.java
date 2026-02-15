package javaOptimo.users;

import javaOptimo.tickets.Category;
import java.util.Optional;

public class Technician {
    private final int id;
    private final String name;
    private final Category specialty;
    private Optional<Integer> assignedTicketId;
    private boolean isAvailable;

    public Technician(int id, String name, Category specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.assignedTicketId = Optional.empty();
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getSpecialty() {
        return specialty;
    }

    public Optional<Integer> getAssignedTicketId() {
        return assignedTicketId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAssignedTicketId(Optional<Integer> assignedTicketId) {
        this.assignedTicketId = assignedTicketId;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean canAcceptTicket() {
        return isAvailable && assignedTicketId.isEmpty();
    }
}
