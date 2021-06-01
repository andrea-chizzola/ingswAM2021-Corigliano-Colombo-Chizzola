package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class MarbleSelectionController extends ViewController{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView marble1;

    @FXML
    private ImageView marble2;

    @FXML
    private ImageView marble3;

    @FXML
    private ImageView marble4;

    @FXML
    private CheckBox keep1;

    @FXML
    private CheckBox keep2;

    @FXML
    private CheckBox keep3;

    @FXML
    private CheckBox keep4;

    @FXML
    private Button doneButton;

    @FXML
    private MenuButton targetSlot1;

    @FXML
    private MenuButton targetSlot3;

    @FXML
    private MenuButton targetSlot4;

    @FXML
    private MenuButton targetSlot2;

    @FXML
    private MenuButton transformations1;

    @FXML
    private MenuButton transformations2;

    @FXML
    private MenuButton transformations3;

    @FXML
    private MenuButton transformations4;

    private List<ImageView> marbles;
    private List<MenuButton> transformationMenus;
    private List<MenuButton> targetMenus;
    private List<CheckBox> keepCheckBoxes;
    private List<Marble> selected;
    private int nModifications;
    private final String path = "/Images/market/";
    private Accumulator accumulator;

    public MarbleSelectionController(){
        accumulator = new Accumulator();
    }

    @FXML
    public void initialize(){
        marbles = List.of(marble1, marble2, marble3, marble4);
        transformationMenus = List.of(transformations1, transformations2, transformations3, transformations4);
        hideTransformations();
        keepCheckBoxes = List.of(keep1, keep2, keep3, keep4);
        hideKeepCheckboxes();
        targetMenus = List.of(targetSlot1, targetSlot2, targetSlot3, targetSlot4);
        keep1.selectedProperty().addListener((observable, oldValue, newValue) -> targetSlot1.setVisible(newValue));
        keep2.selectedProperty().addListener((observable, oldValue, newValue) -> targetSlot2.setVisible(newValue));
        keep3.selectedProperty().addListener((observable, oldValue, newValue) -> targetSlot3.setVisible(newValue));
        keep4.selectedProperty().addListener((observable, oldValue, newValue) -> targetSlot4.setVisible(newValue));
        doneButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> exitFromWindow());
    }

    public void exitFromWindow(){
        List<Marble> selected = getModelReference().getSelectedMarbles();
        String action, slots, marble;

        for(int i=0; i<selected.size(); i++){

            action = (keepCheckBoxes.get(i).isSelected())?"INSERT":"DISCARD";

            //TODO laciare un errore se il player non ha selezionato lo slot. Non inviare discard
            if(action.equals("DISCARD") || targetMenus.get(i).getText().equals("Target Slot"))
                slots = "0";
            else slots = targetMenus.get(i).getText().substring(6,7);

            if(selected.get(i).isWhite())
                marble = (action.equals("DISCARD")||nModifications==0)?"MarbleWhite":transformationMenus.get(i).getText();
            else marble = selected.get(i).toString();

            accumulator.setMarblesActions(marble);
            accumulator.setMarblesActions(action);
            accumulator.setMarblesActions(slots);
        }

        getGUIReference().notifyInteraction(accumulator.buildActionMarble());
        ((Stage) mainPane.getScene().getWindow()).close();
    }

    private void hideTransformations(){
        for(MenuButton menu : transformationMenus){
            menu.setVisible(false);
        }
    }

    private void hideKeepCheckboxes(){
        for(CheckBox box : keepCheckBoxes){
            box.setVisible(false);
        }
    }

    public void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, int nSlots) {
        selected = new LinkedList<>(marblesTray);
        nModifications = whiteModifications.size();
        addTargetMenuOptions(nSlots);

        for(int i=0; i<marblesTray.size(); i++){
            String imageName = marblesTray.get(i).getImage();
            Image image = new Image(MarbleSelectionController.class.getResourceAsStream(path + imageName));
            marbles.get(i).setImage(image);
            keepCheckBoxes.get(i).setVisible(true);
            if(marblesTray.get(i).isWhite() && whiteModifications.size()>0)
                showWhiteTransformations(whiteModifications, transformationMenus.get(i), keepCheckBoxes.get(i));

        }
    }

    private void addTargetMenuOptions(int nSlots){
        for (MenuButton menu : targetMenus) {
            for (int j = 0; j < nSlots; j++) {
                MenuItem item = new MenuItem("Shelf " + (j + 1));
                menu.getItems().add(item);
                item.setOnAction(e -> menu.setText(item.getText()));
            }
        }
    }

    private void showWhiteTransformations(List<Marble> whiteModifications, MenuButton menu, CheckBox checkBox){
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> menu.setVisible(newValue));
        for (Marble whiteModification : whiteModifications) {
            MenuItem item = new MenuItem(whiteModification.toString());
            menu.getItems().add(item);
            item.setOnAction(e -> menu.setText(item.getText()));
        }
    }
}
