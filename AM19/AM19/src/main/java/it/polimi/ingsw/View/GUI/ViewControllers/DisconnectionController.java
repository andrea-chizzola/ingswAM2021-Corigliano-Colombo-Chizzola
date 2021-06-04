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

    /**
     * creates a new disconnection scene
     * @param playerNickname represents the nickname of the disconnected player
     */
    public DisconnectionController(String playerNickname) {
        setNickname(playerNickname);
    }

    /**
     * displays the nickname of the disconnected player
     * @param nickname represents the nickname of the disconnected player
     */
    private void setNickname(String nickname){
        playerNickname.setText(nickname);
    }
}
