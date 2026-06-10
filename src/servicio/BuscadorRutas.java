package servicio;

import tdas.Grafo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Calcula el grado de separación entre dos usuarios usando BFS.
 */
public class BuscadorRutas {

    private final Grafo grafo;

    public BuscadorRutas(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Devuelve el grado de separación entre origen y destino.
     * Retorna 0 si son el mismo usuario.
     * Retorna -1 si no existe camino entre ellos.
     */
    public int gradoSeparacion(String origen, String destino) {
        if (origen.equals(destino)) return 0;

        if (!grafo.existeUsuario(origen) || !grafo.existeUsuario(destino))
            throw new IllegalArgumentException("Uno o ambos usuarios no existen en el grafo.");

        Queue<String> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        HashMap<String, Integer> distancia = new HashMap<>();

        cola.add(origen);
        visitados.add(origen);
        distancia.put(origen, 0);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            int dist = distancia.get(actual);

            for (String vecino : grafo.obtenerContactos(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    distancia.put(vecino, dist + 1);

                    if (vecino.equals(destino))
                        return distancia.get(vecino);

                    cola.add(vecino);
                }
            }
        }

        return -1; // sin camino
    }
}
