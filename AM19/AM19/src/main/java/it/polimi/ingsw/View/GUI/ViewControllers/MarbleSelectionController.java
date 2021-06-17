package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.InteractionTranslator.InteractionTranslator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

/**
 * this class represents a controller of a marble selection scene
 */
public class MarbleSelectionController extends ViewController {

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

    /**
     * this attribute is a list that contains the ImageView of the marbles
     */
    private List<ImageView> marbles;

    /**
     * this attribute is a list that contains reference to the menus
     * used to select the transformation of a white marble
     */
    private List<MenuButton> transformationMenus;

    /**
     * this attribute is a list that contains reference
     * to the menus used to select the target slots
     */
    private List<MenuButton> targetMenus;

    /**
     * this attribute is a list of the checkbox used
     * to decide whether a marble is going to be kept
     */
    private List<CheckBox> keepCheckBoxes;

    /**
     * this attribute represents the marbles selected by the player
     */
    private List<Marble> selected;

    /**
     * this attribute represents represents the number of available transformations for a white marble
     */
    private int nModifications;

    /**
     * this attribute contains the path of the images of the market
     */
    private final String path = "/Images/market/";

    /**
     * this attribute is a reference to an object of type accumulator
     */
    private InteractionTranslator interactionTranslator;


    public MarbleSelectionController(){
        interactionTranslator = new InteractionTranslator(GUIHandler.getGUIReference().getModelReference());
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

    /**
     * this method is used to notify the decision of the player to the current GUI,
     * and to close the MarbleSelection window
     */
    public void exitFromWindow(){
        List<Marble> selected = getModelReference().getSelectedMarbles();
        String action, slots, marble;

        for(int i=0; i<selected.size(); i++){

            action = (keepCheckBoxes.get(i).isSelected())?"INSERT":"DISCARD";

            if(action.equals("DISCARD") || targetMenus.get(i).getText().equals("Target Slot"))
                slots = "0";
            else slots = targetMenus.get(i).getText().substring(6,7);

            if(selected.get(i).isWhite())
                marble = (action.equals("DISCARD")||nModifications==0)?"MarbleWhite":transformationMenus.get(i).getText();
            else marble = selected.get(i).toString();

            interactionTranslator.setMarblesActions(marble);
            interactionTranslator.setMarblesActions(action);
            interactionTranslator.setMarblesActions(slots);
        }

        getGUIReference().notifyInteraction(interactionTranslator.buildActionMarble());
        ((Stage) mainPane.getScene().getWindow()).close();
    }

    /**
     * this method is used to hide the transformations of the white marbles
     */
    private void hideTransformations(){
        for(MenuButton menu : transformationMenus){
            menu.setVisible(false);
        }
    }

    /**
     * this method is used to hide the keep checkboxes
     */
    private void hideKeepCheckboxes(){
        for(CheckBox box : keepCheckBoxes){
            box.setVisible(false);
        }
    }

    /**
     * this method is used to show the player the selected marbles
     * @param marblesTray is a list that represents the selected marbles
     * @param whiteModifications is a list that represents the available white transformations
     * @param nSlots is an int that represents the number of available slots in the warehouse
     */
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

    /**
     * this method is used to add the MenuItem representing the available shelves
     * to each target menu
     * @param nSlots is the number of available slots in the warehouse
     */
    private void addTargetMenuOptions(int nSlots){
        for (MenuButton menu : targetMenus) {
            for (int j = 0; j < nSlots; j++) {
                MenuItem item = new MenuItem("Shelf " + (j + 1));
                menu.getItems().add(item);
                item.setOnAction(e -> menu.setText(item.getText()));
            }
        }
    }

    /**
     * this method is used to show the available transformations of the white marbles to the player
     * @param whiteModifications is a list representing the available white transformations
     * @param menu is the menu button that will show the available transformations
     * @param checkBox is the checkbox associated to the menu
     */
    private void showWhiteTransformations(List<Marble> whiteModifications, MenuButton menu, CheckBox checkBox){
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> menu.setVisible(newValue));
        for (Marble whiteModification : whiteModifications) {
            MenuItem item = new MenuItem(whiteModification.toString());
            menu.getItems().add(item);
            item.setOnAction(e -> menu.setText(item.getText()));
        }
    }

    /**
     * this method is used to disable the "done" button of the scene
     */
    public void disableInteraction(){
        doneButton.setVisible(false);
    }
}
