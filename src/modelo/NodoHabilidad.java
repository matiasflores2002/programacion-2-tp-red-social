package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodoHabilidad {
    private final String nombre;
    private final List<NodoHabilidad> hijos = new ArrayList<>();

    public NodoHabilidad(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la habilidad no puede ser vacío.");
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public void agregarHijo(NodoHabilidad hijo) {
        if (hijo == null)
            throw new IllegalArgumentException("El nodo hijo no puede ser nulo.");
        hijos.add(hijo);
    }

    public List<NodoHabilidad> getHijos() {
        return Collections.unmodifiableList(hijos);
    }

    public boolean esHoja() { return hijos.isEmpty(); }
}
