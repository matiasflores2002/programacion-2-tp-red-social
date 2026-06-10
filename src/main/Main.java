package main;

import modelo.Perfil;
import modelo.Usuario;
import servicio.BuscadorRutas;
import servicio.Recomendador;
import servicio.RedSocial;
import tdas.Grafo;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        RedSocial red = new RedSocial();

        // Registrar usuarios
        red.registrarUsuario(new Usuario("u1", new Perfil("Ana García",   "ana@mail.com",   "Desarrolladora Backend")));
        red.registrarUsuario(new Usuario("u2", new Perfil("Bruno López",  "bruno@mail.com", "Diseñador UX")));
        red.registrarUsuario(new Usuario("u3", new Perfil("Carla Méndez", "carla@mail.com", "Data Scientist")));

        System.out.println("Usuarios registrados: " + red.cantidadUsuarios());
        System.out.println();

        // Buscar por ID existente
        String[] buscar = {"u1", "u3", "u99"};
        for (String id : buscar) {
            Usuario u = red.buscarPorId(id);
            if (u != null)
                System.out.println("Encontrado: " + u);
            else
                System.out.println("No existe usuario con ID: " + id);
        }

        System.out.println();

        // Validar ID duplicado
        try {
            red.registrarUsuario(new Usuario("u1", new Perfil("Otro", "otro@mail.com", "QA")));
        } catch (IllegalStateException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Módulo de conexiones ===");

        // Agregar los mismos usuarios al grafo
        Grafo grafo = new Grafo();
        grafo.agregarUsuario("u1");
        grafo.agregarUsuario("u2");
        grafo.agregarUsuario("u3");
        grafo.agregarUsuario("u4"); // sin conexiones

        // Conectar usuarios
        grafo.conectar("u1", "u2");
        grafo.conectar("u2", "u3");

        // Contactos directos
        System.out.println("Contactos de u1: " + grafo.obtenerContactos("u1"));
        System.out.println("Contactos de u2: " + grafo.obtenerContactos("u2"));

        // Evitar conexión duplicada (no lanza error, simplemente no la agrega)
        grafo.conectar("u1", "u2");
        System.out.println("Contactos de u1 tras duplicado: " + grafo.obtenerContactos("u1"));

        // Evitar auto-conexión
        try {
            grafo.conectar("u1", "u1");
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println();

        // Grado de separación con BFS
        BuscadorRutas buscador = new BuscadorRutas(grafo);
        System.out.println("u1 -> u2: grado " + buscador.gradoSeparacion("u1", "u2")); // 1
        System.out.println("u1 -> u3: grado " + buscador.gradoSeparacion("u1", "u3")); // 2
        System.out.println("u1 -> u1: grado " + buscador.gradoSeparacion("u1", "u1")); // 0
        int sinCamino = buscador.gradoSeparacion("u1", "u4");
        if (sinCamino == -1)
            System.out.println("u1 -> u4: sin camino entre ellos");
        else
            System.out.println("u1 -> u4: grado " + sinCamino);

        System.out.println();
        System.out.println("=== Módulo de recomendaciones ===");

        // Red de ejemplo:
        //  u5 -- u1 -- u2 -- u3
        //         |         /
        //        u6 -------
        // u3 y u6 tienen 2 contactos en común con u1 (a través de u2 y u6/u2)
        Grafo grafoRec = new Grafo();
        for (String id : new String[]{"u1","u2","u3","u5","u6"})
            grafoRec.agregarUsuario(id);

        grafoRec.conectar("u1", "u2");
        grafoRec.conectar("u1", "u5");
        grafoRec.conectar("u1", "u6");
        grafoRec.conectar("u2", "u3");
        grafoRec.conectar("u2", "u6"); // u6 tiene 2 comunes con u3: u1 y u2
        grafoRec.conectar("u3", "u6");

        Recomendador recomendador = new Recomendador(grafoRec);

        // Recomendaciones para u1 (no debe aparecer u2, u5, u6 que ya son contactos)
        System.out.println("Recomendaciones para u1:");
        List<Map.Entry<String, Integer>> recsU1 = recomendador.recomendar("u1");
        if (recsU1.isEmpty()) {
            System.out.println("  Sin recomendaciones.");
        } else {
            for (Map.Entry<String, Integer> rec : recsU1)
                System.out.println("  -> " + rec.getKey() + " (" + rec.getValue() + " contacto/s en común)");
        }

        // Recomendaciones para u3
        System.out.println("Recomendaciones para u3:");
        List<Map.Entry<String, Integer>> recsU3 = recomendador.recomendar("u3");
        if (recsU3.isEmpty()) {
            System.out.println("  Sin recomendaciones.");
        } else {
            for (Map.Entry<String, Integer> rec : recsU3)
                System.out.println("  -> " + rec.getKey() + " (" + rec.getValue() + " contacto/s en común)");
        }
    }
}
