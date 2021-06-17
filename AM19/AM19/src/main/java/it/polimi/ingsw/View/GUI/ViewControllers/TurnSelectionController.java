package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.PlayerInteractions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * this class represents the controller of the turn selection scene
 */
public class TurnSelectionController extends ViewController implements HelperWindow{

    @FXML
    private GridPane mainPane;

    @FXML
    private Button TakeResourcesButton;

    @FXML
    private Button ManageLeaderButton;

    @FXML
    private Button BuyCardButton;

    @FXML
    private Button DoProduction;

    @FXML
    private Button SwapButton;

    @FXML
    private Button ExitButton;

    private MarketboardController controller;

    /**
     * this map contains the name of the turns (key),
     * and the buttons associated to them (value)
     */
    private Map<String, Button> turns;

    @FXML
    public void initialize() {

        turns = Map.of("SWAP", SwapButton, "TAKE_RESOURCES", TakeResourcesButton,
                "MANAGE_LEADER", ManageLeaderButton, "BUY_CARD", BuyCardButton, "DO_PRODUCTION", DoProduction,
                "EXIT", ExitButton);
        disableButtons();
        bindActions();
    }

    /**
     * this method is used to bind an action to each button of the scene
     */
    private void bindActions(){

        GUI gui = getGUIReference();
        TakeResourcesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
                disableButtons();
                gui.notifyInteraction(new TakeResourcesInteraction());
                mainPane.getScene().getWindow().hide();});

        ManageLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
                disableButtons();
                gui.notifyInteraction(new ManageLeaderInteraction());
                mainPane.getScene().getWindow().hide();});

        BuyCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
                disableButtons();
                gui.notifyInteraction(new BuyCardInteraction());
                mainPane.getScene().getWindow().hide();});

        DoProduction.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
                disableButtons();
                gui.notifyInteraction(new DoProductionInteraction());
                mainPane.getScene().getWindow().hide();});

        SwapButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
                disableButtons();
                gui.notifyInteraction(new SwapInteraction());
                mainPane.getScene().getWindow().hide();});

        ExitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                gui.notifyInteraction(MessageFactory.buildExit("End of turn selection"));
                mainPane.getScene().getWindow().hide();
            } catch (MalformedMessageException e) {
                e.printStackTrace();
                //TODO error message
            }
        });
    }

    /**
     * this method is used to enable the buttons associated to the available turns
     * (and to disable the other buttons)
     *
     * @param availableTurns is a list that contains the name of the available turns
     */
    public void setAvailableActions(List<String> availableTurns){
        disableButtons();
        for(String s: availableTurns){
            Button b = turns.get(s);
            b.setDisable(false);
        }
    }

    /**
     * this method is used to disable all the buttons in the scene
     */
    private void disableButtons(){
        for(Button b : turns.values()){
            b.setDisable(true);
        }
    }

    /**
     * this method is used to show the helper window
     */
    public void showWindow(){
        ((Stage) mainPane.getScene().getWindow()).show();
    }

    /**
     * this method is used to hide the helper window
     */
    public void hideWindow(){
        mainPane.getScene().getWindow().hide();
    }

}

