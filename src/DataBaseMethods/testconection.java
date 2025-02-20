/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseMethods;
import java.sql.Connection;
/**
 *
 * @author josue
 */
public class testconection {
      public static void main(String[] args) {
        Connection conn = ConnectionDB.connect();
        if (conn != null) {
            System.out.println("🎉 La conexión fue exitosa.");
        } else {
            System.out.println("⚠️ No se pudo conectar.");
        }
    }  
}


