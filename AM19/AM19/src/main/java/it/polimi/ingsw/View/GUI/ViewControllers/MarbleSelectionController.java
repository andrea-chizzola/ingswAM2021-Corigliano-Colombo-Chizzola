package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private MenuItem menu1Shelf1;

    @FXML
    private MenuItem menu1Shelf2;

    @FXML
    private MenuItem menu1Shelf3;

    @FXML
    private MenuButton targetSlot3;

    @FXML
    private MenuItem menu3Shelf1;

    @FXML
    private MenuItem menu3Shelf2;

    @FXML
    private MenuItem menu3Shelf3;

    @FXML
    private MenuButton targetSlot4;

    @FXML
    private MenuItem menu4Shelf1;

    @FXML
    private MenuItem menu4Shelf2;

    @FXML
    private MenuItem menu4Shelf3;

    @FXML
    private MenuButton targetSlot2;

    @FXML
    private MenuItem menu2Shelf1;

    @FXML
    private MenuItem menu2Shelf2;

    @FXML
    private MenuItem menu2Shelf3;

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
    private final String path = "/Images/market/";
    private Accumulator accumulator;
    int colored;
    int whites;

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
        setTargetMenu(targetSlot1, menu1Shelf1, menu1Shelf2, menu1Shelf3);
        setTargetMenu(targetSlot2, menu2Shelf1, menu2Shelf2, menu2Shelf3);
        setTargetMenu(targetSlot3, menu3Shelf1, menu3Shelf2, menu3Shelf3);
        setTargetMenu(targetSlot4, menu4Shelf1, menu4Shelf2, menu4Shelf3);
        doneButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> exitFromWindow());
    }

    public void exitFromWindow(){
        List<Marble> selected = getModelReference().getSelectedMarbles();
        int c=0, k=0;
        for(int i=0; i<selected.size(); i++){
            String action, slots, marble;
            if(selected.get(i).toString().equals("MarbleWhite")){
                action = (keepCheckBoxes.get(colored + c ).isSelected())?"INSERT":"DISCARD";
                slots = (action.equals("DISCARD"))?"0":targetMenus.get(colored + c).getText().substring(6,7);
                marble = (action.equals("DISCARD"))?"MarbleWhite":transformationMenus.get(colored + c).getText();
                c++;
            }
            else{
                action = (keepCheckBoxes.get(k).isSelected())?"INSERT":"DISCARD";
                slots = (action.equals("DISCARD"))?"0":targetMenus.get(k).getText().substring(6,7);
                marble = selected.get(i).toString();
                k++;
            }
            accumulator.setMarblesActions(marble);
            accumulator.setMarblesActions(action);
            accumulator.setMarblesActions(slots);
        }
        System.out.println(accumulator.buildActionMarble());
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
    private void setTargetMenu(MenuButton targetSlot, MenuItem menuShelf1, MenuItem menuShelf2, MenuItem menuShelf3) {
        targetSlot.setVisible(false);
        menuShelf1.setOnAction(e -> targetSlot.setText(menuShelf1.getText()));
        menuShelf2.setOnAction(e -> targetSlot.setText(menuShelf2.getText()));
        menuShelf3.setOnAction(e -> targetSlot.setText(menuShelf3.getText()));
    }
    //TODO qui non devono arrivare biglie bianche
    //whiteModifications sono parole
    public void showMarblesUpdate(List<String> marblesTray, List<String> whiteModifications, int whites){
        colored = marblesTray.size();
        this.whites = whites;
        for(int i=0; i<marblesTray.size(); i++){
            Image image = new Image(MarbleSelectionController.class.getResourceAsStream(path + marblesTray.get(i)));
            marbles.get(i).setImage(image);
            keepCheckBoxes.get(i).setVisible(true);
        }
        Image marbleWhite = new Image(MarbleSelectionController.class.getResourceAsStream(path + "MarbleWhite.PNG"));
        int whitesStart = marblesTray.size();
        for(int j=whitesStart; j< whitesStart + whites; j++){
            marbles.get(j).setImage(marbleWhite);
            showWhiteTransformations(whiteModifications, transformationMenus.get(j), keepCheckBoxes.get(j));
            keepCheckBoxes.get(j).setVisible(true);
        }
    }

    private void showWhiteTransformations(List<String> whiteModifications, MenuButton menu, CheckBox checkBox){
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> menu.setVisible(newValue));
        for(int i=0; i<whiteModifications.size(); i++){
            MenuItem item = new MenuItem(whiteModifications.get(i));
            menu.getItems().add(item);
            item.setOnAction(e -> menu.setText(item.getText()));
        }
    }
}
