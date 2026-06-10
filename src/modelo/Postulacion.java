package modelo;

public class Postulacion {
    private final String idUsuario;
    private final String puesto;

    public Postulacion(String idUsuario, String puesto) {
        if (idUsuario == null || idUsuario.isBlank())
            throw new IllegalArgumentException("El ID de usuario no puede ser vacío.");
        if (puesto == null || puesto.isBlank())
            throw new IllegalArgumentException("El puesto no puede ser vacío.");
        this.idUsuario = idUsuario;
        this.puesto = puesto;
    }

    public String getIdUsuario() { return idUsuario; }
    public String getPuesto()    { return puesto; }

    @Override
    public String toString() {
        return "Postulacion{usuario='" + idUsuario + "', puesto='" + puesto + "'}";
    }
}
