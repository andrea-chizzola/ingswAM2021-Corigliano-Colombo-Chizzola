package it.polimi.ingsw.View.GUI.ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NotificationController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button closeButton;

    @FXML
    private Text NotificationType;

    @FXML
    private Text NotificationContent;

    @FXML
    private ImageView notificationImage;

    private boolean isOK;
    private String message;

    NotificationController(boolean isOK, String message){
        this.isOK = isOK;
        this.message = message;
    }

    @FXML
    public void initialize(){
        if(isOK) NotificationType.setText("Message:");
        else NotificationType.setText("Error:");
        NotificationContent.setText(message);

        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> closeWindow());
    }

    private void closeWindow(){
        ((Stage) mainPane.getScene().getWindow()).close();
    }

}
