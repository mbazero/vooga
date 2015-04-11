package game_engine.controls;

import game_engine.Behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.KeyCode;

public class ControlsMap {

    private Map<KeyCode, Map<Behavior, String[]>> controls;
    
    public ControlsMap() {
	controls = new HashMap<>();
    }
    
    public void executeBehaviors(KeyCode code) {
	if (controls.containsKey(code)) {
	    controls.get(code).forEach((behavior, params) -> behavior.execute(params));
	}
    }
    
    public void addBehavior(KeyCode code, Behavior behavior, String[] params) {
	Map<Behavior, String[]> keyBehaviors;
	
	if (controls.containsKey(code)) {
	    keyBehaviors = controls.get(code);
	}
	else {
	    keyBehaviors = new HashMap<>();
	}
	
	keyBehaviors.put(behavior, params);
    }
    
    public void handleInput(KeyCode input, List<KeyCode> pressedKeys) {
	// Empty, sub-classes override for their own functionality
    }
    
    public void update(double time, List<KeyCode> pressedKeys) {
	// TODO: implement this
    }
}