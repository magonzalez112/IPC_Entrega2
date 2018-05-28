package entrega_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.HDGSentence;
import net.sf.marineapi.nmea.sentence.MDASentence;
import net.sf.marineapi.nmea.sentence.MWVSentence;
import net.sf.marineapi.nmea.sentence.RMCSentence;
import net.sf.marineapi.nmea.sentence.XDRSentence;
import net.sf.marineapi.nmea.util.Measurement;
import net.sf.marineapi.nmea.util.Position;

public class Model {

    private static Model model;

    private Model() {
    }
    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }
    
    //===================================================================
    // <editor-fold desc="Properties">
    
    private SentenceReader reader;

    //True Wind Dir -- direccion del viento respecto al norte
    private final DoubleProperty TWD = new SimpleDoubleProperty();
    public DoubleProperty TWDProperty() {
        return TWD;
    }
    
    // True Wind Speed -- intensidad de viento
    private final DoubleProperty TWS = new SimpleDoubleProperty();
    public DoubleProperty TWSProperty() {
        return TWS;
    }
    
    //Temperatura
     private final DoubleProperty TEMP = new SimpleDoubleProperty();
    public DoubleProperty TEMPProperty() {
        return TEMP;
    }
    
    //Heading - compas magnetic
    private final DoubleProperty HDG = new SimpleDoubleProperty();
    public DoubleProperty HDGProperty() {
        return HDG;
    }

    // Position -- posicion del GPS
    private final ObjectProperty<Position> GPS = new SimpleObjectProperty();
    public ObjectProperty<Position> GPSroperty() {
        return GPS;
    }
    
    // COG -- rumbo del GPS
    private final DoubleProperty COG = new SimpleDoubleProperty();
    public DoubleProperty COGProperty() {
        return COG;
    }
    // SOG -- velocidad del GPS
    private final DoubleProperty SOG = new SimpleDoubleProperty();
    public DoubleProperty SOGProperty() {
        return SOG;
    }
    
    private final DoubleProperty AWA = new SimpleDoubleProperty();
    public DoubleProperty AWAProperty() {
        return AWA;
    }    
    
    private final DoubleProperty AWS = new SimpleDoubleProperty();
    public DoubleProperty AWSProperty() {
        return AWS;
    }
    
    private final DoubleProperty PITCH = new SimpleDoubleProperty();
    public DoubleProperty PITCHProperty() {
        return PITCH;
    }
    
    private final DoubleProperty ROLL = new SimpleDoubleProperty();
    public DoubleProperty ROLLProperty() {
        return ROLL;
    }    
    
    // </editor-fold>
    //====================================================================
    // <editor-fold desc="Listeners">
    
    class HDGSentenceListener
            extends AbstractSentenceListener<HDGSentence> {

        @Override
        public void sentenceRead(HDGSentence sentence) {
          
            HDG.set(sentence.getHeading());
        }
    };

    class MDASentenceListener
            extends AbstractSentenceListener<MDASentence> {

        @Override
        public void sentenceRead(MDASentence sentence) {
           
            TWD.set(sentence.getTrueWindDirection());
            TWS.set(sentence.getWindSpeedKnots());
            TEMP.set(sentence.getAirTemperature());
        }
    }
    
    class MWVSentenceListener
            extends AbstractSentenceListener<MWVSentence> {

        @Override
        public void sentenceRead(MWVSentence sentence) {
           
            AWA.set(sentence.getAngle());
            AWS.set(sentence.getSpeed());
        }
    }

    class XDRSentenceListener
            extends AbstractSentenceListener<XDRSentence> {
        @Override
        public void sentenceRead(XDRSentence sentence) {
           
           for(Measurement measure : sentence.getMeasurements()){
               if(measure.getName().equals("PITCH")){
                   PITCH.set(measure.getValue());
               }
               else{
                   if(measure.getName().equals("ROLL"))
                   ROLL.set(measure.getValue());
               }
           }
            
        }
    }
    
    //========================================================================================

    class RMCSentenceListener
            extends AbstractSentenceListener<RMCSentence> {

        @Override
        public void sentenceRead(RMCSentence sentence) {
            GPS.set(sentence.getPosition());
            COG.set(sentence.getCourse());
            SOG.set(sentence.getSpeed());
        }
    }
    
    // </editor-fold>
    
    public void addSentenceReader(File file) throws FileNotFoundException {

        InputStream stream = new FileInputStream(file);
        if (reader != null) {  // esto ocurre si ya estamos leyendo un fichero
            reader.stop();
        }
        reader = new SentenceReader(stream);
 
        //==================================================================
        // <editor-fold desc="Sentence Listeners">
        
        HDGSentenceListener hdg = new HDGSentenceListener();
        reader.addSentenceListener(hdg);

        MDASentenceListener mda = new MDASentenceListener();
        reader.addSentenceListener(mda);

        MWVSentenceListener mwv = new MWVSentenceListener();
        reader.addSentenceListener(mwv);
        
        XDRSentenceListener xdr = new XDRSentenceListener();
        reader.addSentenceListener(xdr);

        RMCSentenceListener rmd = new RMCSentenceListener();
        reader.addSentenceListener(rmd);
        
         // </editor-fold>
         //===============================================================

         reader.setExceptionListener(e->{System.out.println(e.getMessage());});
         
         //================================================================     
        reader.start();
    }
}
