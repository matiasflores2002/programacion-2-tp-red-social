package modelo;

public class Perfil {
    private String nombre;
    private String email;
    private String cargo; // ej: "Desarrollador Java"

    public Perfil(String nombre, String email, String cargo) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("El email no puede ser vacío.");
        if (cargo == null || cargo.isBlank())
            throw new IllegalArgumentException("El cargo no puede ser vacío.");
        this.nombre = nombre;
        this.email = email;
        this.cargo = cargo;
    }

    public String getNombre() { return nombre; }
    public String getEmail()  { return email; }
    public String getCargo()  { return cargo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email)   { this.email = email; }
    public void setCargo(String cargo)   { this.cargo = cargo; }

    @Override
    public String toString() {
        return nombre + " | " + cargo + " | " + email;
    }
}
