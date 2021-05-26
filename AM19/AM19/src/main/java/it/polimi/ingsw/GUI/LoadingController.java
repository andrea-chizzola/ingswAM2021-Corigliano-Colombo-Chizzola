package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoadingController {

    @FXML
    private Pane pane;

    @FXML
    public void switchToPrimary(ActionEvent actionEvent) throws IOException {
        Gui.setRoot("/FXML/login.fxml");
    }

    @FXML
    public void initialize(){
    }
}
