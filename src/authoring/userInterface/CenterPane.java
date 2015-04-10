package authoring.userInterface;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoring.Sprite;
import authoring.util.FrontEndUtils;

/**
 * 
 * @author Andrew Sun & Daniel "the fresh code machine of bel-duke" Luker
 *
 */
public class CenterPane extends ScrollPane {

	private ObservableList<Sprite> myListOfSprites;

	private Scene myScene;
	private Group myGroup;
	private Rectangle myCurrentRectangle;

	private int width;
	private int height;

	private static CenterPane mInstance;

	public static CenterPane getInstance(Scene s) {
		if (mInstance == null)
			mInstance = new CenterPane(s);
		return mInstance;
	}

	private CenterPane(Scene scene) {
		myScene = scene;
		myGroup = new Group();
		// myCanvas = new Canvas(
		// (width = (int) (scene.getWidth() - this.RIGHT_PANE_WIDTH)),
		// (height = (int) (scene.getHeight() - this.TOP_PANE_HEIGHT)));
		// myCanvas.getGraphicsContext2D().setStroke(Color.BLACK);
		// myCanvas.getGraphicsContext2D().strokeLine(0, 0, 400, 400);

		this.setContent(myGroup);
		myGroup.getChildren().add(new Rectangle(width, height, Color.WHITE));
		myGroup.setOnMouseClicked(e -> canvasClicked(e));

		// Use stack, or just a group?
		myListOfSprites = FXCollections.observableArrayList();

		FrontEndUtils.setKeyActions(this);

	}

	private void canvasClicked(MouseEvent e) {
		try {
			Sprite s = ((SpriteCursor) myScene.getCursor()).getCurrentSprite();

			s.setXPosition(e.getX() - s.getImage().getWidth() / 2);
			s.setYPosition(e.getY() - s.getImage().getHeight() / 2);
			myGroup.getChildren().add(s);

			System.out.println(s.getID());
			this.myListOfSprites.add(s);
			myScene.setCursor(ImageCursor.DEFAULT);

		} catch (ClassCastException a) {
			System.out.println("error");
		} catch (NullPointerException b) {
			System.out.println("error");
		}

	}

	public Collection<Sprite> getSprites() {
		return myListOfSprites;
	}

	public void createRegion(double x, double y) {
		if (myCurrentRectangle != null) {
			myGroup.getChildren().remove(myCurrentRectangle);
			System.out.println("removing rectangle");
		}
		myCurrentRectangle = new Rectangle(x, y, Color.WHITE);
		myCurrentRectangle.setOnMouseClicked(e -> canvasClicked(e));
		myGroup.getChildren().add(myCurrentRectangle);
	}
}
