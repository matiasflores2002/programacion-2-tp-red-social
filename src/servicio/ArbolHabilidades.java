package servicio;

import modelo.NodoHabilidad;

/**
 * Árbol de habilidades con raíz fija.
 * Permite agregar habilidades hijas, buscar por nombre y mostrar la jerarquía.
 */
public class ArbolHabilidades {

    private final NodoHabilidad raiz;

    public ArbolHabilidades(String nombreRaiz) {
        this.raiz = new NodoHabilidad(nombreRaiz);
    }

    /**
     * Agrega una habilidad hija bajo el nodo con el nombre dado.
     * Lanza excepción si el padre no existe.
     */
    public void agregar(String nombrePadre, String nombreHijo) {
        NodoHabilidad padre = buscarNodo(raiz, nombrePadre);
        if (padre == null)
            throw new IllegalArgumentException("No existe la habilidad: " + nombrePadre);
        padre.agregarHijo(new NodoHabilidad(nombreHijo));
    }

    /**
     * Busca un nodo por nombre. Devuelve null si no existe.
     */
    public NodoHabilidad buscar(String nombre) {
        return buscarNodo(raiz, nombre);
    }

    /** Imprime la jerarquía completa con indentación. */
    public void mostrarJerarquia() {
        mostrarRecursivo(raiz, 0);
    }

    public NodoHabilidad getRaiz() { return raiz; }

    // --- helpers privados ---

    private NodoHabilidad buscarNodo(NodoHabilidad nodo, String nombre) {
        if (nodo.getNombre().equalsIgnoreCase(nombre)) return nodo;
        for (NodoHabilidad hijo : nodo.getHijos()) {
            NodoHabilidad resultado = buscarNodo(hijo, nombre);
            if (resultado != null) return resultado;
        }
        return null;
    }

    private void mostrarRecursivo(NodoHabilidad nodo, int nivel) {
        System.out.println("  ".repeat(nivel) + nodo.getNombre());
        for (NodoHabilidad hijo : nodo.getHijos())
            mostrarRecursivo(hijo, nivel + 1);
    }
}
