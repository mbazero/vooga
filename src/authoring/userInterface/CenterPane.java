package authoring.userInterface;

import java.awt.Cursor;
import java.util.Stack;
import authoring.Sprite;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Andrew Sun
 *
 */
public class CenterPane extends ScrollPane {
	
	private Stack<Sprite> myStack;
	private Scene myScene;
	private Canvas myCanvas;
	private Group myGroup;

	CenterPane(Scene scene) {
		myScene = scene;
		myGroup = new Group();
		myCanvas = new Canvas(400,400);
		myCanvas.getGraphicsContext2D().setStroke(Color.BLACK);
		//myCanvas.getGraphicsContext2D().strokeLine(0, 0, 400, 400);
		
		this.setContent(myGroup);
		myGroup.getChildren().add(new Rectangle(400, 400, Color.WHITE));
		myGroup.setOnMouseClicked(e -> canvasClicked(e));
		
		// Use stack, or just a group?
		myStack = new Stack<>();
		
	}

	private void canvasClicked(MouseEvent e) {

		try{
		Sprite s = ((SpriteCursor) myScene.getCursor()).getCurrentSprite();
		
		// Hey Andrew! I commented this next line out because I decided to pass in 
		// a Consumer in the creation of the Sprite (in CharacterCreationPane), and
		// this line was resetting the setOnMouseClicked method that I had used in 
		// order to switch between panes (although in the end it should probably be 
		// sent to some intermediate class rather than directly switch panes so that  
		// the logic of how many sprites are currently clicked is done before 
		// deciding what will happen).
		
//		s.setOnMouseClicked(p -> spriteClicked(p, s));
		
		s.setX(e.getX() - s.getImage().getWidth()/2);
		s.setY(e.getY() - s.getImage().getHeight()/2);
		myGroup.getChildren().add(s);
		
		System.out.println(s.getID());
		myStack.add(s);
		myScene.setCursor(ImageCursor.DEFAULT);
		}
		catch (ClassCastException a){
			
		}
		catch (NullPointerException b){
			
		}
	}

	private void spriteClicked(MouseEvent p, Sprite s) {
		myGroup.getChildren().remove(s);
		System.out.println("Removing");
	}
	
	
	
	
}