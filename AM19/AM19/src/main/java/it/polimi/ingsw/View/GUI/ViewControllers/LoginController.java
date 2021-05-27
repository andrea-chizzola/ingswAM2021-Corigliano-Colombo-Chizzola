package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.GUI.Gui;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.View.GUI.GUI;
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
    private Label invalidNicknameLabel;
    @FXML
    private TextField nicknameField;
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

        singlePlayerButton.setOnAction(this::onSinglePlayerButtonClicked);
        twoPlayersButton.setOnAction(this::onTwoPlayersButtonClicked);
        threePlayersButton.setOnAction(this::onThreePlayersButtonClicked);
        fourPlayersButton.setOnAction(this::onFourPlayersButtonClicked);

        joinButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinButtonClicked);

        reconnectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onReconnectButtonClicked);

    }

    private void onSinglePlayerButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 1);
                getGuiReference().notifyInteraction(message);
            } catch (MalformedMessageException e) {
                e.printStackTrace();
            }
        }
    }

    private void onTwoPlayersButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 2);
                getGuiReference().notifyInteraction(message);
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }

        }
    }

    private void onThreePlayersButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 3);
                getGuiReference().notifyInteraction(message);
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }
        }
    }

    private void onFourPlayersButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), true, 4);
                getGuiReference().notifyInteraction(message);
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }
        }
    }

    private void onJoinButtonClicked(Event event) {
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildConnection("Connection request.", nicknameField.getText(), false, 0);
                getGuiReference().notifyInteraction(message);
            }catch (MalformedMessageException e){
                e.printStackTrace();
            }
        }
    }

    private void onReconnectButtonClicked(Event event){
        if(checkUsername()) {
            try {
                String message = MessageFactory.buildReconnection("Reconnection request.", nicknameField.getText());
                getGuiReference().notifyInteraction(message);
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
