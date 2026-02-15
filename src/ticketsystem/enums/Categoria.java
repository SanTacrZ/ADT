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
 * Enum que representa las categorías de tickets en el sistema.
 * 
 * Categorías válidas:
 * - RED: Problemas relacionados con redes y conectividad
 * - BASE_DE_DATOS: Problemas con bases de datos
 * - APLICACION: Problemas con aplicaciones de software
 * - SEGURIDAD: Problemas de seguridad informática
 * - HARDWARE: Problemas con hardware físico
 */
public enum Categoria {
    RED,
    BASE_DE_DATOS,
    APLICACION,
    SEGURIDAD,
    HARDWARE
}
