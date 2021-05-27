package it.polimi.ingsw.View.GUI.ViewControllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class GameBoardController extends ViewController{

    private class Coordinates{

        private double x;
        private double y;

        public Coordinates(double x, double y){
            this.x = x;
            this.y = y;
        }

        public double getX(){
            return x;
        }

        public double getY() {
            return y;
        }
    }

    @FXML
    private ImageView firstSlot;
    @FXML
    private ImageView secondSlot;
    @FXML
    private ImageView thirdSlot;
    @FXML
    private ImageView firstLeaderCard;
    @FXML
    private ImageView secondLeaderCard;
    @FXML
    private ImageView thirdLeaderCard;
    @FXML
    private ImageView fourthLeaderCard;
    @FXML
    private ImageView firstPopeFavor;
    @FXML
    private ImageView secondPopeFavor;
    @FXML
    private ImageView thirdPopeFavor;
    @FXML
    private ImageView topResource;
    @FXML
    private ImageView firstMidResource;
    @FXML
    private ImageView secondMidResource;
    @FXML
    private ImageView firstBottomResource;
    @FXML
    private ImageView secondBottomResource;
    @FXML
    private ImageView thirdBottomResource;
    @FXML
    private Button menuButton;
    @FXML
    private Button marketBoardButton;
    @FXML
    private Button otherPlayersButton;
    @FXML
    private Button quitButton;
    @FXML
    private Circle tile;
    @FXML
    private Label coinsNumber;
    @FXML
    private Label shieldsNumber;
    @FXML
    private Label stonesNumber;
    @FXML
    private Label servantsNumber;

    private List<Coordinates> positions;

    private int currentPos;

    @FXML
    private void initialize(){

        positions = new ArrayList<>();
        setPositions(positions);
        currentPos = 0;

    }

    /**
     * changes the position of the player inside his faith track
     * @param position represents the new player's position
     */
    private void changePosition(int position){

        tile.setLayoutX(positions.get(position).getX());
        tile.setLayoutY(positions.get(position).getY());

    }

    /**
     * sets the positions related to each tile of the faith track
     * @param positions contains the coordinates of each tile
     */
    private void setPositions(List<Coordinates> positions){

        positions.add(new Coordinates(54, 113));
        positions.add(new Coordinates(95, 113));
        positions.add(new Coordinates(135, 113));
        positions.add(new Coordinates(135, 78));
        positions.add(new Coordinates(135, 45));
        positions.add(new Coordinates(183, 45));
        positions.add(new Coordinates(220, 45));
        positions.add(new Coordinates(265, 45));
        positions.add(new Coordinates(305, 45));
        positions.add(new Coordinates(350,45));
        positions.add(new Coordinates(350, 78));
        positions.add(new Coordinates(350, 113));
        positions.add(new Coordinates(392, 113));
        positions.add(new Coordinates(435, 113));
        positions.add(new Coordinates(481, 113));
        positions.add(new Coordinates(520, 113));
        positions.add(new Coordinates(563, 113));
        positions.add(new Coordinates(563, 78));
        positions.add(new Coordinates(563, 45));
        positions.add(new Coordinates(603, 45));
        positions.add(new Coordinates(648, 45));
        positions.add(new Coordinates(690, 45));
        positions.add(new Coordinates(734, 45));
        positions.add(new Coordinates(779, 45));
        positions.add(new Coordinates(817, 45));

    }

}
