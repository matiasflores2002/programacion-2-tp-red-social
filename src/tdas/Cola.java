package tdas;

import java.util.LinkedList;

/**
 * Cola FIFO genérica implementada sobre LinkedList.
 */
public class Cola<T> {

    private final LinkedList<T> lista = new LinkedList<>();

    public void encolar(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("No se puede encolar un elemento nulo.");
        lista.addLast(elemento);
    }

    /** Extrae y devuelve el primero en llegar. Lanza excepción si está vacía. */
    public T desencolar() {
        if (estaVacia())
            throw new IllegalStateException("La cola está vacía.");
        return lista.removeFirst();
    }

    /** Devuelve el primero sin extraerlo. */
    public T frente() {
        if (estaVacia())
            throw new IllegalStateException("La cola está vacía.");
        return lista.getFirst();
    }

    public boolean estaVacia() { return lista.isEmpty(); }
    public int tamanio()       { return lista.size(); }

    @Override
    public String toString() { return lista.toString(); }
}
