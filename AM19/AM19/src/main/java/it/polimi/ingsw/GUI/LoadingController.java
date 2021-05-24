package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoadingController {

    @FXML
    public void switchToPrimary(ActionEvent actionEvent) throws IOException {
        Gui.setRoot("/FXML/login.fxml");
    }
}
