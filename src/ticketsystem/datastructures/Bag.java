/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación de una Bolsa (Bag/Multiconjunto).
 * Permite almacenar elementos sin orden particular y con posibles duplicados.
 * Utilizada para gestionar colecciones de tickets, técnicos y categorías.
 * 
 * @param <T> Tipo de elementos almacenados en la bolsa
 */
public class Bag<T> implements Iterable<T> {

    private Node<T> first;
    private int size;

    /**
     * Clase interna que representa un nodo de la bolsa.
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * Constructor que crea una bolsa vacía.
     */
    public Bag() {
        this.first = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento a la bolsa.
     * 
     * @param element Elemento a agregar
     */
    public void add(T element) {
        Node<T> oldFirst = first;
        first = new Node<>(element, oldFirst);
        size++;
    }

    /**
     * Remueve un elemento específico de la bolsa.
     * Solo remueve la primera ocurrencia encontrada.
     * 
     * @param element Elemento a remover
     * @return true si el elemento fue removido, false si no se encontró
     */
    public boolean remove(T element) {
        if (isEmpty()) {
            return false;
        }

        // Caso especial: el elemento está en el primer nodo
        if (first.data.equals(element)) {
            first = first.next;
            size--;
            return true;
        }

        // Buscar en el resto de la bolsa
        Node<T> current = first;
        while (current.next != null) {
            if (current.next.data.equals(element)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Verifica si la bolsa contiene un elemento específico.
     * 
     * @param element Elemento a buscar
     * @return true si el elemento está en la bolsa, false en caso contrario
     */
    public boolean contains(T element) {
        for (T item : this) {
            if (item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna el tamaño de la bolsa.
     * 
     * @return Número de elementos en la bolsa
     */
    public int size() {
        return size;
    }

    /**
     * Verifica si la bolsa está vacía.
     * 
     * @return true si la bolsa está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Retorna un iterador para recorrer la bolsa.
     * 
     * @return Iterator sobre los elementos de la bolsa
     */
    @Override
    public Iterator<T> iterator() {
        return new BagIterator();
    }

    /**
     * Clase interna que implementa el iterador para la bolsa.
     */
    private class BagIterator implements Iterator<T> {
        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}
