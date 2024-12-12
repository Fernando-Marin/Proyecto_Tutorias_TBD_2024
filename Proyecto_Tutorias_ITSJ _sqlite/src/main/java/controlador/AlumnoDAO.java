package controlador;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import modelo.Alumno;

public class AlumnoDAO {

    public boolean agregarAlumno(Alumno alumno) {
        // Consulta SQL para insertar un nuevo alumno
        String sql = "INSERT INTO alumno (Numero_de_control, Nombre, Primer_Apellido, Segundo_Apellido, Semestre, ID_usuario, ID_carrera) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();

        try (Connection conexion = conexionBD.conectar()) {
            // Desactivar el autocommit para manejar transacciones manualmente
            conexion.setAutoCommit(false);

            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                // Configurar los valores en la consulta
                statement.setString(1, alumno.getNumeroDeControl());
                statement.setString(2, alumno.getNombre());
                statement.setString(3, alumno.getPrimerApellido());
                statement.setString(4, alumno.getSegundoApellido());
                statement.setInt(5, alumno.getSemestre());
                statement.setInt(6, alumno.getIdUsuario());
                statement.setInt(7, alumno.getIdCarrera());

                // Ejecutar la consulta
                int filasAfectadas = statement.executeUpdate();

                // Si se ejecuta correctamente, confirmar la transacción
                conexion.commit();

                // Si se insertó al menos una fila, retorna true
                return filasAfectadas > 0;
            } catch (SQLException e) {
                // Si ocurre un error, deshacer la transacción
                conexion.rollback();
                System.out.println("Error al agregar alumno, se realizó rollback: " + e.getMessage());
                return false;
            } finally {
                // Restaurar el autocommit
                conexion.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return false;
        }
    }

    public DefaultTableModel obtenerModeloTablaAlumnos() {
        // Crear las columnas del modelo de la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Número de Control");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Primer Apellido");
        modeloTabla.addColumn("Segundo Apellido");
        modeloTabla.addColumn("Semestre");
        modeloTabla.addColumn("Correo Usuario");
        modeloTabla.addColumn("Nombre Carrera");

        // Consulta SQL para obtener los alumnos con JOIN
        String sql = """
        SELECT 
            a.Numero_de_control, 
            a.Nombre, 
            a.Primer_Apellido, 
            a.Segundo_Apellido, 
            a.Semestre, 
            u.Correo, 
            c.Nombre_carrera
        FROM alumno a
        JOIN usuario u ON a.ID_Usuario = u.ID_Usuario
        JOIN carrera c ON a.ID_Carrera = c.ID_Carrera;
        """;

        // Conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();

        try (Connection conexion = conexionBD.conectar(); PreparedStatement statement = conexion.prepareStatement(sql); ResultSet resultado = statement.executeQuery()) {

            // Recorrer los resultados y agregarlos al modelo
            while (resultado.next()) {
                Object[] fila = new Object[7];
                fila[0] = resultado.getString("Numero_de_control");
                fila[1] = resultado.getString("Nombre");
                fila[2] = resultado.getString("Primer_Apellido");
                fila[3] = resultado.getString("Segundo_Apellido");
                fila[4] = resultado.getInt("Semestre");
                fila[5] = resultado.getString("Correo");
                fila[6] = resultado.getString("Nombre_carrera");

                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los alumnos: " + e.getMessage());
        }

        return modeloTabla;
    }

    public boolean eliminarAlumno(String numeroDeControl) {
        // Consulta SQL para eliminar un alumno por su número de control
        String sql = "DELETE FROM alumno WHERE Numero_de_control = ?";

        // Conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();

        try (Connection conexion = conexionBD.conectar(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Asignar el valor del número de control al parámetro de la consulta
            statement.setString(1, numeroDeControl);

            // Ejecutar la consulta
            int filasAfectadas = statement.executeUpdate();

            // Si se eliminó al menos un registro, retorna true
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar alumno: " + e.getMessage());
            return false;
        }
    }

    // ALumno DAO
    public boolean modificarAlumno(Alumno alumno) {
        // Consulta SQL para actualizar un alumno
        String sql = "UPDATE alumno SET Nombre = ?, Primer_Apellido = ?, Segundo_Apellido = ?, Semestre = ?, ID_usuario = ?, ID_carrera = ? "
                + "WHERE Numero_de_control = ?";

        // Conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();

        try (Connection conexion = conexionBD.conectar(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Configurar los valores en la consulta
            statement.setString(1, alumno.getNombre());
            statement.setString(2, alumno.getPrimerApellido());
            statement.setString(3, alumno.getSegundoApellido());
            statement.setInt(4, alumno.getSemestre());
            statement.setInt(5, alumno.getIdUsuario());
            statement.setInt(6, alumno.getIdCarrera());
            statement.setString(7, alumno.getNumeroDeControl());

            // Ejecutar la consulta
            int filasAfectadas = statement.executeUpdate();

            // Si se modificó al menos una fila, retorna true
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar alumno: " + e.getMessage());
            return false;
        }
    }

    public DefaultTableModel buscarAlumnos(String filtro) {
        // Crear el modelo para la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Número de Control");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Primer Apellido");
        modeloTabla.addColumn("Segundo Apellido");
        modeloTabla.addColumn("Semestre");
        modeloTabla.addColumn("ID Usuario");
        modeloTabla.addColumn("ID Carrera");

        // Consulta SQL con filtro LIKE
        String sql = "SELECT * FROM alumno WHERE "
                + "Numero_de_control LIKE ? OR "
                + "Nombre LIKE ? OR "
                + "Primer_Apellido LIKE ? OR "
                + "Segundo_Apellido LIKE ? OR "
                + "CAST(Semestre AS CHAR) LIKE ? OR "
                + "CAST(ID_usuario AS CHAR) LIKE ? OR "
                + "CAST(ID_carrera AS CHAR) LIKE ?";

        // Conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();

        try (Connection conexion = conexionBD.conectar(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Configurar el parámetro del filtro
            String filtroLike = "%" + filtro + "%";
            for (int i = 1; i <= 7; i++) {
                statement.setString(i, filtroLike);
            }

            // Ejecutar la consulta
            try (ResultSet resultado = statement.executeQuery()) {
                // Recorrer los resultados y agregarlos al modelo
                while (resultado.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = resultado.getString("Numero_de_control");
                    fila[1] = resultado.getString("Nombre");
                    fila[2] = resultado.getString("Primer_Apellido");
                    fila[3] = resultado.getString("Segundo_Apellido");
                    fila[4] = resultado.getInt("Semestre");
                    fila[5] = resultado.getInt("ID_usuario");
                    fila[6] = resultado.getInt("ID_carrera");

                    modeloTabla.addRow(fila);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar alumnos: " + e.getMessage());
        }

        return modeloTabla;
    }

    public DefaultTableModel obtenerModeloTablaAuditoria() {
        // Crear las columnas del modelo de la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Número de Control");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Primer Apellido");
        modeloTabla.addColumn("Segundo Apellido");
        modeloTabla.addColumn("Semestre");
        modeloTabla.addColumn("ID Usuario");
        modeloTabla.addColumn("ID Carrera");
        modeloTabla.addColumn("Fecha y Hora de Eliminación");

        // Consulta SQL para obtener los registros de auditoría
        String sql = "SELECT * FROM alumno_auditoria";

        // Conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();

        try (Connection conexion = conexionBD.conectar(); PreparedStatement statement = conexion.prepareStatement(sql); ResultSet resultado = statement.executeQuery()) {

            // Recorrer los resultados y agregarlos al modelo
            while (resultado.next()) {
                Object[] fila = new Object[9];
                fila[0] = resultado.getInt("ID");
                fila[1] = resultado.getString("Numero_de_control");
                fila[2] = resultado.getString("Nombre");
                fila[3] = resultado.getString("Primer_Apellido");
                fila[4] = resultado.getString("Segundo_Apellido");
                fila[5] = resultado.getInt("Semestre");
                fila[6] = resultado.getInt("ID_usuario");
                fila[7] = resultado.getInt("ID_carrera");
                fila[8] = resultado.getTimestamp("Fecha_Hora_Eliminacion"); // Timestamp para fecha y hora

                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la tabla de auditoría: " + e.getMessage());
        }

        return modeloTabla;
    }

}
