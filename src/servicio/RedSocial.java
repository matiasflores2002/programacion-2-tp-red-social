package servicio;

import modelo.Usuario;
import java.util.HashMap;

public class RedSocial {

    // Búsqueda por ID en O(1) promedio
    private final HashMap<String, Usuario> usuarios = new HashMap<>();

    /**
     * Registra un nuevo usuario.
     * Lanza excepción si el ID ya existe.
     */
    public void registrarUsuario(Usuario usuario) {
        if (usuario == null)
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        if (usuarios.containsKey(usuario.getId()))
            throw new IllegalStateException("Ya existe un usuario con ID: " + usuario.getId());
        usuarios.put(usuario.getId(), usuario);
    }

    /**
     * Busca un usuario por ID. Devuelve null si no existe.
     */
    public Usuario buscarPorId(String id) {
        return usuarios.get(id);
    }

    public int cantidadUsuarios() {
        return usuarios.size();
    }
}
