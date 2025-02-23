/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseMethods;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author josue
 */
public class BuscarReporte {
    
 public static Map<String, String> buscarPorFolio(int folio) {
        Map<String, String> datosReporte = new HashMap<>();
        String sql = "SELECT * FROM reporteoperadores WHERE folio = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacapta", "root", "josue");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, folio);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                datosReporte.put("calle", resultSet.getString("calle"));
                datosReporte.put("colonia", resultSet.getString("colonia"));
                datosReporte.put("referencia", resultSet.getString("referencia"));
                datosReporte.put("especificaciones", resultSet.getString("especificaciones"));
                datosReporte.put("resultados", resultSet.getString("resultados"));
                datosReporte.put("contacto", resultSet.getString("contacto"));
                datosReporte.put("tipo_atendido", resultSet.getString("tipo_atendido"));
                datosReporte.put("genero", resultSet.getString("genero"));
                datosReporte.put("clasificacion", resultSet.getString("clasificacion"));
                datosReporte.put("dia_semana", resultSet.getString("dia_semana"));
                datosReporte.put("grupo_atencion", resultSet.getString("grupo_atencion"));
                datosReporte.put("fecha", resultSet.getDate("fecha").toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datosReporte;
    }
}
