package utilities.SocialCenter;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SocialCenterMenu {

	private HexPage hexPage;
	private Scene menu;
	private StackPane root = new StackPane();
	private Group hexGroup = new Group();
	private double WIDTH;
	private double HEIGHT;
	// private ArrayList<HexTile> list;
	private Stage myStage;
	private String ID;
	private ProfilePage pp;

	public SocialCenterMenu(String id, double width, double height) {
		ID = id;
		WIDTH = width;
		HEIGHT = height;
		initializeHexPage();
		root.getStyleClass().add("background");
		menu.getStylesheets().add("styles/social_menu.css");
	}

	private void initializeHexPage() {
		menu = new Scene(root, WIDTH, HEIGHT);
		hexPage = new HexPage(WIDTH / 2, HEIGHT / 2, 90, 20);
		// list = hex.getList();
		hexPage.addGroup(hexGroup);
		hexPage.addTag("hex");

		for (int i = 0; i < 7; i++) {
			int temp = i;
			String id = "hex" + temp;
			hexPage.getPosition(i).getHexagon().getStyleClass().add(id);

			hexPage.getPosition(i).setOnMouseEnter(e -> handleHoverEnter(temp));
			hexPage.getPosition(i).setOnMouseExit(e -> handleHoverExit(temp));
<<<<<<< HEAD

		}
=======
		}
		hexPage.getPosition(0).setOnMouseClicked(e -> goProfilePage());
>>>>>>> ff8b21766adcaf5ded51491c73efab36f59a78f9

		Image profilePic = new Image(
				"http://media.philly.com/images/20080510_kutcher_300.jpg");
		hexPage.getPosition(0).getHexagon()
				.setFill(new ImagePattern(profilePic));

		root.getChildren().add(hexGroup);
	}

	private void handleHoverExit(int i) {
		hexPage.getPosition(i).getHexagon().getStyleClass()
				.removeAll("hexHover");
	}

	private void handleHoverEnter(int i) {
		hexPage.getPosition(i).getHexagon().getStyleClass().add("hexHover");
<<<<<<< HEAD
		RotateTransition rt = new RotateTransition(Duration.millis(50), hexPage
				.getPosition(i).getHexagon());
		rt.setFromAngle(0);
		rt.setByAngle(3);
		rt.setCycleCount(7);
		rt.setAutoReverse(true);
		rt.play();
=======
>>>>>>> ff8b21766adcaf5ded51491c73efab36f59a78f9
	}

	private void goProfilePage() {
		pp = new ProfilePage(ID, WIDTH, HEIGHT);
		pp.getProfileScreen(myStage);
	}

	public void returnScene(Stage s) {
		myStage = s;
		s.setScene(menu);
	}

}