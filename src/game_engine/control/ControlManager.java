package game_engine.control;

import game_engine.*;

import java.util.*;

import javafx.scene.input.*;

public class ControlManager {
	private int myActiveControl;
	private List<KeyControl> myKeyControls;

	public ControlManager(){
		myActiveControl = -1;		
		myKeyControls = new ArrayList<>();
	}

	public void addControl(Map<KeyCode, Map<IBehavior, String[]>> keyPressMap, Map<KeyCode, Map<IBehavior, String[]>> keyReleaseMap){
		KeyControl newControl = new KeyControl(keyPressMap, keyReleaseMap);
		myKeyControls.add(newControl);
		myActiveControl++;
	}

	public IBehavior setActiveControl(String... indexArray){
		int index = Integer.parseInt(indexArray[0]);
		//needs to replace this error checking with something else
		if(myActiveControl < 0 || index > myKeyControls.size()){
			System.out.println("Invalid Active Control Index");
			return null;
		} else {
			IBehavior activeControl = (params) -> {
				int activeIndex = Integer.parseInt(params[0]);
				myActiveControl = activeIndex;
			};
			return activeControl;
		}
	}

	public void handleKeyEvent(KeyCode keycode, boolean pressed){
		if(myActiveControl < 0){
			System.out.println("No Control has been added yet!");
		} else{
			myKeyControls.get(myActiveControl).executeKeyEvent(keycode, pressed);
		}
	}

}