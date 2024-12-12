// crear una clase llamada ReporteAlumno

package reportes;

import controlador.AlumnoDAO;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReporteAlumno {

    public static boolean generarReporteCSV(String rutaArchivo) {
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        DefaultTableModel modeloTabla = alumnoDAO.obtenerModeloTablaAlumnos();

        // Creamos el archivo
        File archivo = new File(rutaArchivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            // Escribimos los encabezados de las columnas
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                writer.write(modeloTabla.getColumnName(i));
                if (i < modeloTabla.getColumnCount() - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();

            // Escribimos las filas de los alumnos
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    // Obtenemos el valor y lo verificamos si es null
                    Object valor = modeloTabla.getValueAt(i, j);
                    // Si el valor es null, reemplazamos con una cadena vacía
                    if (valor == null) {
                        valor = "";
                    }
                    writer.write(valor.toString()); // Convertimos el valor a cadena
                    if (j < modeloTabla.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            return true; // Si todo salió bien, devolvemos true
        } catch (IOException e) {
            System.out.println("Error al generar el reporte CSV: " + e.getMessage());
            return false; // Si hubo un error, devolvemos false
        }
    }

}