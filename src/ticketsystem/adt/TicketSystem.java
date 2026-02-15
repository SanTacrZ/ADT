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

import ticketsystem.enums.Estado;
import ticketsystem.enums.Categoria;
import ticketsystem.datastructures.List;
import ticketsystem.datastructures.Bag;
import ticketsystem.interfaces.TicketObserver;
import java.util.ArrayList;

/**
 * ADT TicketSystem - Sistema principal que coordina toda la gestión de tickets.
 * 
 * Invariantes:
 * - Cada ticket debe tener un ID único en el sistema
 * - Los tickets pendientes se procesan en orden FIFO
 * - Solo se pueden crear tickets con categorías válidas
 */
public class TicketSystem {

    private List<Ticket> ticketsPendientes;
    private Bag<Ticket> todosLosTickets;
    private Bag<Tecnico> tecnicos;
    private Bag<Categoria> categorias;
    private int proximoTicketId;
    private ArrayList<TicketObserver> observadores;

    /**
     * Constructor que inicializa el sistema de tickets.
     */
    public TicketSystem() {
        this.ticketsPendientes = new List<>();
        this.todosLosTickets = new Bag<>();
        this.tecnicos = new Bag<>();
        this.categorias = new Bag<>();
        this.proximoTicketId = 1;
        this.observadores = new ArrayList<>();

        // Inicializar categorías válidas
        for (Categoria cat : Categoria.values()) {
            categorias.add(cat);
        }
    }

    /**
     * Crea y registra un nuevo ticket en el sistema.
     * 
     * @param descripcion Descripción del problema
     * @param categoria   Categoría del ticket
     * @param cliente     Cliente que reporta el ticket
     * @return El ticket creado
     * @throws IllegalArgumentException si la categoría no es válida
     */
    public Ticket crearTicket(String descripcion, Categoria categoria, Cliente cliente) {
        if (!categorias.contains(categoria)) {
            throw new IllegalArgumentException("Categoría no válida: " + categoria);
        }

        Ticket ticket = new Ticket(proximoTicketId++, descripcion, categoria, cliente);
        todosLosTickets.add(ticket);
        ticketsPendientes.add(ticket);

        // Notificar observadores
        notificarTicketCreado(ticket);

        return ticket;
    }

    /**
     * Asigna automáticamente el siguiente ticket de la cola FIFO a un técnico
     * disponible
     * con la especialidad apropiada.
     * 
     * @return El ticket asignado, o null si no hay tickets pendientes o técnicos
     *         disponibles
     */
    public Ticket asignarTicketAutomatico() {
        if (ticketsPendientes.isEmpty()) {
            return null;
        }

        Ticket ticket = ticketsPendientes.peek();
        Categoria categoriaRequerida = ticket.getCategoria();

        // Buscar técnico disponible con la especialidad requerida
        Tecnico tecnicoSeleccionado = null;
        for (Tecnico tecnico : tecnicos) {
            if (tecnico.getEspecialidad() == categoriaRequerida && tecnico.estaDisponible()) {
                tecnicoSeleccionado = tecnico;
                break;
            }
        }

        if (tecnicoSeleccionado == null) {
            // No hay técnicos disponibles con la especialidad requerida
            return null;
        }

        // Asignar el ticket
        ticketsPendientes.remove();
        ticket.asignarTecnico(tecnicoSeleccionado);
        tecnicoSeleccionado.asignarTicket(ticket);

        // Notificar observadores
        notificarTicketAsignado(ticket);

        return ticket;
    }

    /**
     * Asigna un ticket específico a un técnico específico.
     * 
     * @param ticket  Ticket a asignar
     * @param tecnico Técnico al que se asignará
     * @throws IllegalStateException si el técnico no está disponible
     */
    public void asignarTicketManual(Ticket ticket, Tecnico tecnico) {
        if (!tecnico.estaDisponible()) {
            throw new IllegalStateException("El técnico no está disponible");
        }

        // Remover de la cola de pendientes si está ahí
        ticketsPendientes.removeElement(ticket);

        ticket.asignarTecnico(tecnico);
        tecnico.asignarTicket(ticket);
    }

    /**
     * Cambia el estado de un ticket.
     * 
     * @param ticketId    ID del ticket
     * @param nuevoEstado Nuevo estado
     * @throws IllegalArgumentException si el ticket no existe
     */
    public void cambiarEstadoTicket(int ticketId, Estado nuevoEstado) {
        Ticket ticket = buscarTicketPorId(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket no encontrado: " + ticketId);
        }
        ticket.cambiarEstado(nuevoEstado);
    }

    /**
     * Marca un ticket como resuelto y libera al técnico.
     * 
     * @param ticketId ID del ticket
     * @throws IllegalArgumentException si el ticket no existe
     */
    public void resolverTicket(int ticketId) {
        Ticket ticket = buscarTicketPorId(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket no encontrado: " + ticketId);
        }

        ticket.resolver();

        // Liberar al técnico
        Tecnico tecnico = ticket.getTecnico();
        if (tecnico != null) {
            tecnico.completarTicket();
        }

        // Notificar observadores
        notificarTicketResuelto(ticket);
    }

    /**
     * Cierra un ticket resuelto.
     * 
     * @param ticketId ID del ticket
     * @throws IllegalArgumentException si el ticket no existe
     */
    public void cerrarTicket(int ticketId) {
        Ticket ticket = buscarTicketPorId(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket no encontrado: " + ticketId);
        }
        ticket.cerrar();
    }

    /**
     * Agrega un técnico al sistema.
     * 
     * @param tecnico Técnico a agregar
     */
    public void agregarTecnico(Tecnico tecnico) {
        if (tecnico == null) {
            throw new IllegalArgumentException("El técnico no puede ser null");
        }
        tecnicos.add(tecnico);
    }

    /**
     * Agrega una categoría válida al sistema.
     * 
     * @param categoria Categoría a agregar
     */
    public void agregarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser null");
        }
        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
        }
    }

    /**
     * Obtiene todos los tickets con un estado específico.
     * 
     * @param estado Estado a filtrar
     * @return Bag con los tickets que tienen ese estado
     */
    public Bag<Ticket> obtenerTicketsPorEstado(Estado estado) {
        Bag<Ticket> resultado = new Bag<>();
        for (Ticket ticket : todosLosTickets) {
            if (ticket.getEstado() == estado) {
                resultado.add(ticket);
            }
        }
        return resultado;
    }

    /**
     * Obtiene todos los tickets de una categoría específica.
     * 
     * @param categoria Categoría a filtrar
     * @return Bag con los tickets de esa categoría
     */
    public Bag<Ticket> obtenerTicketsPorCategoria(Categoria categoria) {
        Bag<Ticket> resultado = new Bag<>();
        for (Ticket ticket : todosLosTickets) {
            if (ticket.getCategoria() == categoria) {
                resultado.add(ticket);
            }
        }
        return resultado;
    }

    /**
     * Obtiene el ticket asignado a un técnico.
     * 
     * @param tecnico Técnico a consultar
     * @return El ticket asignado, o null si no tiene ninguno
     */
    public Ticket obtenerTicketDeTecnico(Tecnico tecnico) {
        return tecnico.getTicketAsignado();
    }

    /**
     * Busca un ticket por su ID.
     * 
     * @param ticketId ID del ticket
     * @return El ticket encontrado, o null si no existe
     */
    private Ticket buscarTicketPorId(int ticketId) {
        for (Ticket ticket : todosLosTickets) {
            if (ticket.getId() == ticketId) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Genera estadísticas del sistema.
     * 
     * @return String con las estadísticas
     */
    public String generarEstadisticas() {
        int totalTickets = todosLosTickets.size();
        int pendientes = ticketsPendientes.size();
        int resueltos = obtenerTicketsPorEstado(Estado.RESUELTO).size();
        int cerrados = obtenerTicketsPorEstado(Estado.CERRADO).size();
        int enProgreso = obtenerTicketsPorEstado(Estado.EN_PROGRESO).size();

        int tecnicosDisponibles = 0;
        int tecnicosOcupados = 0;
        for (Tecnico tecnico : tecnicos) {
            if (tecnico.estaDisponible()) {
                tecnicosDisponibles++;
            } else {
                tecnicosOcupados++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Estadísticas del Sistema ===\n");
        sb.append("Total de tickets: ").append(totalTickets).append("\n");
        sb.append("Tickets pendientes: ").append(pendientes).append("\n");
        sb.append("Tickets en progreso: ").append(enProgreso).append("\n");
        sb.append("Tickets resueltos: ").append(resueltos).append("\n");
        sb.append("Tickets cerrados: ").append(cerrados).append("\n");
        sb.append("Técnicos disponibles: ").append(tecnicosDisponibles).append("\n");
        sb.append("Técnicos ocupados: ").append(tecnicosOcupados).append("\n");

        return sb.toString();
    }

    // Getters

    public List<Ticket> getTicketsPendientes() {
        return ticketsPendientes;
    }

    public Bag<Ticket> getTodosLosTickets() {
        return todosLosTickets;
    }

    public Bag<Tecnico> getTecnicos() {
        return tecnicos;
    }

    /**
     * Agrega un observador al sistema.
     * Implementación del PATRÓN OBSERVER.
     * 
     * @param observador Observador a agregar
     */
    public void agregarObservador(TicketObserver observador) {
        if (observador != null) {
            observadores.add(observador);
        }
    }

    /**
     * Notifica a todos los observadores sobre la creación de un ticket.
     */
    private void notificarTicketCreado(Ticket ticket) {
        for (TicketObserver obs : observadores) {
            obs.onTicketCreado(ticket);
        }
    }

    /**
     * Notifica a todos los observadores sobre la asignación de un ticket.
     */
    private void notificarTicketAsignado(Ticket ticket) {
        for (TicketObserver obs : observadores) {
            obs.onTicketAsignado(ticket);
        }
    }

    /**
     * Notifica a todos los observadores sobre la resolución de un ticket.
     */
    private void notificarTicketResuelto(Ticket ticket) {
        for (TicketObserver obs : observadores) {
            obs.onTicketResuelto(ticket);
        }
    }

    /**
     * Método main con casos de prueba.
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas del ADT TicketSystem ===\n");

        TicketSystem sistema = new TicketSystem();

        // Crear clientes
        Cliente c1 = new Cliente(1, "Juan Pérez", "juan@example.com");
        Cliente c2 = new Cliente(2, "María García", "maria@example.com");

        // Crear técnicos
        Tecnico t1 = new Tecnico(1, "Carlos Tech", Categoria.RED);
        Tecnico t2 = new Tecnico(2, "Ana Developer", Categoria.APLICACION);
        sistema.agregarTecnico(t1);
        sistema.agregarTecnico(t2);

        // Test 1: Crear ticket
        Ticket ticket1 = sistema.crearTicket("Problema de red", Categoria.RED, c1);
        assert ticket1.getId() == 1 : "Test 1 falló: ID incorrecto";
        assert ticket1.getEstado() == Estado.NUEVO : "Test 1 falló: Estado incorrecto";
        System.out.println("✓ Test 1 pasado: Ticket creado correctamente");

        // Test 2: Ticket agregado a pendientes
        assert sistema.getTicketsPendientes().size() == 1 : "Test 2 falló: Ticket no en cola";
        System.out.println("✓ Test 2 pasado: Ticket agregado a cola de pendientes");

        // Test 3: Asignación automática
        Ticket asignado = sistema.asignarTicketAutomatico();
        assert asignado == ticket1 : "Test 3 falló: Ticket asignado incorrecto";
        assert ticket1.getTecnico() == t1 : "Test 3 falló: Técnico incorrecto";
        assert !t1.estaDisponible() : "Test 3 falló: Técnico debería estar ocupado";
        System.out.println("✓ Test 3 pasado: Asignación automática funciona");

        // Test 4: Cola FIFO vacía después de asignación
        assert sistema.getTicketsPendientes().isEmpty() : "Test 4 falló: Cola debería estar vacía";
        System.out.println("✓ Test 4 pasado: Cola FIFO funciona correctamente");

        // Test 5: Cambiar estado a EN_PROGRESO
        sistema.cambiarEstadoTicket(1, Estado.EN_PROGRESO);
        assert ticket1.getEstado() == Estado.EN_PROGRESO : "Test 5 falló: Estado incorrecto";
        System.out.println("✓ Test 5 pasado: Cambio de estado funciona");

        // Test 6: Resolver ticket libera técnico
        sistema.resolverTicket(1);
        assert ticket1.getEstado() == Estado.RESUELTO : "Test 6 falló: Estado incorrecto";
        assert t1.estaDisponible() : "Test 6 falló: Técnico debería estar disponible";
        System.out.println("✓ Test 6 pasado: Resolver ticket libera técnico");

        // Test 7: Cerrar ticket
        sistema.cerrarTicket(1);
        assert ticket1.getEstado() == Estado.CERRADO : "Test 7 falló: Estado incorrecto";
        System.out.println("✓ Test 7 pasado: Cerrar ticket funciona");

        // Test 8: Obtener tickets por estado
        Ticket ticket2 = sistema.crearTicket("Bug en aplicación", Categoria.APLICACION, c2);
        Bag<Ticket> nuevos = sistema.obtenerTicketsPorEstado(Estado.NUEVO);
        assert nuevos.size() == 1 : "Test 8 falló: Debería haber 1 ticket nuevo";
        assert nuevos.contains(ticket2) : "Test 8 falló: ticket2 debería estar en nuevos";
        System.out.println("✓ Test 8 pasado: Filtrar por estado funciona");

        // Test 9: Obtener tickets por categoría
        Bag<Ticket> ticketsRed = sistema.obtenerTicketsPorCategoria(Categoria.RED);
        assert ticketsRed.size() == 1 : "Test 9 falló: Debería haber 1 ticket de RED";
        System.out.println("✓ Test 9 pasado: Filtrar por categoría funciona");

        // Test 10: Estadísticas
        String stats = sistema.generarEstadisticas();
        assert stats.contains("Total de tickets: 2") : "Test 10 falló: Estadísticas incorrectas";
        System.out.println("✓ Test 10 pasado: Generación de estadísticas funciona");

        System.out.println("\n=== Todas las pruebas de TicketSystem pasaron ===");
    }
}
