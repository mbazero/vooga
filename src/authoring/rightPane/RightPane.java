package authoring.rightPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
<<<<<<< HEAD
import authoring.AbstractSprite;
import authoring.Sprite;
import authoring.SpriteType;

=======
import authoring.Sprite;
>>>>>>> 040310bf83d8f9d359743e47048a6fb27acca693

/**
 * This class represents the right pane on the screen. It will allow the user to
 * edit a particular character, to edit the interactions between characters, and
 * to create new characters.
 * 
 * @author Natalie Chanfreau, Daniel Luker
 *
 */
public class RightPane extends VBox {

<<<<<<< HEAD
    private Scene myScene;
    private EditingPane myCurrentContent;

    private final static int SPACING = 20;
    private final static int PADDING = 10;
    private final static String CSS = "styles/right_pane.css";

    public RightPane (Scene scene) {
        super(SPACING);
        myScene = scene;

        getStylesheets().add(CSS);
        setPadding(new Insets(PADDING));
        setAlignment(Pos.TOP_CENTER);

        // initializeCurrentContent(new DefaultEditingPane(scene));
        // initializeCurrentContent(new CharacterEditingPane(scene, null));
        initializeCurrentContent(new CharacterCreationPane(scene,
                                                           s -> switchToCharacterEditingPane(s)));
    }

    public void switchToCharacterEditingPane (Sprite s) {
        switchToPane(new CharacterEditingPane(myScene, s, i -> switchToCharacterCreationPane()));
    }

    public void switchToCharacterCreationPane () {
        switchToPane(new CharacterCreationPane(myScene,
                                               s -> switchToCharacterEditingPane(s)));
    }

    public void switchToInteractionEditingPane (SpriteType sprite1, SpriteType sprite2) {
        switchToPane(new InteractionEditingPane(myScene, sprite1, sprite2));
    }

    public void switchToDefaultPane () {
        switchToPane(new DefaultEditingPane(myScene));
    }

    private void switchToPane (EditingPane newPane) {
        clearChildren();
        myCurrentContent = newPane;
        addFromCurrentContent();
    }

    private void clearChildren () {
        getChildren().clear();
    }

    private void addFromCurrentContent () {
        getChildren().addAll(myCurrentContent.getChildren());
    }

    private void initializeCurrentContent (EditingPane content) {
        myCurrentContent = content;
        this.getChildren().addAll(myCurrentContent.getChildren());
    }
=======
	private Scene myScene;
	private EditingPane myCurrentContent;

	private final static int SPACING = 20;
	private final static int PADDING = 10;
	private final static String CSS = "styles/right_pane.css";

	private static RightPane mInstance;

	public static RightPane getInstance() {
		if (mInstance == null)
			mInstance = new RightPane();
		return mInstance;
	}


	private RightPane() {
		super(SPACING);

		getStylesheets().add(CSS);
		setPadding(new Insets(PADDING));
		setAlignment(Pos.TOP_CENTER);

		// initializeCurrentContent(new DefaultEditingPane(scene));
		// initializeCurrentContent(new CharacterEditingPane(scene, null));
	}
	
	public void setScene(Scene scene) {
		myScene = scene;
		initializeCurrentContent(new CharacterCreationPane(scene,
				s -> switchToCharacterEditingPane(s)));
	}

	public void switchToCharacterEditingPane(Sprite sprite) {
		switchToPane(new CharacterEditingPane(myScene, sprite));
	}

	public void switchToCharacterCreationPane() {
		switchToPane(new CharacterCreationPane(myScene,
				s -> switchToCharacterEditingPane(s)));
	}

	public void switchToInteractionEditingPane(Sprite sprite1, Sprite sprite2) {
		switchToPane(new InteractionEditingPane(myScene, sprite1, sprite2));
	}

	public void switchToDefaultPane() {
		switchToPane(new DefaultEditingPane(myScene));
	}

	private void switchToPane(EditingPane newPane) {
		clearChildren();
		myCurrentContent = newPane;
		addFromCurrentContent();
	}

	private void clearChildren() {
		getChildren().clear();
	}

	private void addFromCurrentContent() {
		getChildren().addAll(myCurrentContent.getChildren());
	}

	private void initializeCurrentContent(EditingPane content) {
		myCurrentContent = content;
		this.getChildren().addAll(myCurrentContent.getChildren());
	}
>>>>>>> 040310bf83d8f9d359743e47048a6fb27acca693

}
