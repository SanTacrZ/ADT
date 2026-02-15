/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem.interfaces;

import ticketsystem.adt.Ticket;

/**
 * Interfaz para observadores de eventos del sistema.
 * Ejemplo de PATRÓN OBSERVER: Permite notificaciones desacopladas.
 * 
 * Esto hace el sistema ESCALABLE - se pueden agregar nuevos observadores sin
 * modificar el sistema principal.
 */
public interface TicketObserver {
    /**
     * Notifica cuando un ticket es creado.
     * 
     * @param ticket Ticket creado
     */
    void onTicketCreado(Ticket ticket);

    /**
     * Notifica cuando un ticket es asignado.
     * 
     * @param ticket Ticket asignado
     */
    void onTicketAsignado(Ticket ticket);

    /**
     * Notifica cuando un ticket es resuelto.
     * 
     * @param ticket Ticket resuelto
     */
    void onTicketResuelto(Ticket ticket);
}
