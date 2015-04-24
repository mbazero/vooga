package SocialCenter;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginScreen {
	private static final int TEMPWIDTH = 800;
	private static final int TEMPHEIGHT = 600;

	private Scene loginScreen;
	private StackPane root = new StackPane();
	private Driver db=new Driver();
	private String LoginID;
	private SocialCenterMenu menu=new SocialCenterMenu();
	private Stage stage;

	public LoginScreen() {
		initialize();
		createForm();
		createTitle();
	}

	private void initialize() {
		loginScreen = new Scene(root, TEMPWIDTH, TEMPHEIGHT);
		loginScreen.getStylesheets().add("styles/login.css");
		loginScreen.getStylesheets().add("http://fonts.googleapis.com/css?family=Exo:100,200,400");

		StackPane background = new StackPane();
		background.setId("pane");
		Rectangle r = makeSmoke(loginScreen);
		background.setEffect(new GaussianBlur(6));
		background.getChildren().add(r);
		root.getChildren().add(background);

	}

	private void createForm() {
		VBox form = new VBox(5);
		TextField loginBox = new TextField();
		TextField password = new TextField();
		Button submit = new Button("SUBMIT");
		loginBox.getStyleClass().add("login");
		password.getStyleClass().add("login");
		submit.getStyleClass().add("submit");
		submit.setMinWidth(300);
		loginBox.setText("Login");
		password.setText("Password");
		form.getChildren().addAll(loginBox, password, submit);
		form.setTranslateX(150);
		form.setLayoutY(loginScreen.getHeight() / 2);
		form.setMaxSize(300, 20);
		submit.setOnMouseClicked(e->checkValid(loginBox.getText(), password.getText()));

		root.getChildren().add(form);

	}

	private void createTitle() {
		VBox TitleBox = new VBox();
		Label Title = new Label("High $crollers");
		Title.getStyleClass().add("title");
		TitleBox.getChildren().add(Title);
		TitleBox.setTranslateX(-100
				);
		TitleBox.setLayoutY(loginScreen.getHeight() / 2);
		TitleBox.setMaxSize(300, 20);
		root.getChildren().add(TitleBox);
	}

	private Rectangle makeSmoke(Scene s) {
		return new javafx.scene.shape.Rectangle(s.getWidth(), s.getHeight(),
				Color.WHITESMOKE.deriveColor(0, 1, 1, 0.20));
	}

	private void checkValid(String id, String password){
		try {
			ArrayList<String> results=db.get("SELECT Login_id,Login_pass FROM Login WHERE Login_id = '"+id+"' AND Login_pass='"+password+"'");
			LoginID=id;
			if(!results.contains("none")){
				stage.setScene(menu.returnScene());
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getID(){
		return LoginID;
	}

	public void getLoginScreen(Stage s) {
		stage=s;
		s.setScene(loginScreen);
	}

}
