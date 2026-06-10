package main;

import modelo.Perfil;
import modelo.Usuario;
import servicio.RedSocial;

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
    }
}
