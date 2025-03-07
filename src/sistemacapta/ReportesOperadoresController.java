package sistemacapta;

import java.net.URL;
import java.util.ResourceBundle;
import DataBaseMethods.InsertarReporte;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*; 
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;

public class ReportesOperadoresController implements Initializable {

    @FXML private TextField txtFolio, txtEmpleado,txtCalle, txtColonia, txtReferencia, txtOtroContacto, txtOtroTipoAtendido;
    @FXML private TextArea txtEspecificaciones, txtResultados;
    @FXML private ComboBox<String> comboGenero, comboClasificacion, comboDiaSemana, comboContacto, comboTipoAtendido;
    @FXML private DatePicker datePickerFecha;
    @FXML private Button btnRegistrar;
    @FXML private ComboBox<String> comboGrupoAtencion; // ComboBox con CheckBoxes
    private List<CheckMenuItem> checkItems = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        comboGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));
        comboClasificacion.setItems(FXCollections.observableArrayList("Rescate acuático", "Accidentes Acuáticos", "Apoyo a prestadores de servicios turista", 
        "Muerte por sumersión", "Incendio de casa habitación y/o vehículo", "Desaparecido en el mar", "Muerte Natural/Accidental", "Suicidio", "Homicidio"
        ,"Menores localizados", "Adultos localizados", "Quejas/Fraudes/Extorsión", "Robo con violencia", "Fraude cibernético", "Desove de tortugas"
        ,"Asistencias mecánicas SSP/Asist Vial", "Asist locales CAPTA", "Angeles verdes/Asist mecánicas", "Falsas alarmas/no efectadas", "Extranjeros", "Asistencias turísticas"));
        comboDiaSemana.setItems(FXCollections.observableArrayList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
        comboContacto.setItems(FXCollections.observableArrayList("Directo", "911","Whatsapp", "Otro"));
        comboTipoAtendido.setItems(FXCollections.observableArrayList("Local", "Turista", "Sin especificar"));

        // Configurar el ComboBox con CheckBoxes
        configurarComboBoxGrupoAtencion();

        // Configurar la habilitación de los TextFields
        configurarHabilitacionCampos();

        // Acción del botón Registrar
        btnRegistrar.setOnAction(event -> insertarReporte());
    }

    private void configurarHabilitacionCampos() {
        // Deshabilitar los TextFields inicialmente
        txtOtroContacto.setDisable(true);
        txtOtroTipoAtendido.setDisable(true);

        // Detectar cambios en los ComboBox y habilitar los TextFields según corresponda
        comboContacto.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Otro".equals(newValue)) {
                txtOtroContacto.setDisable(false);
            } else {
                txtOtroContacto.setDisable(true);
                txtOtroContacto.clear(); // Limpiar el campo si no es necesario
            }
        });

        comboTipoAtendido.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Sin especificar".equals(newValue)) {
                txtOtroTipoAtendido.setDisable(false);
            } else {
                txtOtroTipoAtendido.setDisable(true);
                txtOtroTipoAtendido.clear(); // Limpiar el campo si no es necesario
            }
        });
    }

    // Aquí configuramos el ComboBox con CheckBoxes
    private void configurarComboBoxGrupoAtencion() {
        ObservableList<String> opciones = FXCollections.observableArrayList("CAPTA", "Policía vial", "Cruz roja", "Bomberos");

        ContextMenu contextMenu = new ContextMenu();
        for (String opcion : opciones) {
            CheckMenuItem checkMenuItem = new CheckMenuItem(opcion);
            checkMenuItem.setOnAction(e -> actualizarSeleccionComboBox());
            checkItems.add(checkMenuItem);
            contextMenu.getItems().add(checkMenuItem);
        }

        // Al hacer clic en el ComboBox, mostrar el menú con CheckBoxes
        comboGrupoAtencion.setOnMouseClicked(e -> contextMenu.show(comboGrupoAtencion, e.getScreenX(), e.getScreenY()));
    }

    private void actualizarSeleccionComboBox() {
        List<String> seleccionados = new ArrayList<>();
        for (CheckMenuItem item : checkItems) {
            if (item.isSelected()) {
                seleccionados.add(item.getText());
            }
        }
        comboGrupoAtencion.setValue(String.join(", ", seleccionados)); // Mostrar selección en el ComboBox
    }

    @FXML
    private void insertarReporte() {
        // Obtén los valores de los campos
        String folio = txtFolio.getText();
        String tipoAtendido = comboTipoAtendido.getValue();
        String contacto = comboContacto.getValue();
        
        // Si se selecciona "Otro", tomar el valor del TextField correspondiente
        if ("Otro".equals(contacto)) {
            contacto = txtOtroContacto.getText();
        }

        // Si se selecciona "Sin especificar", tomar el valor del TextField correspondiente
        if ("Sin especificar".equals(tipoAtendido)) {
            tipoAtendido = txtOtroTipoAtendido.getText();
        }
        String empleado = txtEmpleado.getText();
        String genero = comboGenero.getValue();
        String calle = txtCalle.getText();
        String colonia = txtColonia.getText();
        String referencia = txtReferencia.getText();
        String clasificacion = comboClasificacion.getValue();
        String grupoAtencion = comboGrupoAtencion.getValue(); // Se guardan los seleccionados
        String diaSemana = comboDiaSemana.getValue(); 
        String especificaciones = txtEspecificaciones.getText();
        String resultados = txtResultados.getText();
        Date fecha = Date.valueOf(datePickerFecha.getValue());

        // Insertar en la base de datos
        InsertarReporte insertarReporte = new InsertarReporte();
        insertarReporte.insertarReporte(folio, empleado, tipoAtendido, contacto, genero, calle, colonia, referencia, clasificacion, grupoAtencion, diaSemana, fecha, especificaciones, resultados);

        // Limpiar campos después de la inserción
       // limpiarCampos();
    }

    @FXML
    private void SendToIncidentes(ActionEvent event) {
        String fxmlFile = "/sistemacapta/GenerarReporteOperador.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }
        @FXML
    private void SendToModificar(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ModificarReporteOperador.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void Salir(ActionEvent event) {
        String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }
}
