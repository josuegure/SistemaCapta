/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacapta;

import functions.FunctionsOfClasses;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author josue
 */
public class GenerarReporteParamedicoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
      @FXML
    private void SendToFormato(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ReportesParamedicos.fxml";
        FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void SendToModificar(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ModificarReporteParamedico.fxml";
        FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void Salir(ActionEvent event) {
        String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml";
        FunctionsOfClasses.switchToScene(event, fxmlFile);
    }  
}
