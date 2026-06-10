package modelo;

public class Perfil {
    private String nombre;
    private String email;
    private String titular; // ej: "Desarrollador Java"

    public Perfil(String nombre, String email, String titular) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("El email no puede ser vacío.");
        if (titular == null || titular.isBlank())
            throw new IllegalArgumentException("El titular no puede ser vacío.");
        this.nombre = nombre;
        this.email = email;
        this.titular = titular;
    }

    public String getNombre() { return nombre; }
    public String getEmail()  { return email; }
    public String getTitular() { return titular; }

    public void setNombre(String nombre)   { this.nombre = nombre; }
    public void setEmail(String email)     { this.email = email; }
    public void setTitular(String titular) { this.titular = titular; }

    @Override
    public String toString() {
        return nombre + " | " + titular + " | " + email;
    }
}
