package it.polimi.ingsw.View.GUI.ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class DisconnectionController extends ViewController{

    @FXML
    private Pane pane;

    @FXML
    private Label playerNickname;

    @FXML
    private Label message;

    public DisconnectionController(String playerNickname) {
        setNickname(playerNickname);
    }

    private void setNickname(String nickname){
        playerNickname.setText(nickname);
    }
}
