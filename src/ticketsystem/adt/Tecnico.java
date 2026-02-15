/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem.adt;

import ticketsystem.enums.Categoria;
import ticketsystem.interfaces.Identificable;

/**
 * ADT Técnico - Representa un técnico de soporte.
 * 
 * Implementa Identificable para POLIMORFISMO: permite tratar técnicos
 * junto con otras entidades identificables de forma uniforme.
 * 
 * Invariantes:
 * - El técnico solo puede tener un ticket asignado a la vez
 */
public class Tecnico implements Identificable {

    private final int id;
    private String nombre;
    private Categoria especialidad;
    private Ticket ticketAsignado;
    private boolean disponible;

    /**
     * Constructor para crear un nuevo técnico.
     * 
     * @param id           Identificador único del técnico
     * @param nombre       Nombre completo del técnico
     * @param especialidad Área de especialización
     */
    public Tecnico(int id, String nombre, Categoria especialidad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (especialidad == null) {
            throw new IllegalArgumentException("La especialidad no puede ser null");
        }

        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.ticketAsignado = null;
        this.disponible = true;
    }

    /**
     * Asigna un ticket al técnico.
     * 
     * @param ticket Ticket a asignar
     * @throws IllegalStateException si el técnico ya tiene un ticket asignado
     */
    public void asignarTicket(Ticket ticket) {
        if (!disponible || ticketAsignado != null) {
            throw new IllegalStateException("El técnico ya tiene un ticket asignado");
        }
        this.ticketAsignado = ticket;
        this.disponible = false;
    }

    /**
     * Libera al técnico al completar un ticket.
     */
    public void completarTicket() {
        this.ticketAsignado = null;
        this.disponible = true;
    }

    /**
     * Verifica si el técnico puede recibir un ticket.
     * 
     * @return true si está disponible, false en caso contrario
     */
    public boolean estaDisponible() {
        return disponible && ticketAsignado == null;
    }

    /**
     * Cambia el estado de disponibilidad del técnico.
     * 
     * @param disponible Nuevo estado de disponibilidad
     */
    public void marcarDisponibilidad(boolean disponible) {
        if (!disponible && ticketAsignado != null) {
            throw new IllegalStateException("No se puede marcar como no disponible mientras tenga un ticket asignado");
        }
        this.disponible = disponible;
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria getEspecialidad() {
        return especialidad;
    }

    public Ticket getTicketAsignado() {
        return ticketAsignado;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void setEspecialidad(Categoria especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException("La especialidad no puede ser null");
        }
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Tecnico{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especialidad=" + especialidad +
                ", disponible=" + disponible +
                ", ticketAsignado=" + (ticketAsignado != null ? ticketAsignado.getId() : "ninguno") +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Tecnico tecnico = (Tecnico) obj;
        return id == tecnico.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Método main con casos de prueba.
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas del ADT Técnico ===\n");

        // Test 1: Crear técnico válido
        Tecnico t1 = new Tecnico(1, "Carlos Rodríguez", Categoria.RED);
        assert t1.getId() == 1 : "Test 1 falló: ID incorrecto";
        assert t1.getNombre().equals("Carlos Rodríguez") : "Test 1 falló: Nombre incorrecto";
        assert t1.getEspecialidad() == Categoria.RED : "Test 1 falló: Especialidad incorrecta";
        assert t1.estaDisponible() : "Test 1 falló: Técnico debería estar disponible";
        System.out.println("✓ Test 1 pasado: Técnico creado correctamente");

        // Test 2: Técnico inicia disponible
        assert t1.isDisponible() : "Test 2 falló: Técnico debería estar disponible inicialmente";
        assert t1.getTicketAsignado() == null : "Test 2 falló: No debería tener ticket asignado";
        System.out.println("✓ Test 2 pasado: Estado inicial correcto");

        // Test 3: Marcar como no disponible
        t1.marcarDisponibilidad(false);
        assert !t1.estaDisponible() : "Test 3 falló: Técnico debería estar no disponible";
        t1.marcarDisponibilidad(true); // Restaurar
        System.out.println("✓ Test 3 pasado: Cambio de disponibilidad funciona");

        // Test 4: Validación de nombre vacío
        try {
            new Tecnico(2, "", Categoria.SEGURIDAD);
            assert false : "Test 4 falló: Debería lanzar excepción por nombre vacío";
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test 4 pasado: Nombre vacío rechazado");
        }

        // Test 5: Validación de especialidad null
        try {
            new Tecnico(3, "Ana López", null);
            assert false : "Test 5 falló: Debería lanzar excepción por especialidad null";
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test 5 pasado: Especialidad null rechazada");
        }

        System.out.println("\n=== Todas las pruebas de Técnico pasaron ===");
        System.out
                .println("Nota: Pruebas adicionales de asignación de tickets se realizarán con el ADT Ticket completo");
    }
}
