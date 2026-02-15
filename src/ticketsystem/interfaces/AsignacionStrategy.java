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
import ticketsystem.adt.Tecnico;

/**
 * Interfaz que define estrategias de asignación de tickets.
 * Ejemplo de PATRÓN STRATEGY: Permite diferentes algoritmos de asignación
 * intercambiables.
 * 
 * Esto hace el sistema ESCALABLE - se pueden agregar nuevas estrategias sin
 * modificar código existente.
 */
public interface AsignacionStrategy {
    /**
     * Asigna un ticket a un técnico según la estrategia implementada.
     * 
     * @param ticket   Ticket a asignar
     * @param tecnicos Iterable de técnicos disponibles
     * @return El técnico seleccionado, o null si no hay ninguno disponible
     */
    Tecnico asignarTecnico(Ticket ticket, Iterable<Tecnico> tecnicos);
}
