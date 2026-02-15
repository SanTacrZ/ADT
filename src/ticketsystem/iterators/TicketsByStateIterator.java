/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem.iterators;

import ticketsystem.adt.Ticket;
import ticketsystem.enums.Estado;
import ticketsystem.datastructures.Bag;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterador que filtra tickets por estado.
 * Implementa el patrón Iterator de Java.
 */
public class TicketsByStateIterator implements Iterator<Ticket> {

    private Iterator<Ticket> baseIterator;
    private Estado estadoFiltro;
    private Ticket nextTicket;

    /**
     * Constructor que crea un iterador filtrado por estado.
     * 
     * @param tickets Bag de tickets a iterar
     * @param estado  Estado por el cual filtrar
     */
    public TicketsByStateIterator(Bag<Ticket> tickets, Estado estado) {
        this.baseIterator = tickets.iterator();
        this.estadoFiltro = estado;
        this.nextTicket = null;
        avanzar();
    }

    /**
     * Avanza al siguiente ticket que cumple el filtro.
     */
    private void avanzar() {
        nextTicket = null;
        while (baseIterator.hasNext()) {
            Ticket ticket = baseIterator.next();
            if (ticket.getEstado() == estadoFiltro) {
                nextTicket = ticket;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextTicket != null;
    }

    @Override
    public Ticket next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más tickets con el estado: " + estadoFiltro);
        }
        Ticket current = nextTicket;
        avanzar();
        return current;
    }
}
