
package DataBaseMethods;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author josue 
 * EN ESTE MÉTODO SE TIENE LA INSERCIÓN DE OPERADORES Y PARAMÉDICOS, IGNORAR EL NOMBRE
 * DE LA CLASE
 */
public class InsertarNuevoOperador {
         public static boolean validarContraseniaAdmin(String contraseniaAdmin) {
        String query = "SELECT * FROM operadores WHERE contrasenia = ? AND tipo_operador = 'administrador'";

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
    public static boolean insertarNuevoOperador(String numTrabajador, String contrasenia, String contraseniaAdmin) {
        if (!validarContraseniaAdmin(contraseniaAdmin)) {
            return false; // Si la contraseña de admin no es válida, no se inserta el operador
        }
        
        String query = "INSERT INTO operadores (Num_trabajador_op, contrasenia, tipo_operador) VALUES (?, ?, 'empleado')";

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
    //INSERTAR NUEVO PARAMEDICO
         public static boolean validarContraseniaAdminPAR(String contraseniaAdminPAR) {
        String query = "SELECT * FROM paramedicos WHERE contrasenia = ? AND tipo_paramedico = 'administrador'";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, contraseniaAdminPAR);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // Si hay un resultado, la contraseña es válida
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para insertar un nuevo PARAMEDICO
    public static boolean insertarNuevoParamedico(String numTrabajador, String contrasenia, String contraseniaAdminPAR) {
        String query = "INSERT INTO paramedicos (Num_trabajador_PAR, contrasenia, tipo_paramedico) VALUES (?, ?, 'empleado')";

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

