package sistemacapta;
import com.itextpdf.text.*;

//import java.awt.Font;
import java.io.*;
import com.itextpdf.text.Image;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;


import com.itextpdf.text.Paragraph;

import java.net.URL;
import java.sql.Connection;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

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

import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.data.category.DefaultCategoryDataset;


import java.time.LocalDate;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;

import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.ui.TextAnchor;  // Mantén esta
import org.jfree.ui.TextAnchor;  // El correcto

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
//import org.jfree.chart.ui.TextAnchor;

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
  
                      
// Código para los saltos después de cada consulta
private void GenerarReporteOperador(ActionEvent event) throws FileNotFoundException, IOException {
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

                // Agregar imagen del logo
                String imagePath = "src/imgs/logo2capta SF.png"; 
                try {
                    Image image = Image.getInstance(imagePath);
                    image.scaleToFit(200, 100);
                    document.add(image);
                    document.add(new Paragraph("Fecha de reporte: " + selectedDate + " al " + endDate));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try (Connection conn = ConnectionDB.connect()) {
                    // **Sección de Conteos**
                    // **Total de Atendidos**
                    String totalQuery = "SELECT COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ?";
                    int totalAtendidos = ejecutarConsultaConteo(conn, totalQuery, selectedDate, endDate);
                    document.add(new Paragraph("Total de Atendidos: " + totalAtendidos, FontFactory.getFont(FontFactory.HELVETICA, 12)));
                    document.add(Chunk.NEWLINE);

                    // **Consultas y Conteos**
                    ejecutarConsultaConteoYAgregarTexto(conn, document, "Género", "SELECT genero, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY genero", selectedDate, endDate);
                    ejecutarConsultaConteoYAgregarTexto(conn, document, "Tipo Atendido", "SELECT tipo_atendido, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY tipo_atendido", selectedDate, endDate);
                    ejecutarConsultaConteoYAgregarTexto(conn, document, "Medio de Contacto", "SELECT contacto, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY contacto", selectedDate, endDate);
                    ejecutarConsultaConteoYAgregarTexto(conn, document, "Grupo de Atención", "SELECT grupo_atencion, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY grupo_atencion", selectedDate, endDate);
                    ejecutarConsultaConteoYAgregarTexto(conn, document, "Clasificación", "SELECT clasificacion, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY clasificacion", selectedDate, endDate);

                    document.add(new Paragraph("Gráficas:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
                    document.add(Chunk.NEWLINE);

                    // **Generación de Gráficas con el máximo basado en totalAtendidos**
                    generarGrafica(conn, document, "Género", "SELECT genero, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY genero", selectedDate, endDate, "GraficaGenero.png", totalAtendidos);
                    generarGrafica(conn, document, "Tipo Atendido", "SELECT tipo_atendido, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY tipo_atendido", selectedDate, endDate, "GraficaTipoAtendido.png", totalAtendidos);
                    generarGrafica(conn, document, "Medio de Contacto", "SELECT contacto, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY contacto", selectedDate, endDate, "GraficaMedioContacto.png", totalAtendidos);
                    generarGrafica(conn, document, "Grupo de Atención", "SELECT grupo_atencion, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY grupo_atencion", selectedDate, endDate, "GraficaGrupoAtencion.png", totalAtendidos);
                    generarGrafica(conn, document, "Clasificación", "SELECT clasificacion, COUNT(*) FROM reporteoperadores WHERE fecha BETWEEN ? AND ? GROUP BY clasificacion", selectedDate, endDate, "GraficaClasificacion.png", totalAtendidos);
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

// **Genera una gráfica con el máximo en el eje Y basado en totalAtendidos**
// **Genera una gráfica y la añade al PDF**
private void generarGrafica(Connection conn, Document document, String titulo, String query, LocalDate startDate, LocalDate endDate, String nombreImagen, int totalAtendidos) throws SQLException, DocumentException, IOException {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setDate(1, java.sql.Date.valueOf(startDate));
        stmt.setDate(2, java.sql.Date.valueOf(endDate));
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dataset.addValue(rs.getInt(2), titulo, rs.getString(1));
            }
        }
    }

    if (!dataset.getRowKeys().isEmpty()) {
        JFreeChart chart = ChartFactory.createBarChart(titulo, "", "Cantidad", dataset);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        
        
         // **Usar el total de reportes como el valor máximo en el eje Y**
        plot.getRangeAxis().setUpperBound(totalAtendidos);


        // Cambiar el color de las barras
        renderer.setSeriesPaint(0, new Color(181,52,52));

        // Hacer las barras más delgadas
        renderer.setMaximumBarWidth(0.05);

        // Mostrar valores en las barras
        renderer.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator());
        renderer.setSeriesItemLabelsVisible(0, true);
        renderer.setSeriesItemLabelFont(0, new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
        renderer.setSeriesItemLabelPaint(0, Color.BLACK);
        
        // Ajustar las etiquetas en el eje X
        CategoryAxis categoryAxis = plot.getDomainAxis();
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);  // Rota las etiquetas 45 grados

        // Guardar la imagen y agregarla al PDF
        File chartFile = new File(nombreImagen);
        ChartUtilities.saveChartAsPNG(chartFile, chart, 500, 300);
        Image img = Image.getInstance(nombreImagen);
        img.scaleToFit(500, 300);
        document.add(img);
        document.add(Chunk.NEWLINE);
    } else {
        document.add(new Paragraph("No hay datos para " + titulo + "."));
    }
}


        // **Ejecuta una consulta de conteo total**
        private int ejecutarConsultaConteo(Connection conn, String query, LocalDate startDate, LocalDate endDate) throws SQLException {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, java.sql.Date.valueOf(startDate));
                stmt.setDate(2, java.sql.Date.valueOf(endDate));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            return 0;
        }

        // **Ejecuta una consulta y agrega el conteo de categorías al PDF**
        private void ejecutarConsultaConteoYAgregarTexto(Connection conn, Document document, String titulo, String query, LocalDate startDate, LocalDate endDate) throws SQLException, DocumentException {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, java.sql.Date.valueOf(startDate));
                stmt.setDate(2, java.sql.Date.valueOf(endDate));
                try (ResultSet rs = stmt.executeQuery()) {
                    document.add(new Paragraph(titulo + ":", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                    while (rs.next()) {
                       String categoria = rs.getString(1);
                        int count = rs.getInt(2);
                       document.add(new Paragraph(" - " + categoria + ": " + count, FontFactory.getFont(FontFactory.HELVETICA, 12)));
                    }
                    document.add(Chunk.NEWLINE);
                }
            }
        }

        // **Genera una gráfica y la añade al PDF**
        private void generarGrafica(Connection conn, Document document, String titulo, String query, LocalDate startDate, LocalDate endDate, String nombreImagen) throws SQLException, DocumentException, IOException {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, java.sql.Date.valueOf(startDate));
                stmt.setDate(2, java.sql.Date.valueOf(endDate));
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        dataset.addValue(rs.getInt(2), titulo, rs.getString(1));
                    }
                }
            }

            if (!dataset.getRowKeys().isEmpty()) {
                JFreeChart chart = ChartFactory.createBarChart(titulo, "", "Cantidad", dataset);
                CategoryPlot plot = chart.getCategoryPlot();
                BarRenderer renderer = (BarRenderer) plot.getRenderer();

                // Cambiar el color de las barras
                //renderer.setSeriesPaint(0, new Color(181,52,52));

                // Hacer las barras más delgadas
            //    renderer.setMaximumBarWidth(0.05);

                // Mostrar valores en las barras
            renderer.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator());
            renderer.setSeriesItemLabelsVisible(0, true);
            renderer.setSeriesItemLabelFont(0, new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
            renderer.setSeriesItemLabelPaint(0, Color.BLACK);
            
                // Guardar la imagen y agregarla al PDF
                File chartFile = new File(nombreImagen);
                ChartUtilities.saveChartAsPNG(chartFile, chart, 500, 300);
                Image img = Image.getInstance(nombreImagen);
                img.scaleToFit(500, 300);
                document.add(img);
                document.add(Chunk.NEWLINE);
            } else {
                document.add(new Paragraph("No hay datos para " + titulo + "."));
            }
        }
        //  GENERAR REPORTE POR FOLIO
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
            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 18, com.itextpdf.text.Font.BOLD);

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
            com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
                        
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
    
private void addTableHeader(PdfPTable table, String header, String data, com.itextpdf.text.Font headerFont, com.itextpdf.text.Font dataFont) {
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

