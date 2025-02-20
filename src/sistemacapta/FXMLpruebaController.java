/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacapta;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import functions.FunctionsOfClasses;
import functions.*;

/**
 * FXML Controller class
 *
 * @author josue
 */
public class FXMLpruebaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
private void EnviarASeleccion(ActionEvent event) {
    String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
}