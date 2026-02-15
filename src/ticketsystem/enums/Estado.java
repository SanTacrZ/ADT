/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem.enums;

/**
 * Enum que representa los estados posibles de un ticket en el sistema.
 * 
 * Estados válidos:
 * - NUEVO: Ticket recién creado, esperando asignación
 * - ASIGNADO: Ticket asignado a un técnico pero aún no en progreso
 * - EN_PROGRESO: Técnico está trabajando activamente en el ticket
 * - RESUELTO: Problema resuelto, esperando cierre
 * - CERRADO: Ticket completamente cerrado
 */
public enum Estado {
    NUEVO,
    ASIGNADO,
    EN_PROGRESO,
    RESUELTO,
    CERRADO
}
