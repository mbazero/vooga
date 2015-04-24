package authoring.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import authoring.dataEditors.Sprite;
import authoring.panes.rightPane.ObjectivePane;
import authoring.userInterface.DialogGridOrganizer;

/**
 * 
 * @author Daniel
 *
 */
public class ObjectiveDialog extends Dialog<ButtonType> {

	private static final int BOTTOM_SPACING = 25;

	private List<ComboBox<String>> mActions = new ArrayList<>();
	private List<ComboBox<String>> mPrereqs = new ArrayList<>();
	private List<ComboBox<String>> mSprites = new ArrayList<>();
	private List<ComboBox<String>> mStates = new ArrayList<>();

	private ObjectivePane myParent;
	private int myIndex;
	private int selected;

	public ObjectiveDialog(ObjectivePane parent, int objectiveNumber) {
		myParent = parent;

		DialogGridOrganizer grid = new DialogGridOrganizer(4);
		grid.addRowEnd(new Label("Complete/Failed"), new Label("Sprite"),
				new Label("Action"), new Label("Pre-requisites"));

		grid.addRowEnd(addStatesBox(), addSpritesBox(0), addActionsBox(),
				addPrereqsBox());
		this.getDialogPane().setContent(grid);
		ButtonType b = new ButtonType("Add");
		this.getDialogPane().getButtonTypes()
				.addAll(b, ButtonType.OK, ButtonType.CANCEL);

		final Button addButton = (Button) this.getDialogPane().lookupButton(b);
		addButton.addEventFilter(ActionEvent.ACTION, event -> {
			this.setHeight(this.getHeight() + BOTTOM_SPACING);
			grid.addRowEnd(addStatesBox(), addSpritesBox(mSprites.size()), addActionsBox(),
					addPrereqsBox());
			event.consume();
		});
		this.setTitle(String.format("Objective %d Behaviours", myIndex = objectiveNumber));
		showBox();
	}

	public void showBox() {
		this.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(
						response -> {
							myParent.getMyParent().getParent().getCenterPane()
									.getActiveTab()
									.addObjective(myIndex, collectBehaviours());
						});
	}

	private Map<String, List<String>> collectBehaviours() {
		Map<String, List<String>> mResult = new HashMap<>();
		mResult.put("onComplete", new ArrayList<String>());
		mResult.put("onFailed", new ArrayList<String>());
		for (int i = 0; i < mActions.size(); i++) {
			String action = String.format("%s:%s:(prereq)%s", mSprites.get(i)
					.getSelectionModel().getSelectedItem(), mActions.get(i)
					.getSelectionModel().getSelectedItem(), mPrereqs.get(i)
					.getSelectionModel().getSelectedItem());
			mResult.get(mStates.get(i).getSelectionModel().getSelectedItem())
					.add(action);
		}
		return mResult;
	}

	public ComboBox<String> addActionsBox() {
		ComboBox<String> b = addComboBox("win", "lose", "die");
		mActions.add(b);
		return b;
	}

	public ComboBox<String> addSpritesBox(int index) {
		ComboBox<String> b = addComboBox("Main player", "other");
		mSprites.add(b);
		b.valueProperty().addListener((ov, t, t1) -> {
			if(t1.equals("other")){
				this.selected = index;
				this.myParent.getMyParent().getParent().setSpriteWaiting(true);
				this.close();
			}
		});
		return b;
	}

	public ComboBox<String> addStatesBox() {
		ComboBox<String> b = addComboBox("onComplete", "onFailed");
		b.getSelectionModel().select(0);
		mStates.add(b);
		return b;
	}

	public ComboBox<String> addPrereqsBox() {
		ComboBox<String> b = addComboBox(myParent.getObjectives().stream().map(e -> {
			return e.getText();
		}).collect(Collectors.toList()));
		mPrereqs.add(b);
		return b;
	}

	public void setSprite(Sprite s) {
		mSprites.get(selected).getItems().add(s.toString());
	}
	
	private ComboBox<String> addComboBox(Collection<String> elements) {
		ComboBox<String> result = new ComboBox<>();
		result.getItems().addAll(elements);
		return result;
	}

	private ComboBox<String> addComboBox(String... elements) {
		return addComboBox(Arrays.asList(elements));
	}
}