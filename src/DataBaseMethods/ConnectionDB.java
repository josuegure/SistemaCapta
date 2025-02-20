/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseMethods;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author josue
 */
public class ConnectionDB {
      private static final String URL = "jdbc:mysql://localhost:3306/sistemacapta"; // URL de la BD
    private static final String USER = "root"; // Usuario de MySQL
    private static final String PASSWORD = "josue"; // Contraseña de MySQL (déjala vacía si no tiene)

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
            return conn;
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }  
}


/*public class DatabaseConnection {


}*/
