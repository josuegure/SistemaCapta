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
public class ActualizarReporte {
    public static boolean actualizarReporte(Map<String, String> datos) {
        String sql = "UPDATE reporteoperadores SET calle=?, colonia=?, referencia=?, especificaciones=?, resultados=?, +"
                     +  "genero=?, clasificacion=?, dia_semana=?, contacto=?, tipo_atendido=?, grupo_atencion=?, fecha=? +"
                      + "WHERE folio=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacapta", "root", "josue");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, datos.get("calle"));
            statement.setString(2, datos.get("colonia"));
            statement.setString(3, datos.get("referencia"));
            statement.setString(4, datos.get("especificaciones"));
            statement.setString(5, datos.get("resultados"));
            statement.setString(6, datos.get("genero"));
            statement.setString(7, datos.get("clasificacion"));
            statement.setString(8, datos.get("dia_semana"));
            statement.setString(9, datos.get("contacto"));
            statement.setString(10, datos.get("tipo_atendido"));
            statement.setString(11, datos.get("grupo_atencion"));
            statement.setDate(12, Date.valueOf(LocalDate.parse(datos.get("fecha"))));
            statement.setInt(13, Integer.parseInt(datos.get("folio")));
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

