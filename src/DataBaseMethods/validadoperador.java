/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseMethods;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author josue
 */
public class validadoperador {
    public static boolean validarOperador(String numTrabajador, String contrasenia) {
        String query = "SELECT COUNT(*) FROM operadores WHERE Num_trabajador_op = ? AND contrasenia = ?";
        
        try (Connection conn = ConnectionDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, numTrabajador);
            pstmt.setString(2, contrasenia);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si el resultado es mayor a 0, el operador existe
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false; // Si hubo error o no se encontró el operador
    }
}
