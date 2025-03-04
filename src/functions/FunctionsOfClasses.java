/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package functions;
//capta
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;

public class FunctionsOfClasses {
    
    
    public static void showAlertGood(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        String imagePath = "/imgs/success.png";
        Image iconImage = new Image(imagePath);

        // Crear un ImageView para el icono
        ImageView iconImageView = new ImageView(iconImage);
        iconImageView.setFitWidth(48); // Ajustar el ancho del icono
        iconImageView.setFitHeight(48); // Ajustar la altura del icono

        // Establecer el icono al lado del título
        alert.setGraphic(iconImageView);
        
        // Crear un DialogPane personalizado para la alerta
        alert.initStyle(StageStyle.UNDECORATED); // Quitar la barra de título
       
        
        alert.showAndWait();
    }
    // 🔹 Nueva alerta de confirmación personalizada con imagen "pregunta"
    public static boolean showAlertConfirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("Esta acción no se puede deshacer.");

        String imagePath = "/imgs/pregunta.png";
        Image iconImage = new Image(imagePath);

        ImageView iconImageView = new ImageView(iconImage);
        iconImageView.setFitWidth(48);
        iconImageView.setFitHeight(48);

        alert.setGraphic(iconImageView);
        alert.initStyle(StageStyle.UNDECORATED);

        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonCancelar = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(botonSi, botonCancelar);

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == botonSi;
    }
      public static void showAlertFail(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        
        String imagePath = "/imgs/eliminar.png";
        Image iconImage = new Image(imagePath);

        // Crear un ImageView para el icono
        ImageView iconImageView = new ImageView(iconImage);
        iconImageView.setFitWidth(48); // Ajustar el ancho del icono
        iconImageView.setFitHeight(48); // Ajustar la altura del icono

        // Establecer el icono al lado del título
        alert.setGraphic(iconImageView);
        
           alert.initStyle(StageStyle.UNDECORATED); // Quitar la barra de título
           
        alert.showAndWait();
    }
    
    
    
      /*public static void switchToScene(ActionEvent event, String fxmlFile) {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(FunctionsOfClasses.class.getResource(fxmlFile));
            Parent root = loader.load();
            
            // Obtener la etapa actual (Stage)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Configurar la nueva escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
      public static void switchToScene(ActionEvent event, String fxmlFile) {
    System.out.println("Cambiando a la escena: " + fxmlFile); // Para depuración
    try {
        Parent root = FXMLLoader.load(FunctionsOfClasses.class.getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    }
      
      
    
      

