package it.polimi.ingsw.GUI;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class LoginController {

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
            Gui.setRoot("/FXML/loading.fxml");
        }
    }

    private void onTwoPlayersButtonClicked(Event event){
        if(checkUsername()) {
            Gui.setRoot("/FXML/loading.fxml");
        }
    }

    private void onThreePlayersButtonClicked(Event event){
        if(checkUsername()) {
            Gui.setRoot("/FXML/loading.fxml");
        }
    }

    private void onFourPlayersButtonClicked(Event event){
        if(checkUsername()) {
            Gui.setRoot("/FXML/loading.fxml");
        }
    }

    private void onJoinButtonClicked(Event event) {
        if(checkUsername()) {
            Gui.setRoot("/FXML/loading.fxml");
        }
    }

    private void onReconnectButtonClicked(Event event){
        if(checkUsername()) {
            Gui.setRoot("/FXML/loading.fxml");
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
