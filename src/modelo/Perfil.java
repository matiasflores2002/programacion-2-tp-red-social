package modelo;

public class Perfil {
    private String nombre;
    private String email;
    private String titular; // ej: "Desarrollador Java"

    public Perfil(String nombre, String email, String titular) {
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
