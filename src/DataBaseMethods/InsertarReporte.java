package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import javafx.scene.control.Alert;

public class InsertarReporte {

    public void insertarReporte(String folio, String tipoAtendido, String contacto, String genero, String calle, String colonia, String referencia, String clasificacion, String grupoAtencion, String diaSemana, Date fecha, String especificaciones, String resultados) {

    String sql = "INSERT INTO reporteoperadores (folio, tipo_atendido, contacto, genero, calle, colonia, referencia, clasificacion, grupo_atencion, dia_semana, fecha, especificaciones, resultados) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, folio); // Folio
            stmt.setString(2, tipoAtendido); // Tipo atendido
            stmt.setString(3, contacto); // Contacto
            stmt.setString(4, genero); // Género
            stmt.setString(5, calle); // Calle
            stmt.setString(6, colonia); // Colonia
            stmt.setString(7, referencia); // Referencia
            stmt.setString(8, clasificacion); // Clasificación
            stmt.setString(9, grupoAtencion); // Grupo de atención
            stmt.setString(10, diaSemana); // Día de la semana
            stmt.setDate(11, fecha); // Fecha (corregido)
            stmt.setString(12, especificaciones); // Especificaciones
            stmt.setString(13, resultados); // Resultados

            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Registro exitoso", "El reporte ha sido registrado correctamente.");
            } else {
                functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", "No se pudo registrar el reporte.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
            functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error de base de datos", "Hubo un problema al registrar el reporte" /*+ e.getMessage()*/);
        }   
    }   
}
