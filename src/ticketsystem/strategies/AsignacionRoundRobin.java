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
 * Estrategia de asignación round-robin (rotación equitativa).
 * Ejemplo de ESCALABILIDAD: Nueva estrategia sin modificar código existente.
 */
public class AsignacionRoundRobin implements AsignacionStrategy {

    private int ultimoIndice = 0;

    @Override
    public Tecnico asignarTecnico(Ticket ticket, Iterable<Tecnico> tecnicos) {
        // Convertir a array para acceso por índice
        java.util.List<Tecnico> lista = new java.util.ArrayList<>();
        for (Tecnico t : tecnicos) {
            if (t.getEspecialidad() == ticket.getCategoria()) {
                lista.add(t);
            }
        }

        if (lista.isEmpty()) {
            return null;
        }

        // Buscar siguiente técnico disponible desde el último índice
        int intentos = 0;
        while (intentos < lista.size()) {
            Tecnico tecnico = lista.get(ultimoIndice % lista.size());
            ultimoIndice++;

            if (tecnico.estaDisponible()) {
                return tecnico;
            }
            intentos++;
        }

        return null;
    }
}
