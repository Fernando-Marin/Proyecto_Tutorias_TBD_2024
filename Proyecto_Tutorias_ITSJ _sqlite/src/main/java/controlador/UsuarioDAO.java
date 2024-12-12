package controlador;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para verificar el login
    public boolean login(String correo, String contraseña) {
        boolean usuarioValido = false; // Inicializamos como falso

        // Consulta SQL para verificar usuario
        String sql = "SELECT * FROM usuario WHERE Correo = ? AND Contraseña = ?";
        
        // Conexión y manejo de la base de datos
        try (Connection conexion = new ConexionBD().conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
             
            // Asignar parámetros a la consulta
            ps.setString(1, correo);
            ps.setString(2, contraseña);

            // Ejecutar la consulta
            try (ResultSet rs = ps.executeQuery()) {
                // Si hay resultados, significa que el usuario es válido
                if (rs.next()) {
                    usuarioValido = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar login: " + e.getMessage());
        }
        
        return usuarioValido; // Retornamos el resultado
    }

    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Prueba del método de login
        String correoPrueba = "admin@example.com";
        String contraseñaPrueba = "admin123";

        if (usuarioDAO.login(correoPrueba, contraseñaPrueba)) {
            System.out.println("Login exitoso: Usuario válido.");
        } else {
            System.out.println("Login fallido: Usuario o contraseña incorrectos.");
        }
    }
}
