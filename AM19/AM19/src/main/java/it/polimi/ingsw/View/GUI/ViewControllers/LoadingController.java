package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.View.GUI.GUI;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class LoadingController extends ViewController{

    @FXML
    private Pane pane;
    @FXML
    private Button exitButton;

    @FXML
    public void initialize(){
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitButtonClicked);
    }

    private void onExitButtonClicked(Event event) {
        getGuiReference().stop();
    }

}
