package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import it.polimi.ingsw.View.GUI.Messages.BuildMessage;
import it.polimi.ingsw.View.GUI.Messages.BuildSelectedResources;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


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

    public InitializeResController(){
        this.accumulator = new Accumulator();
        this.builder = new BuildSelectedResources();
    }


    public void startInitialization(int num){
        if(num == 0)
            getGUIReference().notifyInteraction(builder.buildMessage(new Accumulator()));
    }

    @FXML
    public void initialize(){

        coins = new ArrayList<>();
        coins.add(coin1);
        coins.add(coin2);
        coins.add(coin3);
        coins.add(coin4);

        shields = new ArrayList<>();
        shields.add(shield1);
        shields.add(shield2);
        shields.add(shield3);
        shields.add(shield4);

        servants = new ArrayList<>();
        servants.add(servant1);
        servants.add(servant2);
        servants.add(servant3);
        servants.add(servant4);

        stones = new ArrayList<>();
        stones.add(stone1);
        stones.add(stone2);
        stones.add(stone3);
        stones.add(stone4);

        shelves = new ArrayList<>();
        shelves.add(shelf1);
        shelves.add(shelf2);
        shelves.add(shelf3);
        shelves.add(shelf4);

        buttons = new ArrayList<>();
        buttons.add(select1);
        buttons.add(select2);
        buttons.add(select3);
        buttons.add(select4);

        resources = new ArrayList<>();
        resources.add(resource1);
        resources.add(resource2);
        resources.add(resource3);
        resources.add(resource4);

        setStart();

    }

    private void setStart(){

        for (MenuButton res : resources)
            res.setVisible(false);

        for(TextField text:shelves)
            text.setVisible(false);

        for(Button button : buttons)
            button.setVisible(false);

        actionButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::sendMessage);

        setItemHandlers(coins,"coin");
        setItemHandlers(shields,"shield");
        setItemHandlers(servants,"servant");
        setItemHandlers(stones,"stone");
        setButtonsHandlers();
    }

    private void sendMessage(Event event) {
        getGUIReference().notifyInteraction(builder.buildMessage(accumulator));
        anchorPane.getScene().getWindow().hide();
    }

    private void setItemHandlers(List<MenuItem> list, String resource){

        list.get(0).setOnAction( event ->{
            accumulator.setInitResources(resource);
            resources.get(0).setDisable(true);
            /*buttons.get(0).setVisible(true);*/});
        list.get(1).setOnAction( event ->{
            accumulator.setInitResources(resource);
            resources.get(1).setDisable(true);
           /* buttons.get(1).setVisible(true);*/});
        list.get(2).setOnAction(event ->{
            accumulator.setInitResources(resource);
            resources.get(2).setDisable(true);
            /*buttons.get(2).setVisible(true);*/});
        list.get(3).setOnAction(event ->{
            accumulator.setInitResources(resource);
            resources.get(3).setDisable(true);
            /*buttons.get(3).setVisible(true);*/});
    }

    private void setButtonsHandlers(){

        select1.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> action(0));
        select2.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> action(1));
        select3.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> action(2));
        select4.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> action(3));

    }

    private void action(int i){
        accumulator.setInitResources(shelves.get(i).getText());
        buttons.get(i).setDisable(true);
        buttons.get(i).setOpacity(0.5);
        resources.get(i).setDisable(false);
    }

    public void initResources(int num){
        for(int i=0; i<num; i++){
            resources.get(i).setVisible(true);
            resources.get(i).setDisable(true);
            buttons.get(i).setVisible(true);
            shelves.get(i).setVisible(true);
        }
    }
}
