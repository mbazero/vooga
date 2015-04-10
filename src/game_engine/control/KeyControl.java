package game_engine.control;


import game_engine.IBehavior;

import java.util.*;

import javafx.scene.input.KeyCode;

/**
 * Defines controls and maps them to behaviors
 * @author 
 *
 */
public class KeyControl {

	Map<String, IBehavior> myControlMap;
	Map<String, String> myDesignerMap;
	Map<KeyCode, String> myVirtualKeyboard;

	Map<KeyCode, Map<IBehavior, String[]>> myKeyPressedMap;
	Map<KeyCode, Map<IBehavior, String[]>> myKeyReleasedMap;
	List<KeyCode> myWhilePressedKey;

	public KeyControl(Map<KeyCode, Map<IBehavior, String[]>> keyPressMap, Map<KeyCode, Map<IBehavior, String[]>> keyReleaseMap) {
		myControlMap = new HashMap<>();
		myDesignerMap = new HashMap<>();
		myVirtualKeyboard = new HashMap<>();
		myKeyPressedMap = keyPressMap;
		myKeyReleasedMap = keyReleaseMap;
		myWhilePressedKey = new ArrayList<>();
	}



	public void executeKeyEvent(KeyCode keycode, boolean pressed){
		if(myKeyPressedMap.containsKey(keycode)){
			if(pressed){
				//add error checking later
				myKeyPressedMap.get(keycode).forEach((iBehavior, params) -> iBehavior.execute(params));
				myWhilePressedKey.add(keycode);
			} else {
				myKeyReleasedMap.get(keycode).forEach((iBehavior, params) -> iBehavior.execute(params));
				myWhilePressedKey.remove(keycode);
			}
		}
	}

	/**
	 * method executeBehavior
	 * executes a behavior corresponding to a key
	 * @param keyText the string that maps to the key
	 */
	public void executeBehavior(String keyText) {
		myControlMap.get(myDesignerMap.get(keyText)).execute();
	}
	/**
	 * KeyCode version of executeBehavior
	 * @param keycode
	 */
	public void executeBehavior(KeyCode keycode){
		myControlMap.get(myVirtualKeyboard.get(keycode)).execute();
	}

	//adds a new behavior to the maps
	public void addBehavior(String key, String behaviorName){
		myDesignerMap.put(key, behaviorName);
		myVirtualKeyboard.put(KeyCode.valueOf(key), behaviorName);
		addEntryControlMap(behaviorName, ControlTester.selectBehavior(behaviorName));
	}

	//change the key of a behavior
	public void modifyKey(String oldKey, String newKey){
		if(myDesignerMap.containsKey(oldKey)){
			if(myDesignerMap.containsKey(newKey)){
				System.out.println("New key is in use. Please try another one.");
				return;
			} else {
				myDesignerMap.put(newKey, myDesignerMap.get(oldKey));
				myVirtualKeyboard.put(KeyCode.valueOf(newKey), myVirtualKeyboard.get(kcTranslation(oldKey)));
				addEntryControlMap(newKey, ControlTester.selectBehavior(oldKey));
				myDesignerMap.remove(oldKey);
				myVirtualKeyboard.remove(KeyCode.valueOf(oldKey));
				deleteEntryControlMap(oldKey);
			}
		} else {
			System.out.println("Key modification is aborted");
		}
	}

	private void addEntryControlMap(String key, IBehavior behavior){
		myControlMap.put(key, behavior);
	}

	private void deleteEntryControlMap(String key){
		if(myControlMap.containsKey(key)){
			myControlMap.remove(key);
		} else {
			System.out.println("Nothing to delete in the ControlMap");
		}
	}

	//TODO: throw exception if keycode not defined
	private KeyCode kcTranslation(String index){
		return KeycodeFactory.generateKeyCode(index);
	}

}
