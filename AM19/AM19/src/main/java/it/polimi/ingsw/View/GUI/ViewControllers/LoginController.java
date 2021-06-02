package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.View.GUI.GUIHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class LoginController extends ViewController{

    @FXML
    private Pane pane;
    @FXML
    private Label invalidNicknameLabel;
    @FXML
    private TextField nicknameField;
    @FXML
    private MenuItem soloButton;
    @FXML
    private MenuItem singlePlayerButton;
    @FXML
    private MenuItem twoPlayersButton;
    @FXML
    private MenuItem threePlayersButton;
    @FXML
    private MenuItem fourPlayersButton;
    @FXML
    private Button joinButton;
    @FXML
    private Button reconnectButton;


    @FXML
    public void initialize() {

        invalidNicknameLabel.setVisible(false);

        soloButton.setOnAction(this::onSoloButtonClicked);
        singlePlayerButton.setOnAction(this::onSinglePlayerButtonClicked);
        twoPlayersButton.setOnAction(this::onTwoPlayersButtonClicked);
        threePlayersButton.setOnAction(this::onThreePlayersButtonClicked);
        fourPlayersButton.setOnAction(this::onFourPlayersButtonClicked);

        joinButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinButtonClicked);

        reconnectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onReconnectButtonClicked);

    }

    public Scene getScene(){
        return pane.getScene();
    }



    private void onSoloButtonClicked(Event event){
        if(checkUsername()){
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 1);
                getGUIReference().notifyNickname(nicknameField.getText());
                getGUIReference().notifyInteractionSolo(message);
                LoadingController controller = new LoadingController();
                GUIHandler.loadRoot(pane.getScene(), controller, "/FXML/loading.fxml");
            } catch (MalformedMessageException e) {
                e.printStackTrace();
            }
        }
    }


    private void onSinglePlayerButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 1);
                getGUIReference().notifyNickname(nicknameField.getText());
                getGUIReference().notifyInteraction(message);
                LoadingController controller = new LoadingController();
                GUIHandler.loadRoot(pane.getScene(), controller, "/FXML/loading.fxml");
            } catch (MalformedMessageException e) {
                e.printStackTrace();
            }
        }
    }

    private void onTwoPlayersButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 2);
                getGUIReference().notifyNickname(nicknameField.getText());
                getGUIReference().notifyInteraction(message);
                LoadingController controller = new LoadingController();
                GUIHandler.loadRoot(pane.getScene(), controller, "/FXML/loading.fxml");
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }
        }
    }

    private void onThreePlayersButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 3);
                getGUIReference().notifyNickname(nicknameField.getText());
                getGUIReference().notifyInteraction(message);
                LoadingController controller = new LoadingController();
                GUIHandler.loadRoot(pane.getScene(), controller, "/FXML/loading.fxml");
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }
        }
    }

    private void onFourPlayersButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 4);
                getGUIReference().notifyNickname(nicknameField.getText());
                getGUIReference().notifyInteraction(message);
                LoadingController controller = new LoadingController();
                GUIHandler.loadRoot(pane.getScene(), controller, "/FXML/loading.fxml");
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }
        }
    }

    private void onJoinButtonClicked(Event event) {
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), false, 0);
                getGUIReference().notifyNickname(nicknameField.getText());
                getGUIReference().notifyInteraction(message);
                LoadingController controller = new LoadingController();
                GUIHandler.loadRoot(pane.getScene(), controller, "/FXML/loading.fxml");
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }
        }
    }

    private void onReconnectButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildReconnection("Reconnection request.", nicknameField.getText());
                getGUIReference().notifyNickname(nicknameField.getText());
                getGUIReference().notifyInteraction(message);
                LoadingController controller = new LoadingController();
                GUIHandler.loadRoot(pane.getScene(), controller, "/FXML/loading.fxml");
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }
        }
    }

    private boolean checkUsername(){
        if(!nicknameField.getText().isEmpty()){
            return true;
        }else{
            invalidNicknameLabel.setVisible(true);
            return false;
        }
    }

}
