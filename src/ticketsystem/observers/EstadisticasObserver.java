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
 * Observador que mantiene estadísticas en tiempo real.
 * Ejemplo de ESCALABILIDAD: Nuevo observador sin modificar sistema principal.
 */
public class EstadisticasObserver implements TicketObserver {

    private int ticketsCreados = 0;
    private int ticketsAsignados = 0;
    private int ticketsResueltos = 0;

    @Override
    public void onTicketCreado(Ticket ticket) {
        ticketsCreados++;
    }

    @Override
    public void onTicketAsignado(Ticket ticket) {
        ticketsAsignados++;
    }

    @Override
    public void onTicketResuelto(Ticket ticket) {
        ticketsResueltos++;
    }

    public void mostrarEstadisticas() {
        System.out.println("\n=== Estadísticas en Tiempo Real ===");
        System.out.println("Tickets creados: " + ticketsCreados);
        System.out.println("Tickets asignados: " + ticketsAsignados);
        System.out.println("Tickets resueltos: " + ticketsResueltos);
    }
}
