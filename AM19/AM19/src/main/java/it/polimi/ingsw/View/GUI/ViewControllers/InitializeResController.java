package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import it.polimi.ingsw.View.GUI.Messages.BuildMessage;
import it.polimi.ingsw.View.GUI.Messages.BuildSelectedResources;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * controller of the selection of the resources during the initialization
 */
public class InitializeResController extends ViewController{

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuButton resource1;
    @FXML
    private MenuItem coin1;
    @FXML
    private MenuItem shield1;
    @FXML
    private MenuItem servant1;
    @FXML
    private MenuItem stone1;
    @FXML
    private MenuButton resource2;
    @FXML
    private MenuItem coin2;
    @FXML
    private MenuItem shield2;
    @FXML
    private MenuItem servant2;
    @FXML
    private MenuItem stone2;
    @FXML
    private MenuButton resource3;
    @FXML
    private MenuItem coin3;
    @FXML
    private MenuItem shield3;
    @FXML
    private MenuItem servant3;
    @FXML
    private MenuItem stone3;
    @FXML
    private MenuButton resource4;
    @FXML
    private MenuItem coin4;
    @FXML
    private MenuItem shield4;
    @FXML
    private MenuItem servant4;
    @FXML
    private MenuItem stone4;
    @FXML
    private TextField shelf1;
    @FXML
    private TextField shelf2;
    @FXML
    private TextField shelf3;
    @FXML
    private TextField shelf4;
    @FXML
    private Button select1;
    @FXML
    private Button select2;
    @FXML
    private Button select3;
    @FXML
    private Button select4;
    @FXML
    private Button actionButton;

    private List<Button> buttons;
    private List<MenuButton> resources;
    private List<MenuItem> coins;
    private List<MenuItem> servants;
    private List<MenuItem> shields;
    private List<MenuItem> stones;
    private List<TextField> shelves;

    private Accumulator accumulator;
    private BuildMessage builder;

    /**
     * constructor
     */
    public InitializeResController(){
        this.accumulator = new Accumulator(GUIHandler.getGUIReference().getModelReference());
        this.builder = new BuildSelectedResources();
    }


    @FXML
    public void initialize(){

        coins = List.of(coin1,coin2,coin3,coin4);
        shields = List.of(shield1,shield2,shield3,shield4);
        servants = List.of(servant1,servant2,servant3,servant4);
        stones = List.of(stone1,stone2,stone3,stone4);
        buttons = List.of(select1,select2,select3,select4);
        resources = List.of(resource1,resource2,resource3,resource4);
        shelves = List.of(shelf1,shelf2,shelf3,shelf4);

        setStart();

    }

    /**
     * sets the buttons during the opening of the window
     */
    private void setStart(){

        for (MenuButton res : resources)
            res.setVisible(false);

        for(TextField text:shelves)
            text.setVisible(false);

        for(Button button : buttons)
            button.setVisible(false);

        actionButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> notifyInteraction());

        setItemHandlers(coins,"coin");
        setItemHandlers(shields,"shield");
        setItemHandlers(servants,"servant");
        setItemHandlers(stones,"stone");
        setButtonsHandlers();

    }

    /**
     * notifies the interaction
     */
    private void notifyInteraction() {
        getGUIReference().notifyInteraction(builder.buildMessage(accumulator));
        anchorPane.getScene().getWindow().hide();
    }

    /**
     * sets the handlers of all the MenuItems passed in the List
     * @param list List of MenuItem
     * @param resource the resource associated with the item
     */
    private void setItemHandlers(List<MenuItem> list, String resource){

        list.get(0).setOnAction( event ->{
            accumulator.setInitResources(resource);
            resources.get(0).setDisable(true);});

        list.get(1).setOnAction( event ->{
            accumulator.setInitResources(resource);
            resources.get(1).setDisable(true);});

        list.get(2).setOnAction(event ->{
            accumulator.setInitResources(resource);
            resources.get(2).setDisable(true);});

        list.get(3).setOnAction(event ->{
            accumulator.setInitResources(resource);
            resources.get(3).setDisable(true);});
    }

    /**
     * sets the handlers of the buttons
     */
    private void setButtonsHandlers(){

        select1.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> action(0));
        select2.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> action(1));
        select3.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> action(2));
        select4.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> action(3));

    }

    /**
     * sets the action of the button
     * @param i the number of the button to be set
     */
    private void action(int i){
        accumulator.setInitResourcesShelf(shelves.get(i).getText());
        buttons.get(i).setDisable(true);
        buttons.get(i).setOpacity(0.5);
        resources.get(i).setDisable(false);
    }

    /**
     * this method set the correct number of buttons to manage the resources according to the number passed as parameter
     * @param num the number of resources to be initialized
     */
    public void initResources(int num){
        if(num == 0){
            notifyInteraction();
            return;
        }

        for(int i=0; i<num; i++){
            resources.get(i).setVisible(true);
            resources.get(i).setDisable(true);
            buttons.get(i).setVisible(true);
            shelves.get(i).setVisible(true);
        }
    }
}
