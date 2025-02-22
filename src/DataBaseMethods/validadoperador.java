package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*EN ESTE MÉTODO SE TIENE LA VALIDACIÓN DE OPERADORES Y PARAMÉDICOS, IGNORAR EL NOMBRE
  DE LA CLASE */
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

    public static boolean validarParamedico(String numTrabajador, String contrasenia) {
        String query = "SELECT COUNT(*) FROM paramedicos WHERE Num_trabajador_par = ? AND contrasenia = ?";
        
        try (Connection conn = ConnectionDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, numTrabajador);
            pstmt.setString(2, contrasenia);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si el resultado es mayor a 0, el paramédico existe
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false; // Si hubo error o no se encontró el paramédico
    }
}
