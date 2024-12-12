package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:sqlite:tutoria.db";

    public Connection conectar() {
        Connection conexion = null;
        try {
            // Se establece la conexión con la base de datos de SQLite
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexión exitosa a SQLite");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conectar();
    }
}
