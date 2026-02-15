/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem.strategies;

import ticketsystem.adt.Ticket;
import ticketsystem.adt.Tecnico;
import ticketsystem.interfaces.AsignacionStrategy;

/**
 * Estrategia de asignación por especialidad (primera coincidencia disponible).
 * Implementación del patrón STRATEGY.
 */
public class AsignacionPorEspecialidad implements AsignacionStrategy {

    @Override
    public Tecnico asignarTecnico(Ticket ticket, Iterable<Tecnico> tecnicos) {
        for (Tecnico tecnico : tecnicos) {
            if (tecnico.getEspecialidad() == ticket.getCategoria() && tecnico.estaDisponible()) {
                return tecnico;
            }
        }
        return null;
    }
}
