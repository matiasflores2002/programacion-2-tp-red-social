package servicio;

import modelo.Postulacion;
import tdas.Cola;

/**
 * Gestiona postulaciones en orden de llegada (FIFO).
 */
public class GestorPostulaciones {

    private final Cola<Postulacion> cola = new Cola<>();

    public void encolar(Postulacion postulacion) {
        if (postulacion == null)
            throw new IllegalArgumentException("La postulación no puede ser nula.");
        cola.encolar(postulacion);
        System.out.println("Encolada: " + postulacion);
    }

    /**
     * Procesa (extrae) la siguiente postulación en orden de llegada.
     * Devuelve null si no hay postulaciones pendientes.
     */
    public Postulacion procesarSiguiente() {
        if (cola.estaVacia()) {
            System.out.println("No hay postulaciones pendientes.");
            return null;
        }
        Postulacion procesada = cola.desencolar();
        System.out.println("Procesada: " + procesada);
        return procesada;
    }

    public void mostrarPendientes() {
        if (cola.estaVacia()) {
            System.out.println("No hay postulaciones pendientes.");
            return;
        }
        System.out.println("Postulaciones pendientes (" + cola.tamanio() + "): " + cola);
    }

    public boolean hayPendientes() { return !cola.estaVacia(); }
    public int cantidadPendientes() { return cola.tamanio(); }
}
