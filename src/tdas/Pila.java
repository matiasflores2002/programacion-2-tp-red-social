package tdas;

import java.util.LinkedList;

/**
 * Pila LIFO genérica implementada sobre LinkedList.
 */
public class Pila<T> {

    private final LinkedList<T> lista = new LinkedList<>();

    public void apilar(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("No se puede apilar un elemento nulo.");
        lista.addFirst(elemento);
    }

    /** Extrae y devuelve el último elemento apilado. Lanza excepción si está vacía. */
    public T desapilar() {
        if (estaVacia())
            throw new IllegalStateException("La pila está vacía.");
        return lista.removeFirst();
    }

    /** Devuelve el tope sin extraerlo. */
    public T tope() {
        if (estaVacia())
            throw new IllegalStateException("La pila está vacía.");
        return lista.getFirst();
    }

    public boolean estaVacia() { return lista.isEmpty(); }
    public int tamanio()       { return lista.size(); }
}
