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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TurnSelectionController extends ViewController{


    private Map<String, Button> turns;
    private List<String> availableTurns = new LinkedList<>();

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

    @FXML
    public void initialize() {

        turns = Map.of("SWAP", SwapButton, "TAKE_RESOURCES", TakeResourcesButton,
                "MANAGE_LEADER", ManageLeaderButton, "BUY_CARD", BuyCardButton, "DO_PRODUCTION", DoProduction,
                "EXIT", ExitButton);
        disableButtons();
        for(String s: availableTurns){
            Button b = turns.get(s);
            b.setDisable(false);
        }

        /*controller = new MarketboardController();
        GUIHandler.createHelperWindow(controller, "/FXML/marketboard.fxml");*/
        bindActions();
    }


    private void bindActions(){

        GUI gui = getGUIReference();
        TakeResourcesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                gui.notifyInteraction(new TakeResourcesInteraction()));

        ManageLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                gui.notifyInteraction(new ManageLeaderInteraction()));

        BuyCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                gui.notifyInteraction(new BuyCardInteraction()));

        DoProduction.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                gui.notifyInteraction(new DoProductionInteraction()));

        SwapButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                gui.notifyInteraction(new SwapInteraction()));

        ExitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                gui.notifyInteraction(MessageFactory.buildExit("End of turn selection"));
            } catch (MalformedMessageException e) {
                e.printStackTrace();
                //TODO exit from client
            }
        });
    }

    public void setAvailableActions(List<String> availableTurns){
        this.availableTurns = new LinkedList<>(availableTurns);
    }

    private void disableButtons(){
        for(Button b : turns.values()){
            b.setDisable(true);
        }
    }
}

