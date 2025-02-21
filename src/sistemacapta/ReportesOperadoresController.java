package sistemacapta;

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
public class ReportesOperadoresController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
       @FXML
private void SendToInicioSesionOperador(ActionEvent event) {
    String fxmlFile = "/sistemacapta/InicioOperador.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
       @FXML
private void Salir(ActionEvent event) {
    String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
}
