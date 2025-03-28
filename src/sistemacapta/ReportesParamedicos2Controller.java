
package sistemacapta;
import DataBaseMethods.ConnectionDB;
import DataBaseMethods.InsertarReporte;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReportesParamedicos2Controller implements Initializable {
private int folioRecibido;
    @FXML private TextField txtAlergia, txtMedicamento, txtEnfermedades;
    
    @FXML private ComboBox<String> comboNivel, comboVia, comboReflejo, comboObservacion, comboAuscul, comboPulsos, comboPiel, comboCalidad, comboCarac, comboHemitorax, comboSitio, comboAtendido,
     comboCondicion, comboPrioridad, comboHemorragia, comboVias, comboAplicacion, comboSoluciones, comboManejo;
    private int folio; 
     @FXML private Button btnRegistrarCompleto;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     comboNivel.setItems(FXCollections.observableArrayList("1. Alerta", "2. Respuesta a estímulo verbal","3. Respuesta a estímulo doloroso" ,"4. Inconsciente"));
       comboVia.setItems(FXCollections.observableArrayList("1. Permeable", "2. Comprometida"));
       comboReflejo.setItems(FXCollections.observableArrayList("1. Ausente", "2. Presente"));
       comboObservacion.setItems(FXCollections.observableArrayList("1. Automatismo Regular", "2. Automatismo Irregular","3. Ventilación rápida" ,"4. Ventilación Superficial" ,"5. Apnea" ));
       comboAuscul.setItems(FXCollections.observableArrayList("1. Ruidos Respiratorios Normales", "2. Ruidos Respiratorios Disminuidos","3. Ruidos Respiratorios Ausentes" ,"4. Estertores Sibilancias"));
       comboPulsos.setItems(FXCollections.observableArrayList("1. Carotideo", "2. Radial","3. Paro Cardiorespiratorio"));
       comboPiel.setItems(FXCollections.observableArrayList("1. Normal", "2. Pálida","3. Cianótica"));
       comboCalidad.setItems(FXCollections.observableArrayList("1. Normal Rítmico", "2. Normal Arrítmico","3. Rápido Rítmico" ,"4. Rápido Arrítmico" ,"5. Lento Rítmico","6. Lento Arrítmico" ));
       comboCarac.setItems(FXCollections.observableArrayList("1. Normal", "2. Caliente","3. Fría" ,"4. Diaforesis"));
       comboHemitorax.setItems(FXCollections.observableArrayList("1. Derecho", "2. Izquierdo"));
       comboSitio.setItems(FXCollections.observableArrayList("1. Apical", "2. Base"));
       comboAtendido.setItems(FXCollections.observableArrayList("1. Si", "2. No"));
       comboCondicion.setItems(FXCollections.observableArrayList("1. Crítico Inestable", "2. No crítico estable"));
       comboPrioridad.setItems(FXCollections.observableArrayList("1. Rojo", "2. Amarillo","3. Verde" ,"4. Negro"));
       comboHemorragia.setItems(FXCollections.observableArrayList("1. Presión Directa", "2. Torniquete","3. Empaquetamiento" ,"4. Vendaje Compresivo"));
       comboVias.setItems(FXCollections.observableArrayList("1. LineaIV#", "2. Catéter#"));
       comboAplicacion.setItems(FXCollections.observableArrayList("1. Mano", "2. Pliegue Antecubitsl","3. Intraosea" ,"4. Otra"));
       comboSoluciones.setItems(FXCollections.observableArrayList("1. Hartmann", "2. Nacl 0.9%","3. Mixta" ,"4. Glucosa 5%"));
       comboManejo.setItems(FXCollections.observableArrayList("1. RCP Básica", "2. RCP Avanzada","3. Inmovilización de extremidades" ,"4. Empaquetamiento" ,"5. Curación","6. Vendaje" ));
            btnRegistrarCompleto.setOnAction(this::registrarEvaluacion);

    } 
    
        // Método para recibir el folio desde la primera interfaz
    public void recibirFolio(int folio) {
        this.folioRecibido = folio;
        System.out.println("Folio recibido: " + folioRecibido);
    }
      @FXML
    private void registrarEvaluacion(ActionEvent event) {
if (folioRecibido == 0) {
        System.out.println("Error: No se recibió un folio válido.");
        return;
    }

    // Verificar si el folio realmente existe antes de insertar
    InsertarReporte verificar = new InsertarReporte();
    if (!verificar.existeFolio(folioRecibido)) {
        System.out.println("Error: El folio no existe en reporteparamedicos.");
        return;
    }


        // Obtener los datos de la interfaz
        String nivelConsciencia = comboNivel.getValue();
        String viaAerea = comboVia.getValue();
        String reflejoDeglucion = comboReflejo.getValue();
        String observacion = comboObservacion.getValue();
        String auscultacion = comboAuscul.getValue();
        String presenciaPulsos = comboPulsos.getValue();
        String piel = comboPiel.getValue();
        String calidad = comboCalidad.getValue();
        String caracteristicas = comboCarac.getValue();
        String hemitorax = comboHemitorax.getValue();
        String sitio = comboSitio.getValue();
        String tipoAlergia = txtAlergia.getText();
        String atendidoPrimerCorrespondiente = comboAtendido.getValue();
        String medicamentosIngeridos = txtMedicamento.getText();
        String cirugiasPrevias = txtEnfermedades.getText();
        String condicionPaciente = comboCondicion.getValue();
        String prioridad = comboPrioridad.getValue();

            // Obtener los datos de la interfaz para el tratamiento
    String controlHemorragia = comboHemorragia.getValue();
    String viasVenosas = comboVias.getValue();
    String sitioAplicacion = comboAplicacion.getValue();
    String tipoSoluciones = comboSoluciones.getValue();
    String manejo = comboManejo.getValue();
        // Insertar datos en la base de datos
        InsertarReporte insertar = new InsertarReporte();
        insertar.insertarEvaluacion(
            folioRecibido, nivelConsciencia, viaAerea, reflejoDeglucion, observacion, auscultacion, 
            presenciaPulsos, piel, calidad, caracteristicas, hemitorax, sitio, tipoAlergia, 
            atendidoPrimerCorrespondiente, medicamentosIngeridos, cirugiasPrevias, condicionPaciente, prioridad,
                   controlHemorragia, viasVenosas, sitioAplicacion, tipoSoluciones, manejo

        );

        // Mostrar mensaje de éxito
        functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, 
            "Registro Exitoso", "Todos los datos han sido guardados correctamente.");
System.out.println("Folio recibido para evaluación: " + folioRecibido);

    }
    
     @FXML
    private void SendToIncidentes(ActionEvent event) {
        String fxmlFile = "/sistemacapta/GenerarReporteParamedico.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }
        @FXML
    private void SendToModificar(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ModificarReporteParamedico.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void Salir(ActionEvent event) {
        String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    } 
    @FXML
    private void regresar(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ReportesParamedicos.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    } 
}
