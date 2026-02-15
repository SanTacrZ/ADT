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
import ticketsystem.interfaces.Identificable;
import java.time.LocalDateTime;
import java.time.Duration;

/**
 * ADT Ticket - Representa un ticket de soporte en el sistema.
 * 
 * Implementa Identificable para POLIMORFISMO: permite tratar tickets
 * junto con otras entidades identificables de forma uniforme.
 * 
 * Invariantes:
 * - El ID debe ser único y no modificable una vez creado
 * - Un ticket no puede ser resuelto sin estar asignado a un técnico
 * - Un ticket solo puede cerrarse si está en estado Resuelto
 * - Las fechas deben ser coherentes (resolución posterior a creación)
 */
public class Ticket implements Identificable {

    private final int id;
    private String descripcion;
    private Categoria categoria;
    private Estado estado;
    private Cliente cliente;
    private Tecnico tecnico;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaResolucion;

    /**
     * Constructor para crear un nuevo ticket.
     * 
     * @param id          Identificador único del ticket
     * @param descripcion Descripción del problema
     * @param categoria   Categoría del ticket
     * @param cliente     Cliente que reporta el ticket
     */
    public Ticket(int id, String descripcion, Categoria categoria, Cliente cliente) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser null");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null");
        }

        this.id = id;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.cliente = cliente;
        this.estado = Estado.NUEVO;
        this.tecnico = null;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaResolucion = null;
    }

    /**
     * Asigna un técnico al ticket.
     * 
     * @param tecnico Técnico a asignar
     * @throws IllegalArgumentException si el técnico es null
     */
    public void asignarTecnico(Tecnico tecnico) {
        if (tecnico == null) {
            throw new IllegalArgumentException("El técnico no puede ser null");
        }
        this.tecnico = tecnico;
        if (this.estado == Estado.NUEVO) {
            this.estado = Estado.ASIGNADO;
        }
    }

    /**
     * Cambia el estado del ticket.
     * 
     * @param nuevoEstado Nuevo estado del ticket
     * @throws IllegalStateException si la transición de estado no es válida
     */
    public void cambiarEstado(Estado nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }

        // Validar transiciones de estado
        switch (nuevoEstado) {
            case NUEVO:
                // Permitir volver a NUEVO (por ejemplo, al reabrir)
                break;
            case ASIGNADO:
                if (tecnico == null) {
                    throw new IllegalStateException("No se puede asignar sin técnico");
                }
                break;
            case EN_PROGRESO:
                if (tecnico == null) {
                    throw new IllegalStateException("No se puede poner en progreso sin técnico asignado");
                }
                if (estado == Estado.NUEVO) {
                    throw new IllegalStateException("No se puede pasar de NUEVO a EN_PROGRESO directamente");
                }
                break;
            case RESUELTO:
                if (tecnico == null) {
                    throw new IllegalStateException("No se puede resolver sin técnico asignado");
                }
                if (estado != Estado.EN_PROGRESO) {
                    throw new IllegalStateException("Solo se puede resolver desde EN_PROGRESO");
                }
                break;
            case CERRADO:
                if (estado != Estado.RESUELTO) {
                    throw new IllegalStateException("Solo se puede cerrar desde RESUELTO");
                }
                break;
        }

        this.estado = nuevoEstado;
    }

    /**
     * Marca el ticket como resuelto.
     * 
     * @throws IllegalStateException si el ticket no está en progreso o no tiene
     *                               técnico
     */
    public void resolver() {
        if (tecnico == null) {
            throw new IllegalStateException("No se puede resolver un ticket sin técnico asignado");
        }
        if (estado != Estado.EN_PROGRESO) {
            throw new IllegalStateException("Solo se puede resolver un ticket en progreso");
        }

        this.estado = Estado.RESUELTO;
        this.fechaResolucion = LocalDateTime.now();
    }

    /**
     * Marca el ticket como cerrado.
     * 
     * @throws IllegalStateException si el ticket no está resuelto
     */
    public void cerrar() {
        if (estado != Estado.RESUELTO) {
            throw new IllegalStateException("Solo se puede cerrar un ticket resuelto");
        }
        this.estado = Estado.CERRADO;
    }

    /**
     * Calcula el tiempo transcurrido desde la creación hasta la resolución
     * (o hasta ahora si aún no se resuelve).
     * 
     * @return Duración en minutos
     */
    public long tiempoTranscurrido() {
        LocalDateTime fin = (fechaResolucion != null) ? fechaResolucion : LocalDateTime.now();
        return Duration.between(fechaCreacion, fin).toMinutes();
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Estado getEstado() {
        return estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    // Setters limitados (solo lo que tiene sentido modificar)

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", categoria=" + categoria +
                ", estado=" + estado +
                ", cliente=" + cliente.getNombre() +
                ", tecnico=" + (tecnico != null ? tecnico.getNombre() : "sin asignar") +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaResolucion=" + fechaResolucion +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Ticket ticket = (Ticket) obj;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Método main con casos de prueba.
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas del ADT Ticket ===\n");

        // Crear objetos auxiliares
        Cliente cliente = new Cliente(1, "Juan Pérez", "juan@example.com");
        Tecnico tecnico = new Tecnico(1, "Carlos Tech", Categoria.RED);

        // Test 1: Crear ticket válido
        Ticket t1 = new Ticket(1, "Problema de conexión", Categoria.RED, cliente);
        assert t1.getId() == 1 : "Test 1 falló: ID incorrecto";
        assert t1.getEstado() == Estado.NUEVO : "Test 1 falló: Estado inicial incorrecto";
        assert t1.getTecnico() == null : "Test 1 falló: No debería tener técnico asignado";
        System.out.println("✓ Test 1 pasado: Ticket creado correctamente");

        // Test 2: Asignar técnico
        t1.asignarTecnico(tecnico);
        assert t1.getTecnico() == tecnico : "Test 2 falló: Técnico no asignado";
        assert t1.getEstado() == Estado.ASIGNADO : "Test 2 falló: Estado debería ser ASIGNADO";
        System.out.println("✓ Test 2 pasado: Técnico asignado correctamente");

        // Test 3: Cambiar a EN_PROGRESO
        t1.cambiarEstado(Estado.EN_PROGRESO);
        assert t1.getEstado() == Estado.EN_PROGRESO : "Test 3 falló: Estado incorrecto";
        System.out.println("✓ Test 3 pasado: Cambio a EN_PROGRESO exitoso");

        // Test 4: Resolver ticket
        t1.resolver();
        assert t1.getEstado() == Estado.RESUELTO : "Test 4 falló: Estado debería ser RESUELTO";
        assert t1.getFechaResolucion() != null : "Test 4 falló: Fecha de resolución no registrada";
        System.out.println("✓ Test 4 pasado: Ticket resuelto correctamente");

        // Test 5: Cerrar ticket
        t1.cerrar();
        assert t1.getEstado() == Estado.CERRADO : "Test 5 falló: Estado debería ser CERRADO";
        System.out.println("✓ Test 5 pasado: Ticket cerrado correctamente");

        // Test 6: Validación - no se puede resolver sin técnico
        Ticket t2 = new Ticket(2, "Otro problema", Categoria.APLICACION, cliente);
        try {
            t2.resolver();
            assert false : "Test 6 falló: Debería lanzar excepción al resolver sin técnico";
        } catch (IllegalStateException e) {
            System.out.println("✓ Test 6 pasado: No se puede resolver sin técnico");
        }

        // Test 7: Validación - no se puede cerrar sin estar resuelto
        Ticket t3 = new Ticket(3, "Problema de seguridad", Categoria.SEGURIDAD, cliente);
        t3.asignarTecnico(tecnico);
        try {
            t3.cerrar();
            assert false : "Test 7 falló: Debería lanzar excepción al cerrar sin resolver";
        } catch (IllegalStateException e) {
            System.out.println("✓ Test 7 pasado: No se puede cerrar sin estar resuelto");
        }

        // Test 8: Tiempo transcurrido
        long tiempo = t1.tiempoTranscurrido();
        assert tiempo >= 0 : "Test 8 falló: Tiempo transcurrido negativo";
        System.out.println("✓ Test 8 pasado: Cálculo de tiempo transcurrido funciona");

        // Test 9: Validación de descripción vacía
        try {
            new Ticket(4, "", Categoria.HARDWARE, cliente);
            assert false : "Test 9 falló: Debería lanzar excepción por descripción vacía";
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test 9 pasado: Descripción vacía rechazada");
        }

        // Test 10: Validación de transición inválida
        Ticket t4 = new Ticket(5, "Test transición", Categoria.RED, cliente);
        try {
            t4.cambiarEstado(Estado.RESUELTO);
            assert false : "Test 10 falló: No se puede ir de NUEVO a RESUELTO directamente";
        } catch (IllegalStateException e) {
            System.out.println("✓ Test 10 pasado: Transición inválida rechazada");
        }

        System.out.println("\n=== Todas las pruebas de Ticket pasaron ===");
    }
}
