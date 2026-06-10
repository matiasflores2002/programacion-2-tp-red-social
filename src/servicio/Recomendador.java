package servicio;

import tdas.Grafo;
import java.util.*;

/**
 * Recomienda contactos basándose en la cantidad de vínculos comunes.
 * Un candidato es válido si: no es el propio usuario, no está ya conectado
 * directamente, y comparte al menos un contacto en común.
 * Las recomendaciones se ordenan de mayor a menor vínculos comunes.
 */
public class Recomendador {

    private final Grafo grafo;

    public Recomendador(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Devuelve una lista de entradas (ID candidato -> vínculos comunes)
     * ordenada de mayor a menor.
     */
    public List<Map.Entry<String, Integer>> recomendar(String idUsuario) {
        if (!grafo.existeUsuario(idUsuario))
            throw new IllegalArgumentException("Usuario no encontrado: " + idUsuario);

        Set<String> contactosDirectos = grafo.obtenerContactos(idUsuario);
        Map<String, Integer> comunes = new HashMap<>();

        // Para cada contacto directo, revisamos sus vecinos
        for (String contacto : contactosDirectos) {
            for (String candidato : grafo.obtenerContactos(contacto)) {
                if (candidato.equals(idUsuario)) continue;           // sí mismo
                if (contactosDirectos.contains(candidato)) continue; // ya conectado

                comunes.merge(candidato, 1, Integer::sum);
            }
        }

        // Ordenar por vínculos comunes descendente
        List<Map.Entry<String, Integer>> resultado = new ArrayList<>(comunes.entrySet());
        resultado.sort((a, b) -> b.getValue() - a.getValue());
        return resultado;
    }
}
