package sistemacapta;

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
public class SeleccionUsuarioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
@FXML
private void SendToOperador(ActionEvent event) {
    String fxmlFile = "/sistemacapta/InicioOperador.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
@FXML
private void SendToParamedico(ActionEvent event) {
    String fxmlFile = "/sistemacapta/InicioParamedico.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
@FXML
private void SendToNuevoOperador(ActionEvent event) {
    String fxmlFile = "/sistemacapta/NuevoUsuarioOperador.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
@FXML
private void SendToNuevoParamedico(ActionEvent event) {
    String fxmlFile = "/sistemacapta/NuevoUsuarioParamedico.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
}