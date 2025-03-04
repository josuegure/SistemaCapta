/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacapta;
import DataBaseMethods.ConnectionDB;
import DataBaseMethods.validadoperador;
import functions.FunctionsOfClasses;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author josue
 */
public class InicioOperadorController implements Initializable {
    @FXML
    private TextField txtNumTrabajador;
    @FXML
    private PasswordField txtContrasenia;  
    @FXML
    private Button btnIniciarSesion;
        @FXML
    private Button btnIniciarSesion2;
    private validadoperador loginService;

    public InicioOperadorController() {
        this.loginService = new validadoperador();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
    @FXML
    private void iniciarSesion() {
        String numTrabajador = txtNumTrabajador.getText();
        String contrasenia = txtContrasenia.getText();

        if (validarOperador(numTrabajador, contrasenia)) {
            mostrarMensajeBienvenida(numTrabajador);  // Muestra el mensaje antes de redirigir
            mostrarVentanaSeleccion();
        } else {
            mostrarError("Número de trabajador o contraseña incorrectos.");
        }
    }
    private boolean validarOperador(String numTrabajador, String contrasenia) {
        String query = "SELECT * FROM operadores WHERE Num_trabajador_op = ? AND contrasenia = ?";
        try (Connection conn = ConnectionDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, numTrabajador);
            pstmt.setString(2, contrasenia);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Si hay resultado, el usuario existe
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void mostrarMensajeBienvenida(String numTrabajador) {
        FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Inicio exitoso", "Bienvenido operador #" + numTrabajador + "!");
    }

    private void mostrarError(String mensaje) {
        FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error de inicio de sesión", mensaje);
    }
        private void mostrarVentanaSeleccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacapta/ReportesOperadores.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnIniciarSesion2.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleccionar Usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        @FXML
private void handleKeyPress(KeyEvent event) {
    if (event.getCode().toString().equals("ENTER")) {
        iniciarSesion();
    }
}
}
    /*private void mostrarMensajeBienvenida(String numTrabajador) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inicio exitoso");
        alert.setHeaderText(null);
        alert.setContentText("Bienvenido " + numTrabajador + "!");
        alert.showAndWait();
    }
    private void mostrarVentanaSeleccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacapta/SeleccionUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleccionar Usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de inicio de sesión");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
*
}*/
