package servicio;

import modelo.Postulacion;
import tdas.Cola;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona postulaciones en orden de llegada (FIFO).
 * No imprime: devuelve datos para que quien llame decida cómo mostrarlos.
 */
public class GestorPostulaciones {

    private final Cola<Postulacion> cola = new Cola<>();

    public void encolar(Postulacion postulacion) {
        if (postulacion == null)
            throw new IllegalArgumentException("La postulación no puede ser nula.");
        cola.encolar(postulacion);
    }

    /**
     * Extrae y devuelve la siguiente postulación en orden FIFO.
     * Devuelve null si no hay postulaciones pendientes.
     */
    public Postulacion procesarSiguiente() {
        if (cola.estaVacia()) return null;
        return cola.desencolar();
    }

    /**
     * Devuelve una copia de las postulaciones pendientes en orden de llegada.
     */
    public List<Postulacion> obtenerPendientes() {
        List<Postulacion> resultado = new ArrayList<>();
        // Usamos una cola auxiliar para no destruir la original
        Cola<Postulacion> auxiliar = new Cola<>();
        while (!cola.estaVacia()) {
            Postulacion p = cola.desencolar();
            resultado.add(p);
            auxiliar.encolar(p);
        }
        while (!auxiliar.estaVacia())
            cola.encolar(auxiliar.desencolar());
        return resultado;
    }

    public boolean hayPendientes()   { return !cola.estaVacia(); }
    public int cantidadPendientes()  { return cola.tamanio(); }
}
