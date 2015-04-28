package game_player;

import game_engine.Level;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.controls.ControlsManager;
import game_engine.sprite.TransitionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


public class VoogaGame implements IActor {
    private List<Level> levels;
    private Level activeLevel;
    private Group root;
    private double width, height;
    private Timeline animation;
    private ControlsManager controlsManager;
    private long lastUpdateTime;
    private TransitionManager transitionManager;
    private Map<String, IAction> myActions;

    public VoogaGame(double fps, double w, double h) {
            levels = new ArrayList<Level>();
            root = new Group();
            width = w;
            height = h;
            animation = new Timeline(fps, getFrame(fps));
            animation.setCycleCount(Timeline.INDEFINITE);
            lastUpdateTime = 0l;
    }

    public void addLevel (Level l) {
        levels.add(l);
    }

    public double getHeight () {
        return height;
    }
	
	private KeyFrame getFrame(double fps) {
		return new KeyFrame(Duration.millis(fps), (frame) -> update(System.currentTimeMillis()));
	}

    private IAction setActiveLevel = (params) -> {
        int index = Integer.parseInt(params[0]);
        setActiveLevel(index);
    };

    public void setActiveLevel (int index) {
        root.getChildren().clear();
        activeLevel = levels.get(index);
        controlsManager = activeLevel.getControlManager();
        activeLevel.start(width, height);
        root.getChildren().add(root);
        transitionManager.playTransitions();
    }
	
	public void setTransitionManager(TransitionManager manager){
	    transitionManager =manager; 
	}
	
	public TransitionManager getTransitionManager(){
	    return transitionManager;
	}

	public void update(long currentTime) {
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentTime;
		}

		activeLevel.update(currentTime - lastUpdateTime);
		lastUpdateTime = currentTime;
	}
	
	protected Group getRoot() {
		return root;
	}
	
	public void start() {
		Stage stage = new Stage();
		Scene scene = new Scene(root, width, height);
		scene.setOnKeyPressed(e -> controlsManager.handleInput(e));
		scene.setOnKeyReleased(e -> controlsManager.handleInput(e));
		stage.setX(Screen.getPrimary().getVisualBounds().getMinX());
		stage.setY(Screen.getPrimary().getVisualBounds().getMinY());
		root.requestFocus();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		stage.setOnCloseRequest(e -> animation.stop());
		animation.play();
	}

    @Override
    public IAction getAction (String name) {
        if (myActions == null) {
            myActions = buildActionMap();
        }
        return myActions.get(name);
    }
}
