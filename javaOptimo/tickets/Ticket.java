package javaOptimo.tickets;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class Ticket {
    private final int id;
    private final String description;
    private final Category category;
    private State state;
    private final int clientId;
    private Optional<Integer> technicianId;
    private final Instant createdAt;
    private Optional<Instant> resolvedAt;

    public Ticket(int id, String description, Category category, int clientId) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.clientId = clientId;
        this.state = State.NUEVO;
        this.technicianId = Optional.empty();
        this.createdAt = Instant.now();
        this.resolvedAt = Optional.empty();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public State getState() {
        return state;
    }

    public int getClientId() {
        return clientId;
    }

    public Optional<Integer> getTechnicianId() {
        return technicianId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Optional<Instant> getResolvedAt() {
        return resolvedAt;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTechnicianId(Optional<Integer> technicianId) {
        this.technicianId = technicianId;
    }

    public void setResolvedAt(Optional<Instant> resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public Duration getTimeElapsed() {
        Instant end = resolvedAt.orElse(Instant.now());
        return Duration.between(createdAt, end);
    }
}
