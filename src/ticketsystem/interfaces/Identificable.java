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

/**
 * Interfaz para entidades que pueden ser identificadas de forma única.
 * Ejemplo de POLIMORFISMO: Permite tratar diferentes entidades de forma
 * uniforme.
 */
public interface Identificable {
    /**
     * Obtiene el identificador único de la entidad.
     * 
     * @return ID único
     */
    int getId();
}
