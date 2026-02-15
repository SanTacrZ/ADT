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
 * Implementación de una lista enlazada simple con comportamiento FIFO.
 * Utilizada para gestionar la cola de tickets pendientes.
 * 
 * @param <T> Tipo de elementos almacenados en la lista
 */
public class List<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Clase interna que representa un nodo de la lista.
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Constructor que crea una lista vacía.
     */
    public List() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento al final de la lista (comportamiento FIFO).
     * 
     * @param element Elemento a agregar
     */
    public void add(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Remueve y retorna el primer elemento de la lista (comportamiento FIFO).
     * 
     * @return El primer elemento de la lista
     * @throws NoSuchElementException si la lista está vacía
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("La lista está vacía");
        }

        T data = head.data;
        head = head.next;
        size--;

        if (isEmpty()) {
            tail = null;
        }

        return data;
    }

    /**
     * Remueve un elemento específico de la lista.
     * 
     * @param element Elemento a remover
     * @return true si el elemento fue removido, false si no se encontró
     */
    public boolean removeElement(T element) {
        if (isEmpty()) {
            return false;
        }

        // Caso especial: el elemento está en la cabeza
        if (head.data.equals(element)) {
            head = head.next;
            size--;
            if (isEmpty()) {
                tail = null;
            }
            return true;
        }

        // Buscar en el resto de la lista
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(element)) {
                current.next = current.next.next;
                if (current.next == null) {
                    tail = current;
                }
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Obtiene el elemento en la posición especificada.
     * 
     * @param index Índice del elemento (0-based)
     * @return El elemento en la posición especificada
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    /**
     * Retorna el primer elemento sin removerlo.
     * 
     * @return El primer elemento de la lista
     * @throws NoSuchElementException si la lista está vacía
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        return head.data;
    }

    /**
     * Retorna el tamaño de la lista.
     * 
     * @return Número de elementos en la lista
     */
    public int size() {
        return size;
    }

    /**
     * Verifica si la lista está vacía.
     * 
     * @return true si la lista está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retorna un iterador para recorrer la lista.
     * 
     * @return Iterator sobre los elementos de la lista
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    /**
     * Clase interna que implementa el iterador para la lista.
     */
    private class ListIterator implements Iterator<T> {
        private Node<T> current = head;

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
