package sistemacapta;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class ModificarReporteOperadorController implements Initializable {

    @FXML private TextField txtFolio, txtCalle, txtColonia, txtReferencia, txtContacto, txtTipoAtendido;
    @FXML private TextArea txtEspecificaciones, txtResultados;
    @FXML private ComboBox<String> comboGenero, comboClasificacion, comboDiaSemana, comboGrupoAtencion, comboContacto, comboTipoAtendido;
    @FXML private DatePicker datePickerFecha;
    @FXML private Button btnBuscar, btnActualizar;

    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa ComboBoxes
        comboGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));
        comboClasificacion.setItems(FXCollections.observableArrayList("Rescate acuático", "Asistencias médicas", "Muerte por sumersión"));
        comboDiaSemana.setItems(FXCollections.observableArrayList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
        comboGrupoAtencion.setItems(FXCollections.observableArrayList("CAPTA", "Policía vial", "Cruz roja", "Bomberos"));
                comboContacto.setItems(FXCollections.observableArrayList("Whatsapp", "Llamada", "Otro"));
        comboTipoAtendido.setItems(FXCollections.observableArrayList("local","turista", "Sin especificar"));

        // Conectar a la base de datos
        conectarBaseDatos();

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
            mostrarAlerta("Advertencia", "Ingrese un folio para buscar el reporte", AlertType.WARNING);
            return;
        }

        String sql = "SELECT * FROM reporteoperadores WHERE folio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(folio));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
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
            } else {
                mostrarAlerta("Información", "No se encontró un reporte con ese folio", AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al buscar el reporte", AlertType.ERROR);
            e.printStackTrace();
        }
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

        String sql = "UPDATE reporteoperadores SET calle=?, colonia=?, referencia=?, especificaciones=?, resultados=?, "
                   + "genero=?, clasificacion=?, dia_semana=?, contacto=?, tipo_atendido=?, grupo_atencion=?, fecha=? "
                   + "WHERE folio=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, txtCalle.getText());
            statement.setString(2, txtColonia.getText());
            statement.setString(3, txtReferencia.getText());
            statement.setString(4, txtEspecificaciones.getText());
            statement.setString(5, txtResultados.getText());
            statement.setString(6, comboGenero.getValue());
            statement.setString(7, comboClasificacion.getValue());
            statement.setString(8, comboDiaSemana.getValue());
           // statement.setString(9, txtContacto.getText());
            //statement.setString(10, txtTipoAtendido.getText());
             statement.setString(9, comboContacto.getValue());
            statement.setString(10, comboTipoAtendido.getValue());
            statement.setString(11, comboGrupoAtencion.getValue());
            statement.setDate(12, Date.valueOf(datePickerFecha.getValue()));
            statement.setInt(13, folioInt);  // Actualizar el folio con el valor entero

            // Mostrar mensaje de depuración
            System.out.println("Ejecutando actualización para el folio: " + folioInt);

            int filasActualizadas = statement.executeUpdate();

            if (filasActualizadas > 0) {
                mostrarAlerta("Éxito", "Reporte actualizado correctamente", AlertType.INFORMATION);
            } else {
                mostrarAlerta("Advertencia", "No se encontró un reporte con ese folio", AlertType.WARNING);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al actualizar el reporte", AlertType.ERROR);
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
        String fxmlFile = "/sistemacapta/ReportesOperadores.fxml"; // Ajusta la ruta según tu estructura de paquetes
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void Salir(ActionEvent event) {
        String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml";
        functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }
}
