package sistemacapta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class SistemaCapta extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
             

            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeleccionUsuario.fxml"));
            Parent root = loader.load();

            // Crear la escena y establecerla en el escenario
            Scene scene = new Scene(root);
            primaryStage.setTitle("Sistema Capta");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
