/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem.observers;

import ticketsystem.adt.Ticket;
import ticketsystem.interfaces.TicketObserver;

/**
 * Observador que registra eventos en consola.
 * Implementación del patrón OBSERVER.
 */
public class LoggerObserver implements TicketObserver {

    private String nombre;

    public LoggerObserver(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void onTicketCreado(Ticket ticket) {
        System.out.println("[" + nombre + "] 📝 Ticket #" + ticket.getId() + " creado: " + ticket.getDescripcion());
    }

    @Override
    public void onTicketAsignado(Ticket ticket) {
        System.out.println(
                "[" + nombre + "] 👤 Ticket #" + ticket.getId() + " asignado a: " + ticket.getTecnico().getNombre());
    }

    @Override
    public void onTicketResuelto(Ticket ticket) {
        System.out.println(
                "[" + nombre + "] ✅ Ticket #" + ticket.getId() + " resuelto por: " + ticket.getTecnico().getNombre());
    }
}
