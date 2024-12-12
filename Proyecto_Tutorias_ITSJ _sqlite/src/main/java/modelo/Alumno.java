package modelo;

public class Alumno {

    private String numeroDeControl; // Mapeo para la columna Numero_de_control
    private String nombre;          // Mapeo para la columna Nombre
    private String primerApellido;  // Mapeo para la columna Primer_Apellido
    private String segundoApellido; // Mapeo para la columna Segundo_Apellido
    private int semestre;           // Mapeo para la columna Semestre
    private int idUsuario;          // Mapeo para la columna ID_usuario
    private int idCarrera;          // Mapeo para la columna ID_carrera

    // Constructor vacío (necesario para algunos frameworks)
    public Alumno() {
    }

    // Constructor con parámetros
    public Alumno(String numeroDeControl, String nombre, String primerApellido, String segundoApellido, int semestre, int idUsuario, int idCarrera) {
        this.numeroDeControl = numeroDeControl;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.semestre = semestre;
        this.idUsuario = idUsuario;
        this.idCarrera = idCarrera;
    }

    // Getters y Setters
    public String getNumeroDeControl() {
        return numeroDeControl;
    }

    public void setNumeroDeControl(String numeroDeControl) {
        this.numeroDeControl = numeroDeControl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    // Método toString para representación textual
    @Override
    public String toString() {
        return "Alumno{" +
               "numeroDeControl='" + numeroDeControl + '\'' +
               ", nombre='" + nombre + '\'' +
               ", primerApellido='" + primerApellido + '\'' +
               ", segundoApellido='" + segundoApellido + '\'' +
               ", semestre=" + semestre +
               ", idUsuario=" + idUsuario +
               ", idCarrera=" + idCarrera +
               '}';
    }
}