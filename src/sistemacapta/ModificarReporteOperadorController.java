package sistemacapta;

import functions.FunctionsOfClasses;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class ModificarReporteOperadorController implements Initializable {

    @FXML private TextField txtFolio, txtEmpleado, txtCalle, txtColonia, txtReferencia, txtContacto, txtTipoAtendido;
    @FXML private TextArea txtEspecificaciones, txtResultados;
    @FXML private ComboBox<String> comboGenero, comboClasificacion, comboDiaSemana, comboGrupoAtencion, comboContacto, comboTipoAtendido;
    @FXML private DatePicker datePickerFecha;
    @FXML private Button btnBuscar, btnActualizar;
    private List<CheckMenuItem> checkItems = new ArrayList<>();
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa ComboBoxes
        comboGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));
        comboClasificacion.setItems(FXCollections.observableArrayList("Rescate acuático", "Accidentes Acuáticos", "Apoyo a prestadores de servicios turista", 
        "Muerte por sumersión", "Incendio de casa habitación y/o vehículo", "Desaparecido en el mar", "Muerte Natural/Accidental", "Suicidio", "Homicidio"
        ,"Menores localizados", "Adultos localizados", "Quejas/Fraudes/Extorsión", "Robo con violencia", "Fraude cibernético", "Desove de tortugas"
        ,"Asistencias mecánicas SSP/Asist Vial", "Asist locales CAPTA", "Angeles verdes/Asist mecánicas", "Falsas alarmas/no efectadas", "Extranjeros", "Asistencias turísticas"));
        comboDiaSemana.setItems(FXCollections.observableArrayList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
        //comboGrupoAtencion.setItems(FXCollections.observableArrayList("CAPTA", "Policía vial", "Cruz roja", "Bomberos"));
                comboContacto.setItems(FXCollections.observableArrayList("Whatsapp", "Llamada", "Otro"));
        comboTipoAtendido.setItems(FXCollections.observableArrayList("Local","Turista", "Sin especificar"));

        // Conectar a la base de datos
        conectarBaseDatos();
        // Configurar el ComboBox con CheckBoxes
        configurarComboBoxGrupoAtencion();
        
        // Vincular botones con sus acciones
      /*  btnBuscar.setOnAction(event -> buscarReporte());
        btnActualizar.setOnAction(event -> actualizarReporte());*/
    }

    private void conectarBaseDatos() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacapta", "root", "josue");
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo conectar a la base de datos", AlertType.ERROR);
        }
    }

    @FXML
    private void buscarReporte() {
        String folio = txtFolio.getText();

        if (folio.isEmpty()) {
        FunctionsOfClasses.showAlertFail(Alert.AlertType.WARNING, "Advertencia", "Ingrese un folio para buscar el reporte.");
        return;

        }

        String sql = "SELECT * FROM reporteoperadores WHERE folio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(folio));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                txtEmpleado.setText(resultSet.getString("Numero_operador"));
                txtCalle.setText(resultSet.getString("calle"));
                txtColonia.setText(resultSet.getString("colonia"));
                txtReferencia.setText(resultSet.getString("referencia"));
                txtEspecificaciones.setText(resultSet.getString("especificaciones"));
                txtResultados.setText(resultSet.getString("resultados"));
               //txtContacto.setText(resultSet.getString("contacto"));
               // txtTipoAtendido.setText(resultSet.getString("tipo_atendido"));

                comboGenero.setValue(resultSet.getString("genero"));
                comboClasificacion.setValue(resultSet.getString("clasificacion"));
                comboContacto.setValue(resultSet.getString("contacto"));
                comboTipoAtendido.setValue(resultSet.getString("tipo_atendido"));
                comboDiaSemana.setValue(resultSet.getString("dia_semana"));
                comboGrupoAtencion.setValue(resultSet.getString("grupo_atencion"));
                datePickerFecha.setValue(resultSet.getDate("fecha").toLocalDate());
                         //FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Éxito", "Reporte encontrado exitosamente.");
        } else {
            FunctionsOfClasses.showAlertFail(Alert.AlertType.WARNING, "Información", "No se encontró un reporte con ese folio.");
        }
    } catch (SQLException e) {
        FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", "Error al buscar el reporte.");
        e.printStackTrace();
    }

        }
    @FXML
private void handleKeyPress(KeyEvent event) {
    if (event.getCode().toString().equals("ENTER")) {
        buscarReporte();
    }
}
    
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
    private void actualizarReporte() {
        String folio = txtFolio.getText();

        if (folio.isEmpty()) {
            mostrarAlerta("Advertencia", "Ingrese un folio para actualizar el reporte", AlertType.WARNING);
            return;
        }

        // Verificar que el folio es un número válido
        int folioInt = -1;
        try {
            folioInt = Integer.parseInt(folio);
        } catch (NumberFormatException e) {
            mostrarAlerta("Advertencia", "El folio debe ser un número válido", AlertType.WARNING);
            return;
        }

        String sql = "UPDATE reporteoperadores SET Numero_operador=?, calle=?, colonia=?, referencia=?, especificaciones=?, resultados=?, "
                   + "genero=?, clasificacion=?, dia_semana=?, contacto=?, tipo_atendido=?, grupo_atencion=?, fecha=? "
                   + "WHERE folio=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, txtEmpleado.getText());
            statement.setString(2, txtCalle.getText());
            statement.setString(3, txtColonia.getText());
            statement.setString(4, txtReferencia.getText());
            statement.setString(5, txtEspecificaciones.getText());
            statement.setString(6, txtResultados.getText());
            statement.setString(7, comboGenero.getValue());
            statement.setString(8, comboClasificacion.getValue());
            statement.setString(9, comboDiaSemana.getValue());
           // statement.setString(9, txtContacto.getText());
            //statement.setString(10, txtTipoAtendido.getText());
             statement.setString(10, comboContacto.getValue());
            statement.setString(11, comboTipoAtendido.getValue());
            statement.setString(12, comboGrupoAtencion.getValue());
            statement.setDate(13, Date.valueOf(datePickerFecha.getValue()));
            statement.setInt(14, folioInt);  // Actualizar el folio con el valor entero

            // Mostrar mensaje de depuración
            System.out.println("Ejecutando actualización para el folio: " + folioInt);

            int filasActualizadas = statement.executeUpdate();

if (filasActualizadas > 0) {
            FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Éxito", "Reporte actualizado correctamente.");
        } else {
            FunctionsOfClasses.showAlertFail(Alert.AlertType.WARNING, "Advertencia", "No se encontró un reporte con ese folio.");
        }
    } catch (SQLException e) {
        FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", "Error al actualizar el reporte.");
        e.printStackTrace();
    }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void SendToFormato(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ReportesOperadores.fxml"; // Ajusta la ruta según tu estructura de paquetes
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void SendToIncidentes(ActionEvent event) {
        String fxmlFile = "/sistemacapta/GenerarReporteOperador.fxml"; // Ajusta la ruta según tu estructura de paquetes
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void Salir(ActionEvent event) {
        String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
        System.out.println(System.getProperty("javafx.runtime.version"));

    }
}
