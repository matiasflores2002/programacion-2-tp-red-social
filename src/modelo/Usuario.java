package modelo;

public class Usuario {
    private final String id;
    private Perfil perfil;

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
    public void setPerfil(Perfil perfil) {
        if (perfil == null)
            throw new IllegalArgumentException("El perfil no puede ser nulo.");
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + perfil;
    }
}
