package authoring.dialogs;

import java.util.List;
import java.util.function.Consumer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;


public abstract class DataDialog extends Dialog<ButtonType> {

    private static final String ADD = "Add";
    private static final int BOTTOM_SPACING = 25;
    private int myIndex;
    private DialogGridOrganizer myGrid;

    void initialize (Sprite s, int sizeOfGridOrganizer, Node[] titleLabelRow) {
        myGrid = new DialogGridOrganizer(sizeOfGridOrganizer);
        myGrid.addRowEnd(titleLabelRow);

        for (int i = 0; i < getListContent().size(); i++) {
            this.setHeight(this.getHeight() + BOTTOM_SPACING);
            addBlankRow(myGrid, myIndex++);
        }

        addAddButton();
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        getDialogPane().setContent(myGrid);
        showBox(s);
    }

    void addAddButton() {
        ButtonType addButton = new ButtonType(ADD);
        getDialogPane().getButtonTypes().add(addButton);
        lookupAddButton(addButton);
    }
    
    void lookupAddButton (ButtonType b) {
        final Button addButton = (Button) this.getDialogPane().lookupButton(b);
        System.out.println("add button: " + addButton);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            this.setHeight(this.getHeight() + BOTTOM_SPACING);
            addBlankRow(myGrid, myIndex++);
            event.consume();
        });
    }

    abstract ObservableList<String> getListContent ();

    abstract void addBlankRow (DialogGridOrganizer grid, int index);

    abstract Consumer<ButtonType> getTodoOnOK ();

    public void showBox (Sprite s) {
        Consumer<ButtonType> todoOnOK = getTodoOnOK();
        this.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(todoOnOK);
    }

    ComboBox<String> addComboBox (List<ComboBox<String>> comboBoxes) {
        ObservableList<String> toAdd = getListContent();
        ComboBox<String> box = new ComboBox<>();
        box.getItems().addAll(toAdd);
        comboBoxes.add(box);
        return box;
    }
    
    TextField addTextField (List<TextField> textFields) {
        TextField result = new TextField();
        textFields.add(result);
        return result;
    }
    
    TextField addTextField (EventHandler<KeyEvent> todoOnKeyPressed, List<TextField> myTextFields) {
        TextField result = new TextField();
        result.setEditable(false);
        result.setOnKeyPressed(todoOnKeyPressed);
        myTextFields.add(result);
        return result;
    }

    void addRow (int index) {
        addBlankRow(myGrid, index);
    }

}