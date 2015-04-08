package authoring.userInterface;

//import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
//import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import authoring.rightPane.RightPane;

/**
 * @author hojeanniechung & Daniel Luker & Andrew Sun
 *
 */
public class AuthoringWindow {

	private Scene myScene;
//	private ButtonFactory mbuttonList;
//	private String mFileSelector = "src/Resources/FilestoParse.xml";

	private static final int FILE_MENU = 0;
	private static final int EDIT_MENU = 1;
	private static final int VIEW_MENU = 2;
	private static final int HELP_MENU = 3;
	
	private static final int NEW_FILE = 0;
	private static final int OPEN_FILE = 1;
	private static final int CLOSE_GAME = 2;
	
	private CenterPane myCenterPane;
	
	public Scene GameCreateUI() {

		VBox root = new VBox();

		BorderPane canvas = new BorderPane();

		myScene = new Scene(root, 1000, 1000, Color.WHITE);

		canvas.setPrefHeight(myScene.getHeight());
		canvas.setPrefWidth(myScene.getWidth());
		// Setting up borderPane
		canvas.setBottom(setupBottomPane(myScene.getWidth()));
		canvas.setTop(setupTopPane(myScene.getWidth()));
		canvas.setLeft(setupLeftPane());
		canvas.setRight(setupRightPane());
		canvas.setCenter(setupCenterPane());

		root.getChildren().add(menuBar());
		root.getChildren().add(canvas);
		// create a place to display the shapes and react to input

		return myScene;
	}

	private MenuBar menuBar() {
		MenuBar mBar = new MenuBar();
		String[] menuItems = { "File:New/Load/Close", "Edit:Copy",
				"View:Sreen Options", "Help:Lol there is no help" };
		
		Arrays.asList(menuItems).forEach(
				str -> {
					String a = str.split(":")[0];
					System.out.println(a);
					Menu m = new Menu(a);
					String s = str.split(":")[1];
					String[] t = s.split("/");
					Arrays.asList(t).forEach(
							str1 -> m.getItems().add(new MenuItem(str1)));
					mBar.getMenus().add(m);
				});
		
		/*
		 * @author Andrew
		 */
		mBar.getMenus().get(FILE_MENU).getItems().get(NEW_FILE).setOnAction(e -> {
			// Refactor this into new class/method
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Create New Game Scene");
			
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			
			grid.add(new Label("xSize"), 0, 0);
			TextField textBox1 = new TextField("400");
			grid.add(textBox1, 0, 1);
			grid.add(new Label("ySize"), 1, 0);
			TextField textBox2 = new TextField("400");
			grid.add(textBox2, 1, 1);
			
			dialog.getDialogPane().setContent(grid);
			dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			
			
			Optional<ButtonType> result = dialog.showAndWait();
			// Refactor? Is it possible to get rid of this if statement?
			if (result.get() == ButtonType.OK){
				// TODO: check to make sure user entered numbers
				
				myCenterPane.createRegion(Double.parseDouble(textBox1.getText()), Double.parseDouble(textBox1.getText()));
			}
		});
		
		mBar.getMenus().get(FILE_MENU).getItems().get(OPEN_FILE).setOnAction(e -> {
			//refactor this into new class
			 FileChooser fileChooser = new FileChooser();
			 fileChooser.setTitle("Open Resource File");
			 fileChooser.getExtensionFilters().addAll(
			         new ExtensionFilter("Text Files", "*.txt"),
			         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
			         new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
			         new ExtensionFilter("All Files", "*.*"));
			 fileChooser.showOpenDialog(null);
		});
		mBar.getMenus().get(FILE_MENU).getItems().get(CLOSE_GAME).setOnAction(e -> Platform.exit());
		return mBar;
	}

	private HBox setupBottomPane(double width) {
		HBox buttonBox = new HBox();
//		for (Button B : mbuttonList.getSharedInstace(mFileSelector, "Button")
//				.generateButtonBoxes()) {
//			buttonBox.getChildren().add(B);
//		}
//		System.out.println(mbuttonList
//				.getSharedInstace(mFileSelector, "Button").GetAttributes()
//				.toString());
		return buttonBox;
	}

	private HBox setupTopPane(double width) {
		Map<String, EventHandler<Event>> mButtons = new HashMap<>();

		mButtons.put("Global Settings", null);
		mButtons.put("Map Settings", null);
		mButtons.put("Interactions List", null);
		mButtons.put("Characters", null);
		mButtons.put("Blocks", null);
		mButtons.put("Decorations", null);
		mButtons.put("UI Controls", null);

		TopPane mTopPane = new TopPane();
		mTopPane.addButtons(mButtons);
		return mTopPane;
	}

	private VBox setupRightPane() {
		return new RightPane(myScene);
	}

	private VBox setupLeftPane() {
		return new LeftPane();
	}

	private Node setupCenterPane() {
		myCenterPane = new CenterPane(myScene);
		return myCenterPane;
	}

}
