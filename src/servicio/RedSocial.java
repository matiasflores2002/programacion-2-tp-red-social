package servicio;

import modelo.Usuario;
import tdas.Grafo;
import java.util.HashMap;
import java.util.Set;

public class RedSocial {

    private final HashMap<String, Usuario> usuarios = new HashMap<>();
    private final Grafo grafo = new Grafo();
    private final BuscadorRutas buscadorRutas = new BuscadorRutas(grafo);

    /**
     * Registra un nuevo usuario y lo agrega al grafo.
     * Lanza excepción si el ID ya existe.
     */
    public void registrarUsuario(Usuario usuario) {
        if (usuario == null)
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        if (usuarios.containsKey(usuario.getId()))
            throw new IllegalStateException("Ya existe un usuario con ID: " + usuario.getId());
        usuarios.put(usuario.getId(), usuario);
        grafo.agregarUsuario(usuario.getId());
    }

    /** Busca un usuario por ID en O(1). Devuelve null si no existe. */
    public Usuario buscarPorId(String id) {
        return usuarios.get(id);
    }

    /**
     * Conecta dos usuarios ya registrados.
     * Lanza excepción si algún ID no existe o si son el mismo.
     */
    public void conectar(String idA, String idB) {
        validarRegistrado(idA);
        validarRegistrado(idB);
        grafo.conectar(idA, idB);
    }

    /** Devuelve los contactos directos de un usuario. */
    public Set<String> obtenerContactos(String id) {
        validarRegistrado(id);
        return grafo.obtenerContactos(id);
    }

    /**
     * Calcula el grado de separación entre dos usuarios usando BFS.
     * Devuelve 0 si son el mismo, -1 si no hay camino.
     */
    public int gradoSeparacion(String idOrigen, String idDestino) {
        validarRegistrado(idOrigen);
        validarRegistrado(idDestino);
        return buscadorRutas.gradoSeparacion(idOrigen, idDestino);
    }

    /** Expone el grafo para servicios externos como Recomendador. */
    public Grafo getGrafo() { return grafo; }

    public int cantidadUsuarios() { return usuarios.size(); }

    private void validarRegistrado(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("El ID no puede ser vacío.");
        if (!usuarios.containsKey(id))
            throw new IllegalStateException("Usuario no registrado: " + id);
    }
}
