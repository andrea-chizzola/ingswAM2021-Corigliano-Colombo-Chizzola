package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Client.ReducedModel.ReducedBoard;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;

import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.GUI.GUIHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OtherBoardsController extends BoardUpdate{

    @FXML
    private Pane pane;

    @FXML
    private Label nicknameLabel;

    @FXML
    private void initialize(){

        //nicknameLabel.setText(nickname);

        warehouse = new ArrayList<>();
        warehouse2 = new ArrayList<>();
        warehouse3 = new ArrayList<>();
        warehouse2.add(firstMidResource);
        warehouse2.add(secondMidResource);
        warehouse3.add(firstBottomResource);
        warehouse3.add(secondBottomResource);
        warehouse3.add(thirdBottomResource);
        setWarehouse(warehouse);

        developmentCards = new ArrayList<>();
        setDevelopmentCards(developmentCards);

        leaderCards = new ArrayList<>();
        setLeaderCards(leaderCards);

        popeFavors = new ArrayList<>();
        setPopeFavors(popeFavors);

        positions = new ArrayList<>();
        setPositions(positions);

    }

    public void showWindow(){
        ((Stage) pane.getScene().getWindow()).show();
    }

    public void hideWindow(){
        pane.getScene().getWindow().hide();
    }
}
