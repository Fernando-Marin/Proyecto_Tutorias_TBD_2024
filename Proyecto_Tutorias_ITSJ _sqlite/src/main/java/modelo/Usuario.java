package modelo;

public class Usuario {
    // Atributos
    private int idUsuario;
    private String correo;
    private String contraseña;
    private String rol;

    // Constructores
    public Usuario() {
    }

    public Usuario(int idUsuario, String correo, String contraseña, String rol) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Método toString
    @Override
    public String toString() {
        return "Usuario{" +
               "idUsuario=" + idUsuario +
               ", correo='" + correo + '\'' +
               ", contraseña='" + contraseña + '\'' +
               ", rol='" + rol + '\'' +
               '}';
    }
}

