package tdas;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Grafo no dirigido de conexiones profesionales.
 * Cada nodo es un ID de usuario; las aristas representan contactos.
 */
public class Grafo {

    // Lista de adyacencia: ID -> conjunto de IDs conectados
    private final HashMap<String, Set<String>> adyacencia = new HashMap<>();

    /** Agrega un nodo al grafo si no existe. */
    public void agregarUsuario(String id) {
        validarId(id);
        adyacencia.putIfAbsent(id, new HashSet<>());
    }

    /**
     * Conecta dos usuarios. Ignora si ya están conectados.
     * Lanza excepción si algún ID no existe o si son el mismo.
     */
    public void conectar(String idA, String idB) {
        validarId(idA);
        validarId(idB);
        if (idA.equals(idB))
            throw new IllegalArgumentException("Un usuario no puede conectarse consigo mismo.");
        if (!adyacencia.containsKey(idA))
            throw new IllegalStateException("Usuario no encontrado en el grafo: " + idA);
        if (!adyacencia.containsKey(idB))
            throw new IllegalStateException("Usuario no encontrado en el grafo: " + idB);

        adyacencia.get(idA).add(idB);
        adyacencia.get(idB).add(idA);
    }

    /** Devuelve los contactos directos de un usuario (vista no modificable). */
    public Set<String> obtenerContactos(String id) {
        validarId(id);
        Set<String> contactos = adyacencia.get(id);
        if (contactos == null)
            throw new IllegalStateException("Usuario no encontrado en el grafo: " + id);
        return Collections.unmodifiableSet(contactos);
    }

    /** Indica si dos usuarios ya están conectados directamente. */
    public boolean estaConectado(String idA, String idB) {
        Set<String> contactos = adyacencia.get(idA);
        return contactos != null && contactos.contains(idB);
    }

    public boolean existeUsuario(String id) {
        return adyacencia.containsKey(id);
    }

    /** Devuelve una vista del mapa completo (para BFS externo). */
    public HashMap<String, Set<String>> getAdyacencia() {
        return adyacencia;
    }

    private void validarId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("El ID no puede ser vacío.");
    }
}
