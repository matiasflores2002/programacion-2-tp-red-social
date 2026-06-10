package main;

import modelo.Perfil;
import modelo.Usuario;
import servicio.BuscadorRutas;
import servicio.RedSocial;
import tdas.Grafo;

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
    }
}
