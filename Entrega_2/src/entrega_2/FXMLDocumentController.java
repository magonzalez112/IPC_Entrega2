package entrega_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.ToggleSwitch;
/**
 * @author migonsa1
 */
public class FXMLDocumentController implements Initializable {
   
    private Model model;
    private Timeline animation;
    private XYChart.Series <String, Number> series1;
    private XYChart.Series <String, Number> series2;
    private XYChart.Series <String, Number> series3;
    private XYChart.Series <String, Number> series4;
    private int sequence = 0;
    
    // -----------------------
    // <editor-fold desc="instanciacion FXML ">
    
    @FXML
    private Label hdgLabel;

    @FXML
    private Label twdLabel;

    @FXML
    private Label twsLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private Label awaLabel;

    @FXML
    private Label pitchLabel;

    @FXML
    private Label rollLabel;

    @FXML
    private Label awsLabel;

    @FXML
    private Label latLabel;

    @FXML
    private Label cogLabel;

    @FXML
    private Label sogLabel;

    @FXML
    private Label lonLabel;
    
    @FXML
    private ToggleSwitch swTime;

    @FXML
    private ToggleSwitch swShift;
    
    @FXML
    private Label ficheroLabel;
    
    @FXML
    private LineChart<String, Number> graphTWD;
       
    @FXML
    private NumberAxis yaxistwd;

    @FXML
    private CategoryAxis xaxistwd;

    @FXML
    private LineChart<String, Number> graphTWS;
    
    @FXML
    private CategoryAxis xaxistws;

    @FXML
    private NumberAxis yaxistws;

    // </editor-fold>
    //------------------------
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();
         
         // <editor-fold desc="Graphs init">
        graphTWD.setCreateSymbols(false);
        graphTWD.setAnimated(false);
        graphTWD.setLegendVisible(false);
        graphTWS.setCreateSymbols(false);
        graphTWS.setAnimated(false);
        graphTWS.setLegendVisible(false);
        graphTWD.setHorizontalGridLinesVisible(false);
        graphTWS.setHorizontalGridLinesVisible(false);
        graphTWD.setVerticalGridLinesVisible(false);
        graphTWS.setVerticalGridLinesVisible(false);
        
        series1 = new XYChart.Series<>();
        series2 = new XYChart.Series<>();
        series1.setName("TWD 2 min"); 
        series2.setName("TWD 10 min"); 
        xaxistwd.setLabel("Tiempo (s)");
        yaxistwd.setLabel("Dirección");
        graphTWD.getData().add(series1);
        
        series3 = new XYChart.Series<>();
        series4 = new XYChart.Series<>();
        series3.setName("TWS 2 min"); 
        series4.setName("TWS 10 min"); 
        xaxistws.setLabel("Tiempo (s)");
        yaxistws.setLabel("Velocidad");
        graphTWS.getData().add(series3); 
        // </editor-fold>
        
        // -----------------------
        // <editor-fold desc="Listeners">
        
        swTime.selectedProperty().addListener((ObservableValue<? 
                extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (swTime.isSelected()) {
                graphTWD.getData().clear();
                graphTWS.getData().clear();
                graphTWD.getData().add(series2);
                graphTWS.getData().add(series4);
            }
            else{
                graphTWD.getData().clear();
                graphTWS.getData().clear();
                graphTWD.getData().add(series1);
                graphTWS.getData().add(series3);
            }
        });

        swShift.selectedProperty().addListener((ObservableValue<? 
                extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            String themeDark = getClass().getResource("dark.css").toExternalForm();
            String themeLight = getClass().getResource("light.css").toExternalForm();
            if(swShift.isSelected()){
                swShift.getScene().getStylesheets().clear();
                swShift.getScene().getStylesheets().add(themeDark);
            }
                else {
                swShift.getScene().getStylesheets().clear();
                swShift.getScene().getStylesheets().add(themeLight);
            }
        });
        model.HDGProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "º";
            Platform.runLater(() -> {
                hdgLabel.setText(dat);
            });
        });
        model.TWDProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "º";
            Platform.runLater(() -> {
                twdLabel.setText(dat);
            });
        });
        model.TWSProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "Kn";
            Platform.runLater(() -> {
                twsLabel.setText(dat);
            });
        });
        model.TEMPProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "ºC";
            Platform.runLater(() -> {
                tempLabel.setText(dat);
            });
        });
       
         DecimalFormat df = new DecimalFormat("#.00000");
         
        model.GPSroperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                lonLabel.setText(String.valueOf(df.format(newValue.getLongitude())) + " " + newValue.getLongitudeHemisphere());
                latLabel.setText(String.valueOf(df.format(newValue.getLatitude())) + " " + newValue.getLatitudeHemisphere());
            });
        });
        model.COGProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "º";
            Platform.runLater(() -> {
                cogLabel.setText(dat);
            });
        });
        model.SOGProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "Km/h";
            Platform.runLater(() -> {
                sogLabel.setText(dat);
            });
        });
        model.AWAProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "º";
            Platform.runLater(() -> {
                awaLabel.setText(dat);
            });
        });
        model.AWSProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "Km/h";
            Platform.runLater(() -> {
                awsLabel.setText(dat);
            });
        });
        model.PITCHProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "Km/h";
            Platform.runLater(() -> {
                pitchLabel.setText(dat);
            });
        });
        model.ROLLProperty().addListener((observable, oldValue, newValue) -> {
            String dat = String.valueOf(newValue) + "Km/h";
            Platform.runLater(() -> {
                rollLabel.setText(dat);
            });
        });
        
        // </editor-fold>
        //------------------------
        
        animation = new Timeline();
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.millis(1000),
                        (ActionEvent actionEvent) -> plotTime()));
        animation.setCycleCount(Animation.INDEFINITE);
        
    }    
    
    @FXML
    private void cargarFichero(ActionEvent event) throws FileNotFoundException {
        FileChooser ficheroChooser = new FileChooser();
        ficheroChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("ficheros NMEA", "*.NMEA"));
        ficheroChooser.setTitle("fichero datos NMEA");
        
        File ficheroNMEA = ficheroChooser.showOpenDialog(ficheroLabel.getScene().getWindow());
        if (ficheroNMEA != null) {
            ficheroLabel.setText("fichero: " + ficheroNMEA.getName()); 
            model.addSentenceReader(ficheroNMEA);
            animation.play();
        }
    }
    
    private void plotTime() {
       sequence++;
       series1.getData().add(new XYChart.Data<String, Number>(
               String.valueOf(sequence), model.TWDProperty().getValue()));
       series2.getData().add(new XYChart.Data<String, Number>(
               String.valueOf(sequence), model.TWDProperty().getValue()));
       series3.getData().add(new XYChart.Data<String, Number>(
               String.valueOf(sequence), model.TWSProperty().getValue()));
       series4.getData().add(new XYChart.Data<String, Number>(
               String.valueOf(sequence), model.TWSProperty().getValue()));
        
        if (sequence > 120) {
            series1.getData().remove(0);
            series3.getData().remove(0);
        }
        if (sequence > 600) {
            series2.getData().remove(0);
            series4.getData().remove(0);
        }
    }
}
