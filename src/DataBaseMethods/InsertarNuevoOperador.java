
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
    public static boolean insertarNuevoOperador(String numTrabajador, String contrasenia, String contraseniaAdmin) {
        String query = "INSERT INTO operadores (Num_trabajador_op, contrasenia, contrasenia_admin_OP) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, numTrabajador);
            pstmt.setString(2, contrasenia);
            pstmt.setString(3, contraseniaAdmin);
            int filasInsertadas = pstmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //INSERTAR NUEVO PARAMEDICO
         public static boolean validarContraseniaAdminPAR(String contraseniaAdminPAR) {
        String query = "SELECT * FROM admin_paramedicos WHERE contrasenia_adminPAR = ?";

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
        String query = "INSERT INTO paramedicos (Num_trabajador_PAR, contrasenia, contrasenia_admin_PAR) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, numTrabajador);
            pstmt.setString(2, contrasenia);
            pstmt.setString(3, contraseniaAdminPAR);
            int filasInsertadas = pstmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    
    }
    
    }

