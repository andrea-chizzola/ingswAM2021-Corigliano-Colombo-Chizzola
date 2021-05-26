package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.Client.InteractionObserver;
import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.PlayerInteractions.BuyCardInteraction;
import it.polimi.ingsw.View.PlayerInteractions.DoProductionInteraction;
import it.polimi.ingsw.View.PlayerInteractions.ManageLeaderInteraction;
import it.polimi.ingsw.View.PlayerInteractions.SwapInteraction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class TurnSelectionController {

    @FXML
    private GridPane mainPane;

    @FXML
    private AnchorPane pane;

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

    @FXML
    public void initialize() {
        //ExitButton.setDisable(true);
        //SwapButton.setVisible(false);
        bindAction();
    }

    private void bindAction(){
        GUIHandler handler = GUIHandler.instance();
        InteractionObserver interactionObserver = handler.getInteractionObserver();

        /*ManageLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> interactionObserver.notifyInteraction(new ManageLeaderInteraction()));
        BuyCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> interactionObserver.notifyInteraction(new BuyCardInteraction()));
        DoProduction.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> interactionObserver.notifyInteraction(new DoProductionInteraction()));
        SwapButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> interactionObserver.notifyInteraction(new SwapInteraction()));
        ExitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("EXIT"));*/

        /*TakeResourcesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> GUIHandler.loadRoot(mainPane.getScene(), "/FXML/prova.fxml"));
        ManageLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("ManageLeader"));
        BuyCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("BuyCardButton"));
        DoProduction.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("DoProduction"));
        SwapButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("SwapButton"));
        ExitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("ExitButton"));*/
    }

    public void setAvailableActions(){
        //metto a disabled tutti i bottoni e riattivo quelli presenti
    }
}

