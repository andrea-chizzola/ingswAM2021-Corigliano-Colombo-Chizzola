package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;


import java.io.IOException;

public class LoginController {

    @FXML
    private Label wrongNicknameLabel;
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
    public void joinButtonHandler(ActionEvent actionEvent) throws IOException {

        Gui.setRoot("/FXML/loading.fxml");

    }
}
