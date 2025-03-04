package sistemacapta;

import java.net.URL;
import java.sql.Connection;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import DataBaseMethods.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import functions.FunctionsOfClasses;
import javafx.scene.input.KeyEvent;

public class GenerarReporteOperadorController implements Initializable {

    @FXML private DatePicker StartDate;
    @FXML private Button RegisterButton;
    @FXML private DatePicker EndDate;
   // @FXML private Button GenerarReporteServicio;
    @FXML private TextField txtFolio;
    @FXML private TextField txtFecha;
    @FXML private Button btnEliminar;
        @FXML private Button btnImprimir;
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = ConnectionDB.connect();
        btnEliminar.setOnAction(event -> eliminarReporte());
        btnImprimir.setOnAction(event -> generarPDFPorFolio());
        RegisterButton.setOnAction(event -> {
            try {
                GenerarReporteOperador(event);
            } catch (Exception ex) {
                Logger.getLogger(GenerarReporteOperadorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
// 🔹 Cuando el usuario presiona "Enter" en txtFolio, se llena automáticamente txtFecha
    @FXML
    private void buscarFecha(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            String folio = txtFolio.getText().trim();
            if (!folio.isEmpty()) {
                obtenerFechaPorFolio(folio);
            }
        }
    }
    private void obtenerFechaPorFolio(String folio) {
        String query = "SELECT fecha FROM reporteoperadores WHERE folio = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, folio);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                txtFecha.setText(rs.getString("fecha"));
            } else {
                FunctionsOfClasses.showAlertFail(Alert.AlertType.WARNING, "Advertencia", "No se encontró un reporte con ese folio.");
                txtFecha.clear();
            }
        } catch (SQLException e) {
            FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", "Error al buscar la fecha: " + e.getMessage());
        }
    }
    @FXML
    private void eliminarReporte() {
        String folio = txtFolio.getText().trim();

        if (folio.isEmpty()) {
            FunctionsOfClasses.showAlertFail(Alert.AlertType.WARNING, "Advertencia", "Ingresa un folio válido.");
            return;
        }

        boolean confirmacion = FunctionsOfClasses.showAlertConfirm("Confirmación", "¿Seguro que desea eliminar el reporte #" + folio + "?");

        if (confirmacion) {
            boolean eliminado = EliminarReporte.eliminarReportePorFolio(connection, folio);

            if (eliminado) {
                FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Éxito", "Folio eliminado correctamente.");
                txtFolio.clear();
                txtFecha.clear();
            } else {
                FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", "No se encontró el folio en la base de datos.");
            }
        }
    }
        //Codigo para los saltos después de cada consulta
       private void LineJumps( Document document,int limits) throws DocumentException {
           for(int i = 0; i < limits; i++){
           document.add(new Paragraph(Chunk.NEWLINE));
           }
       }
        private void GenerarReporteOperador(ActionEvent event) throws FileNotFoundException {
    LocalDate selectedDate = StartDate.getValue();
    LocalDate endDate = EndDate.getValue();
    
    if (selectedDate != null && endDate != null) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte como");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF", "pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();
                // Agregar imagen
                String imagePath = "src/imgs/logo2capta SF.png"; 
                try {
                    Image image = Image.getInstance(imagePath);
                    image.scaleToFit(200, 100);
                    document.add(image);
                   // LineJumps(document, 1);
                    document.add(new Paragraph("Fecha de reporte: " + selectedDate + " al " + endDate));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Conexión a la base de datos
                try (Connection conn = ConnectionDB.connect()) {

                    // Total de emergencias
                    ejecutarConsulta(document, conn, 
                        "Emergencias totales atendidas", 
                        "SELECT COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ?", 
                        selectedDate, endDate);

                    // Género (Hombres y Mujeres)
                    String queryGenero = "SELECT genero, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY genero";
                    try (PreparedStatement stmt = conn.prepareStatement(queryGenero)) {
                        stmt.setDate(1, java.sql.Date.valueOf(selectedDate));
                        stmt.setDate(2, java.sql.Date.valueOf(endDate));

                        int totalMasculinos = 0, totalFemeninos = 0;
                        try (ResultSet rs = stmt.executeQuery()) {
                            while (rs.next()) {
                                String genero = rs.getString(1).toLowerCase();
                                int count = rs.getInt(2);
                                if (genero.equals("masculino")) {
                                    totalMasculinos = count;
                                } else if (genero.equals("femenino")) {
                                    totalFemeninos = count;
                                }
                            }
                        }

                        document.add(new Paragraph("Total de Masculinos atendidos: " + totalMasculinos));
                        document.add(new Paragraph("Total de Femeninas atendidas: " + totalFemeninos));
                        LineJumps(document, 1); // Espacio en blanco
                        //document.add(new Paragraph ("===================="));
                    }

                    // Tipo de atendido
                    ejecutarConsultaConGrupo(document, conn, 
                        "Atendidos", 
                        "SELECT tipo_atendido, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY tipo_atendido", 
                        selectedDate, endDate);
                    
                     //Medio de contacto
                    ejecutarConsultaConGrupo(document, conn, 
                        "Medio de contacto", 
                        "SELECT contacto, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY contacto", 
                        selectedDate, endDate);
                    // Grupo de atención
                    ejecutarConsultaConGrupo(document, conn, 
                        "Grupo de Atención", 
                        "SELECT grupo_atencion, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY grupo_atencion", 
                        selectedDate, endDate);

                    // Clasificación
                    ejecutarConsultaConGrupo(document, conn, 
                        "Clasificación", 
                        "SELECT clasificacion, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY clasificacion", 
                        selectedDate, endDate);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                document.close();
                functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "", "Reporte generado exitosamente en " + filePath);

            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Guardado cancelado.");
        }
    } else {
        System.out.println("No se ha seleccionado una fecha.");
    }
}

        // Método para ejecutar consultas generales con conteo
        private void ejecutarConsulta(Document document, Connection conn, String titulo, String query, LocalDate startDate, LocalDate endDate) throws SQLException, DocumentException {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, java.sql.Date.valueOf(startDate));
                stmt.setDate(2, java.sql.Date.valueOf(endDate));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int total = rs.getInt(1);
                        document.add(new Paragraph(titulo + ": " + total));
                        LineJumps(document, 1); // Espacio en blanco
                        //document.add(new Paragraph ("===================="));
                    }
                }
            }
        }

        // Método para ejecutar consultas con agrupamiento
        private void ejecutarConsultaConGrupo(Document document, Connection conn, String titulo, String query, LocalDate startDate, LocalDate endDate) throws SQLException, DocumentException {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, java.sql.Date.valueOf(startDate));
                stmt.setDate(2, java.sql.Date.valueOf(endDate));
                try (ResultSet rs = stmt.executeQuery()) {
                    document.add(new Paragraph(titulo + ":"));
                    while (rs.next()) {
                        String categoria = rs.getString(1);
                        int count = rs.getInt(2);
                        document.add(new Paragraph(" - " + categoria + ": " + count));
                    }
                    LineJumps(document, 1); // Espacio en blanco
                    //document.add(new Paragraph("===================="));
                }
            }
        }

        @FXML
    private void generarPDFPorFolio() {
        String folio = txtFolio.getText().trim();
        if (folio.isEmpty()) {
            FunctionsOfClasses.showAlertFail(Alert.AlertType.WARNING, "Advertencia", "Ingresa un folio válido.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte como");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF", "pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // Agregar imagen del logo
                String imagePath = "src/imgs/logo2capta SF.png";
                try {
                    Image image = Image.getInstance(imagePath);
                    image.scaleToFit(200, 100);
                    image.setAlignment(Element.ALIGN_CENTER);
                    document.add(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                // Agregar título principal
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                Paragraph title = new Paragraph("Reporte de Operador", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph("\n"));
                
                // Obtener datos del reporte
                try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM reporteoperadores WHERE folio = ?")) {
                    pstmt.setString(1, folio);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        PdfPTable table = new PdfPTable(2);
                        table.setWidthPercentage(100);
                        
                        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                        Font dataFont = new Font(Font.FontFamily.HELVETICA, 12);
                        
                        addTableHeader(table, "Folio", rs.getString("folio"), headerFont, dataFont);
                        addTableHeader(table, "Número de operador", rs.getString("Numero_operador"), headerFont, dataFont);
                        addTableHeader(table, "Fecha", rs.getString("fecha"), headerFont, dataFont);
                        addTableHeader(table, "Día de la Semana", rs.getString("dia_semana"), headerFont, dataFont);
                        addTableHeader(table, "Tipo Atendido", rs.getString("tipo_atendido"), headerFont, dataFont);
                        addTableHeader(table, "Medio de Contacto", rs.getString("contacto"), headerFont, dataFont);
                        addTableHeader(table, "Género", rs.getString("genero"), headerFont, dataFont);
                        addTableHeader(table, "Calle", rs.getString("calle"), headerFont, dataFont);
                        addTableHeader(table, "Colonia", rs.getString("colonia"), headerFont, dataFont);
                        addTableHeader(table, "Referencia", rs.getString("referencia"), headerFont, dataFont);
                        addTableHeader(table, "Clasificación", rs.getString("clasificacion"), headerFont, dataFont);
                        addTableHeader(table, "Grupo de Atención", rs.getString("grupo_atencion"), headerFont, dataFont);
                        addTableHeader(table, "Especificaciones", rs.getString("especificaciones"), headerFont, dataFont);
                        addTableHeader(table, "Resultados", rs.getString("resultados"), headerFont, dataFont);
                        
                        document.add(table);
                    } else {
                        FunctionsOfClasses.showAlertFail(Alert.AlertType.WARNING, "Advertencia", "No se encontró un reporte con ese folio.");
                        document.close();
                        return;
                    }
                }
                
                document.close();
                FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Éxito", "Reporte generado exitosamente en " + filePath);

            } catch (DocumentException | FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void addTableHeader(PdfPTable table, String header, String data, Font headerFont, Font dataFont) {
        PdfPCell headerCell = new PdfPCell(new Paragraph(header, headerFont));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);
        table.addCell(new PdfPCell(new Paragraph(data, dataFont)));
    }
        @FXML
    private void SendToFormato(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ReportesOperadores.fxml";
        FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void SendToModificar(ActionEvent event) {
        String fxmlFile = "/sistemacapta/ModificarReporteOperador.fxml";
        FunctionsOfClasses.switchToScene(event, fxmlFile);
    }

    @FXML
    private void Salir(ActionEvent event) {
        String fxmlFile = "/sistemacapta/SeleccionUsuario.fxml";
        FunctionsOfClasses.switchToScene(event, fxmlFile);
    }
}
