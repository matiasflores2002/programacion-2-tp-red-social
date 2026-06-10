package main;

import modelo.NodoHabilidad;
import modelo.Perfil;
import modelo.Postulacion;
import modelo.Usuario;
import servicio.ArbolHabilidades;
import servicio.GestorPostulaciones;
import servicio.Recomendador;
import servicio.RedSocial;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // ── Módulo de usuarios ────────────────────────────────────────────────
        RedSocial red = new RedSocial();

        red.registrarUsuario(new Usuario("u1", new Perfil("Ana García",   "ana@mail.com",   "Desarrolladora Backend")));
        red.registrarUsuario(new Usuario("u2", new Perfil("Bruno López",  "bruno@mail.com", "Diseñador UX")));
        red.registrarUsuario(new Usuario("u3", new Perfil("Carla Méndez", "carla@mail.com", "Data Scientist")));
        red.registrarUsuario(new Usuario("u4", new Perfil("Diego Ruiz",   "diego@mail.com", "DevOps Engineer")));

        System.out.println("Usuarios registrados: " + red.cantidadUsuarios());
        System.out.println();

        String[] buscar = {"u1", "u3", "u99"};
        for (String id : buscar) {
            Usuario u = red.buscarPorId(id);
            if (u != null)
                System.out.println("Encontrado: " + u);
            else
                System.out.println("No existe usuario con ID: " + id);
        }

        System.out.println();

        // ID duplicado
        try {
            red.registrarUsuario(new Usuario("u1", new Perfil("Otro", "otro@mail.com", "QA")));
        } catch (IllegalStateException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        // ── Módulo de conexiones ──────────────────────────────────────────────
        System.out.println();
        System.out.println("=== Módulo de conexiones ===");

        red.conectar("u1", "u2");
        red.conectar("u2", "u3");

        System.out.println("Contactos de u1: " + red.obtenerContactos("u1"));
        System.out.println("Contactos de u2: " + red.obtenerContactos("u2"));

        // Conexión duplicada (no lanza error, Set la ignora)
        red.conectar("u1", "u2");
        System.out.println("Contactos de u1 tras duplicado: " + red.obtenerContactos("u1"));

        // Auto-conexión
        try {
            red.conectar("u1", "u1");
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        // Grado de separación
        System.out.println();
        System.out.println("u1 -> u2: grado " + red.gradoSeparacion("u1", "u2")); // 1
        System.out.println("u1 -> u3: grado " + red.gradoSeparacion("u1", "u3")); // 2
        System.out.println("u1 -> u1: grado " + red.gradoSeparacion("u1", "u1")); // 0
        int sinCamino = red.gradoSeparacion("u1", "u4");
        if (sinCamino == -1)
            System.out.println("u1 -> u4: sin camino entre ellos");
        else
            System.out.println("u1 -> u4: grado " + sinCamino);

        // ── Módulo de recomendaciones ─────────────────────────────────────────
        System.out.println();
        System.out.println("=== Módulo de recomendaciones ===");

        // Red de ejemplo dedicada para el recomendador
        //  u5 -- u1 -- u2 -- u3
        //         |         /
        //        u6 -------
        RedSocial redRec = new RedSocial();
        for (String id : new String[]{"u1","u2","u3","u5","u6"})
            redRec.registrarUsuario(new Usuario(id, new Perfil("Usuario " + id, id + "@mail.com", "Rol")));

        redRec.conectar("u1", "u2");
        redRec.conectar("u1", "u5");
        redRec.conectar("u1", "u6");
        redRec.conectar("u2", "u3");
        redRec.conectar("u2", "u6");
        redRec.conectar("u3", "u6");

        Recomendador recomendador = new Recomendador(redRec.getGrafo());

        System.out.println("Recomendaciones para u1:");
        List<Map.Entry<String, Integer>> recsU1 = recomendador.recomendar("u1");
        if (recsU1.isEmpty()) {
            System.out.println("  Sin recomendaciones.");
        } else {
            for (Map.Entry<String, Integer> rec : recsU1)
                System.out.println("  -> " + rec.getKey() + " (" + rec.getValue() + " contacto/s en común)");
        }

        System.out.println("Recomendaciones para u3:");
        List<Map.Entry<String, Integer>> recsU3 = recomendador.recomendar("u3");
        if (recsU3.isEmpty()) {
            System.out.println("  Sin recomendaciones.");
        } else {
            for (Map.Entry<String, Integer> rec : recsU3)
                System.out.println("  -> " + rec.getKey() + " (" + rec.getValue() + " contacto/s en común)");
        }

        // ── Módulo de postulaciones (FIFO) ────────────────────────────────────
        System.out.println();
        System.out.println("=== Módulo de postulaciones (FIFO) ===");

        GestorPostulaciones gestor = new GestorPostulaciones();
        gestor.encolar(new Postulacion("u1", "Desarrollador Java"));
        gestor.encolar(new Postulacion("u2", "Diseñador UX Senior"));
        gestor.encolar(new Postulacion("u3", "Data Analyst"));

        System.out.println("Pendientes (" + gestor.cantidadPendientes() + "):");
        for (Postulacion p : gestor.obtenerPendientes())
            System.out.println("  " + p);

        System.out.println();
        Postulacion procesada = gestor.procesarSiguiente();
        System.out.println("Procesada: " + procesada);
        procesada = gestor.procesarSiguiente();
        System.out.println("Procesada: " + procesada);

        System.out.println("Pendientes (" + gestor.cantidadPendientes() + "):");
        for (Postulacion p : gestor.obtenerPendientes())
            System.out.println("  " + p);

        System.out.println();
        System.out.println("Procesada: " + gestor.procesarSiguiente());
        Postulacion vacia = gestor.procesarSiguiente();
        System.out.println("Cola vacía devuelve: " + vacia);

        // ── Módulo de historial de perfil (LIFO) ──────────────────────────────
        System.out.println();
        System.out.println("=== Módulo de historial de perfil (LIFO) ===");

        Usuario ana = new Usuario("u1", new Perfil("Ana García", "ana@mail.com", "Desarrolladora Backend"));
        System.out.println("Perfil inicial:     " + ana.getPerfil());

        ana.actualizarPerfil(new Perfil("Ana García", "ana@mail.com", "Tech Lead"));
        System.out.println("Tras 1er cambio:    " + ana.getPerfil());

        ana.actualizarPerfil(new Perfil("Ana García", "ana.garcia@empresa.com", "CTO"));
        System.out.println("Tras 2do cambio:    " + ana.getPerfil());
        System.out.println("Cambios apilados:   " + ana.cantidadCambios());

        System.out.println();
        System.out.println("Deshacer exitoso:   " + ana.deshacerCambio());
        System.out.println("Perfil restaurado:  " + ana.getPerfil());
        ana.deshacerCambio();
        System.out.println("Perfil restaurado:  " + ana.getPerfil());
        System.out.println("Deshacer sin historial: " + ana.deshacerCambio() + " (no hay más cambios)");

        // ── Módulo de árbol de habilidades ────────────────────────────────────
        System.out.println();
        System.out.println("=== Módulo de árbol de habilidades ===");

        ArbolHabilidades arbol = new ArbolHabilidades("Tecnología");
        arbol.agregar("Tecnología", "Desarrollo");
        arbol.agregar("Tecnología", "Infraestructura");
        arbol.agregar("Desarrollo", "Java");
        arbol.agregar("Desarrollo", "Python");
        arbol.agregar("Infraestructura", "Redes");

        arbol.mostrarJerarquia();
        System.out.println();

        NodoHabilidad encontrado = arbol.buscar("Java");
        System.out.println("Búsqueda 'Java':   " + (encontrado != null ? "encontrado" : "no encontrado"));
        System.out.println("Búsqueda 'Diseño': " + (arbol.buscar("Diseño") != null ? "encontrado" : "no encontrado"));

        arbol.agregar("Java", "Spring Boot");
        System.out.println("Hijos de 'Java' tras agregar Spring Boot: "
            + arbol.buscar("Java").getHijos().stream().map(NodoHabilidad::getNombre).toList());

        try {
            arbol.agregar("Inexistente", "Algo");
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }
}
