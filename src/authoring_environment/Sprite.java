package authoring_environment;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/***
 * Class that contains information about the sprites for eventually generating xml files
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */
public class Sprite extends ImageView {

    private final String X_STRING = "x";
    private final String Y_STRING = "y";

    private Map<String, Double> myPosition;
    private Map<String, Double> myVelocity;
    private Map<String, String> myKeyActions;
    private Map<String, String> myCharacteristics;
    private String imageuri;
    private int id;

    public Sprite (int id, String image) {
        this.id = id;
        imageuri = image;
        this.setImage(new Image(getClass().getResourceAsStream(imageuri)));
        myPosition = new HashMap<>();
        myVelocity = new HashMap<>();
        myKeyActions = new HashMap<>();
    }

    // addKeyAction()

    public int getID () {
        return id;
    }

    public void setXPosition (double value) {
        myPosition.put(X_STRING, value);
    }

    public void setYPosition (double value) {
        myPosition.put(Y_STRING, value);
    }

    public void setXVelocity (double value) {
        myVelocity.put(X_STRING, value);
    }

    public void setYVelocity (double value) {
        myVelocity.put(Y_STRING, value);
    }
    
    public void setKeyControl (String action, String result) {
        myKeyActions.put(action, result);
    }
    
    public void setCharacteristic (String characteristic, String value) {
        myCharacteristics.put(characteristic, value);
    }

    public String getCharacteristic (String characteristic) {
        return myCharacteristics.get(characteristic);
    }
}