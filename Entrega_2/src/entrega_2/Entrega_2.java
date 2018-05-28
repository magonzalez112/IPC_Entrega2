package entrega_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * @author migonsa1
 */
public class Entrega_2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        String themeLight = getClass().getResource("light.css").toExternalForm();
        scene.getStylesheets().add(themeLight);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("NMEA");
        stage.setMinWidth(800);
        stage.setMinHeight(480);
        stage.getIcons().add(new 
                Image(getClass().getResourceAsStream("icon.png")));
        stage.show();
                stage.setOnCloseRequest(e->{
            System.exit(0);
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
