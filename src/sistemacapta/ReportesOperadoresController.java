package sistemacapta;

import java.net.URL;
import java.util.ResourceBundle;
import DataBaseMethods.InsertarReporte;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

/**
 * FXML Controller class
 *
 * @author josue
 */

public class ReportesOperadoresController implements Initializable {

    @FXML private TextField txtFolio, txtCalle, txtColonia, txtReferencia;
    @FXML private TextArea txtEspecificaciones, txtResultados;
    @FXML private ComboBox<String> comboGenero, comboClasificacion, comboGrupoAtencion, comboDiaSemana, comboContacto, comboTipoAtendido;
    @FXML private DatePicker datePickerFecha;
    @FXML private Button btnRegistrar;

    @FXML
    public void initialize() {
        
    }
@FXML
private void insertarReporte() {
    // Obtén los valores de los campos
    String folio = txtFolio.getText();
    String tipoAtendido = comboTipoAtendido.getValue();
    String contacto = comboContacto.getValue();
    String genero = comboGenero.getValue();
    String calle = txtCalle.getText();
    String colonia = txtColonia.getText();
    String referencia = txtReferencia.getText();
    String clasificacion = comboClasificacion.getValue();
    String grupoAtencion = comboGrupoAtencion.getValue();
    String diaSemana = comboDiaSemana.getValue(); // Obtener el día de la semana
    String especificaciones = txtEspecificaciones.getText();
    String resultados = txtResultados.getText();
    Date fecha = Date.valueOf(datePickerFecha.getValue());

    // Llama al método que inserta el reporte (asegúrate de que esté bien implementado)
    InsertarReporte insertarReporte = new InsertarReporte();
    insertarReporte.insertarReporte(folio, tipoAtendido, contacto, genero, calle, colonia, referencia, clasificacion, grupoAtencion, diaSemana, fecha, especificaciones, resultados);
}


    private void limpiarCampos() {
        txtFolio.clear();
        txtCalle.clear();
        txtColonia.clear();
        txtReferencia.clear();
        txtEspecificaciones.clear();
        txtResultados.clear();
        comboGenero.getSelectionModel().clearSelection();
        comboClasificacion.getSelectionModel().clearSelection();
        comboGrupoAtencion.getSelectionModel().clearSelection();
        comboDiaSemana.getSelectionModel().clearSelection();
        comboTipoAtendido.getSelectionModel().clearSelection();
        comboContacto.getSelectionModel().clearSelection();
        datePickerFecha.setValue(null);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // Opciones para los ComboBox
        comboGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));
        comboClasificacion.setItems(FXCollections.observableArrayList("Rescate acuático", "Asistencias médicas", "Muerte por sumersión"));
        comboGrupoAtencion.setItems(FXCollections.observableArrayList("Capta", "Policía vial"));
        comboDiaSemana.setItems(FXCollections.observableArrayList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
        comboContacto.setItems(FXCollections.observableArrayList("Directo", "911", "Otro"));
        comboTipoAtendido.setItems(FXCollections.observableArrayList("Local", "Turista", "Sin especificar"));
        
        // Acción del botón Registrar
       btnRegistrar.setOnAction(event -> insertarReporte());
    }    
       @FXML
    private void SendToIncidentes(ActionEvent event) {
    String fxmlFile = "/sistemacapta/InicioOperador.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
       @FXML
    private void Salir(ActionEvent event) {
    String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml"; // Ajusta la ruta según tu estructura de paquetes
    functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
}
}
