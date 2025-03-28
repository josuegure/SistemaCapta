/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacapta;
import DataBaseMethods.InsertarReporte;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 *  @author josue
 */
public class ReportesParamedicosController implements Initializable {
    @FXML private TextField txtFolio1, textNumparamedico, txtEstado,txtDelegacion, txtAsignacion, txtCalle, txtColonia, txtMunicipio, txtHoraLlamada, txtHoraDespacho, txtHoraArribo, txtHoraDisponible;
    @FXML private TextField txtMoto, txtConductor, txtPrestador;
    @FXML private TextField txtNombrePaciente, txtEdadPaciente, txtDomicilioPaciente, txtColoniaPaciente, txtMunicipioPaciente, txtTelefonoPaciente, txtOcupacionPaciente, txtDerechoHabiente;
    @FXML private ComboBox<String> comboMotivo, comboDiaSemana, comboLugarOcurrencia, comboGenero, comboAgenteCausal, comboOrigenProbable;
    @FXML private DatePicker datePickerFecha;
    @FXML private TextField txtEspecifique, txtEspecifiqueCausa, txtPrimeraVez, txtSubsecuente;
    @FXML private Button btnSiguiente, btnSiguienteInterfaz;

    @Override
    public void initialize (URL url, ResourceBundle rb) {
    comboMotivo.setItems(FXCollections.observableArrayList("Golpe", "Secuestro", "Reporte bimestral", "NYE"));
    comboDiaSemana.setItems(FXCollections.observableArrayList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
    comboLugarOcurrencia.setItems(FXCollections.observableArrayList("Casa", "Hogar", "Escuela", "Via pública", "Hospital", "Playa"));
    comboGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));
   comboAgenteCausal.setItems(FXCollections.observableArrayList("1. Arma", "2. Juguete","3. Automotor" ,"4. Bicicleta/Scooter" ,"5. Producto biologico/Quimico"
       ,"6. Maquinaria","7. Herramienta" ,"8. Fuego" ,"9. Sustancia/Objeto caliente","10. Sustancia tóxica","11. Electricidad","12. Explosión"
       ,"13. Ser humano","14. Animal","15. Otro"));
    comboOrigenProbable.setItems(FXCollections.observableArrayList("1. Neurológica", "2. Cardiovascular","3. Respiratorio" ,"4. Metabólico" ,"5. Digestiva" ,"6. Urogenital" 
       ,"7. Gineco-Obstétrica" ,"8. Cognitivo/emocional" ,"9. Músculo Esquelético" ,"10. Infecciosa" ,"11. Oncológico" ,"12. Otro"));
       // btnRegistrar.setOnAction(event -> insertarReporte());
        btnSiguienteInterfaz.setOnAction(this::abrirSegundaInterfaz);

}
    //generar reporte paramedico  
@FXML
private void insertarReporte() {
    int folio = Integer.parseInt(txtFolio1.getText());
    String numParamedico = textNumparamedico.getText();
    String estado = txtEstado.getText();
    String delegacion = txtDelegacion.getText();
    String asignacion = txtAsignacion.getText();
    String calle = txtCalle.getText();
    String colonia = txtColonia.getText();
    String municipio = txtMunicipio.getText();
    String horaLlamada = txtHoraLlamada.getText();
    String motivo = comboMotivo.getValue();
    Date fecha = Date.valueOf(datePickerFecha.getValue());
    String diaSemana = comboDiaSemana.getValue();
    String lugarOcurrencia = comboLugarOcurrencia.getValue();
    String horaDespacho = txtHoraDespacho.getText();
    String horaArribo = txtHoraArribo.getText();
    String horaDisponible = txtHoraDisponible.getText();  
    // Datos de vehículo
    String numMoto = txtMoto.getText();
    String operador = txtConductor.getText();
    String prestadorServicio = txtPrestador.getText(); 
    // Datos de paciente
    String nombrePaciente = txtNombrePaciente.getText();
    String genero =  comboGenero.getValue();
    String domicilio = txtDomicilioPaciente.getText();
    String coloniaPaciente = txtColoniaPaciente.getText();
    String municipioPaciente = txtMunicipioPaciente.getText();
    String telefono = txtTelefonoPaciente.getText();
    String ocupacion = txtOcupacionPaciente.getText();
    String derechoHabiente = txtDerechoHabiente.getText();
    String edad = txtEdadPaciente.getText();
    //Datos causa traumatia
    String agenteCausal = comboAgenteCausal.getValue();
    String especifique = txtEspecifique.getText();
    String origenProbable = comboOrigenProbable.getValue();
    String especifiqueOrigen = txtEspecifiqueCausa.getText();
    String primeraVez = txtPrimeraVez.getText(); // Si es un ComboBox
    String subsecuente = txtSubsecuente.getText();
    // Insertar datos en la base de datos
    InsertarReporte insertarReporte = new InsertarReporte();
    insertarReporte.insertarReporteCompleto(
        folio, numParamedico, estado, delegacion, asignacion, motivo, fecha, diaSemana, 
        calle, colonia, municipio, lugarOcurrencia, horaLlamada, horaDespacho, horaArribo, horaDisponible, 
        numMoto, operador, prestadorServicio, 
        nombrePaciente, genero, domicilio, coloniaPaciente, municipioPaciente, telefono, ocupacion, derechoHabiente, edad,
        agenteCausal, especifique, origenProbable, especifiqueOrigen, primeraVez, subsecuente        
    );
}
@FXML
private void abrirSegundaInterfaz(ActionEvent event) {
    // Insertar los datos en reporteparamedicos antes de abrir la nueva ventana
    insertarReporte(); 

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportesParamedicos2.fxml"));
        Parent root = loader.load();

        // Pasar el folio a la segunda interfaz
        ReportesParamedicos2Controller controlador2 = loader.getController();
        int folio = Integer.parseInt(txtFolio1.getText());
        controlador2.recibirFolio(folio);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Cerrar la ventana actual
        ((Stage) btnSiguienteInterfaz.getScene().getWindow()).close();
    } catch (Exception e) {
        System.out.println("Error al abrir la segunda interfaz: " + e.getMessage());
    }
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
    private void Formato2(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ReportesParamedicos2.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }
}
