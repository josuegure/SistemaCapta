/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseMethods;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author josue
 */
public class InsertarNuevoOperador {
         public static boolean validarContraseniaAdmin(String contraseniaAdmin) {
        String query = "SELECT * FROM admin_operadores WHERE contrasenia_adminOP = ?";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, contraseniaAdmin);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // Si hay un resultado, la contraseña es válida
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para insertar un nuevo operador
    public static boolean insertarNuevoOperador(String numTrabajador, String contrasenia) {
        String query = "INSERT INTO operadores (Num_trabajador_op, contrasenia) VALUES (?, ?)";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, numTrabajador);
            pstmt.setString(2, contrasenia);

            int filasInsertadas = pstmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
