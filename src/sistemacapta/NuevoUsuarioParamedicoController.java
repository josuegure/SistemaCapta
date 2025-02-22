/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacapta;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
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
public class NuevoUsuarioParamedicoController implements Initializable {
    @FXML
    private TextField txtNumTrabajadorPar;
    @FXML
    private PasswordField txtContraseniaPar;
    @FXML
    private PasswordField txtContraseniaAdminPar;
    @FXML
    private Button btregistrarPar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        private void initialize() {
        btregistrarPar.setOnAction(event -> InsertarNuevoParamedico());
    }
         @FXML
    private void InsertarNuevoParamedico() {
        String numTrabajador = txtNumTrabajadorPar.getText().trim();
        String contrasenia = txtContraseniaPar.getText().trim();
        String contraseniaAdmin = txtContraseniaAdminPar.getText().trim();

        // Validar que los campos no estén vacíos
        if (numTrabajador.isEmpty() || contrasenia.isEmpty() || contraseniaAdmin.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return;
        }

        // Validar la contraseña del administrador antes de registrar
        if (!InsertarNuevoOperador.validarContraseniaAdminPAR(contraseniaAdmin)) {
            mostrarError("Contraseña de administrador incorrecta.");
            return;
        }

        // Insertar operador si la contraseña del admin es correcta
        if (InsertarNuevoOperador.insertarNuevoParamedico(numTrabajador, contrasenia, contraseniaAdmin)) {
            mostrarMensajeExito("El paramédico se registró exitosamente.");
            limpiarCampos();
        } else {
            mostrarError("No se pudo registrar al paramédico, verifica todos los datos.");
        }
    }
    private void mostrarMensajeExito(String mensaje) {
    FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Paramédico agregado exitosamente", mensaje);
}

private void mostrarError(String mensaje) {
    FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Ocurrio un fallo al agregar paramédico", mensaje);
}
    
    private void mostrarVentanaSeleccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacapta/SeleccionUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btregistrarPar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleccionar Usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }}
    
    private void limpiarCampos() {
        txtNumTrabajadorPar.clear();
        txtContraseniaPar.clear();
        txtContraseniaAdminPar.clear();
    }
       @FXML
private void SendToInicioSesionParamedico(ActionEvent event) {
    String fxmlFile = "/sistemacapta/InicioParamedico.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
}
