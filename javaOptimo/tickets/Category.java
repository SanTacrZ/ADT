package javaOptimo.tickets;

public enum Category {
    RED("RED"),
    APLICACION("APLICACIÓN"),
    SEGURIDAD("SEGURIDAD"),
    HARDWARE("HARDWARE");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
