/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacapta;

import DataBaseMethods.InsertarNuevoOperador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import functions.FunctionsOfClasses;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
/**
 * FXML Controller class
 *
 * @author josue
 */
public class NuevoUsuarioOperadorController implements Initializable {
 @FXML
    private TextField txtNumTrabajador;
    @FXML
    private PasswordField txtContrasenia;
    @FXML
    private PasswordField txtContraseniaAdmin;
    @FXML
    private Button btregistrar;

    //@Override
    private void initialize() {
        btregistrar.setOnAction(event -> InsertarNuevoOperador());
    }
    /**
     * Initializes the controller class.
     */
    @FXML
    private void InsertarNuevoOperador() {
        String numTrabajador = txtNumTrabajador.getText().trim();
        String contrasenia = txtContrasenia.getText().trim();
        String contraseniaAdmin = txtContraseniaAdmin.getText().trim();

        // Validar que los campos no estén vacíos
        if (numTrabajador.isEmpty() || contrasenia.isEmpty() || contraseniaAdmin.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return;
        }

        // Validar la contraseña del administrador antes de registrar
        if (!InsertarNuevoOperador.validarContraseniaAdmin(contraseniaAdmin)) {
            mostrarError("Contraseña de administrador incorrecta.");
            return;
        }

        // Insertar operador si la contraseña del admin es correcta
        if (InsertarNuevoOperador.insertarNuevoOperador(numTrabajador, contrasenia)) {
            mostrarMensajeExito("El operador se registró exitosamente.");
            limpiarCampos();
        } else {
            mostrarError("No se pudo registrar el operador, verifica todos los datos.");
        }
    }
  
private void mostrarMensajeExito(String mensaje) {
    FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Operador agregado exitosamente", mensaje);
}

private void mostrarError(String mensaje) {
    FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Ocurrio un fallo al agregar operador", mensaje);
}
    
    private void mostrarVentanaSeleccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacapta/SeleccionUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btregistrar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleccionar Usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }}
    
    private void limpiarCampos() {
        txtNumTrabajador.clear();
        txtContrasenia.clear();
        txtContraseniaAdmin.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
   @FXML
private void SendToInicioSesionOperador(ActionEvent event) {
    String fxmlFile = "/sistemacapta/InicioOperador.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
} 
}
