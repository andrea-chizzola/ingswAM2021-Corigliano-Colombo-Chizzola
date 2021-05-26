package it.polimi.ingsw.GUI;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class LoadingController {

    @FXML
    private Button exitButton;

    @FXML
    public void initialize(){
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitButtonClicked);
    }

    private void onExitButtonClicked(Event event) {
        Gui.setRoot("/FXML/login.fxml");
    }

}
