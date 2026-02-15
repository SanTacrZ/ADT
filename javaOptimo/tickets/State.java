package javaOptimo.tickets;

public enum State {
    NUEVO("NUEVO"),
    ASIGNADO("ASIGNADO"),
    EN_PROGRESO("EN PROGRESO"),
    RESUELTO("RESUELTO"),
    CERRADO("CERRADO");

    private final String displayName;

    State(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
