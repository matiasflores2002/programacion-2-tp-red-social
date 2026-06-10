package modelo;

import tdas.Pila;

public class Usuario {
    private final String id;
    private Perfil perfil;
    private final Pila<Perfil> historial = new Pila<>();

    public Usuario(String id, Perfil perfil) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("El ID no puede ser vacío.");
        if (perfil == null)
            throw new IllegalArgumentException("El perfil no puede ser nulo.");
        this.id = id;
        this.perfil = perfil;
    }

    public String getId()     { return id; }
    public Perfil getPerfil() { return perfil; }

    /**
     * Actualiza el perfil guardando el estado anterior en la pila.
     */
    public void actualizarPerfil(Perfil nuevo) {
        if (nuevo == null)
            throw new IllegalArgumentException("El perfil no puede ser nulo.");
        historial.apilar(perfil); // guarda el estado actual antes de pisarlo
        this.perfil = nuevo;
    }

    /**
     * Deshace la última actualización restaurando el perfil anterior.
     * Devuelve false si no hay cambios para deshacer.
     */
    public boolean deshacerCambio() {
        if (historial.estaVacia()) return false;
        this.perfil = historial.desapilar();
        return true;
    }

    public boolean tieneHistorial() { return !historial.estaVacia(); }
    public int cantidadCambios()    { return historial.tamanio(); }

    @Override
    public String toString() {
        return "[" + id + "] " + perfil;
    }
}
