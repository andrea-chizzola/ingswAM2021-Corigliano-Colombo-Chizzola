package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.MessageFactory;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    private Pane pane;
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

    private List<ImageView> warehouse;

    private List<ImageView> developmentCards;

    private List<ImageView> leaderCards;

    private List<ImageView> popeFavors;

    private int currentPos;

    @FXML
    private void initialize(){

        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMenuClicked);
        marketBoardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMarketBoardClicked);
        otherPlayersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onOtherPlayersClicked);
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onQuitClicked);

        warehouse = new ArrayList<>();
        setWarehouse(warehouse);

        developmentCards = new ArrayList<>();
        setDevelopmentCards(developmentCards);

        leaderCards = new ArrayList<>();
        setLeaderCards(leaderCards);

        popeFavors = new ArrayList<>();


        positions = new ArrayList<>();
        setPositions(positions);
        currentPos = 0;

    }

    private void onQuitClicked(Event event) {
        try {
            String message = MessageFactory.buildDisconnection("Disconnection request.", "nickname"); //TODO AGGIUNGERE RIFERIMENTO A NICKNAME DEL GIOCATORE
            getGUIReference().notifyInteraction(message);
        } catch (MalformedMessageException e){
            e.printStackTrace();
        }
    }

    private void onOtherPlayersClicked(Event event) {

    }

    private void onMarketBoardClicked(Event event) {

    }

    private void onMenuClicked(Event event) {

    }

    /**
     * places the resource in the selected warehouse position
     * @param position represents the warehouse position
     * @param path represents the path to the image related to the resource
     */
    public void setResource(int position, String path){
        if(position > 0 && position <= 6) {
            Image res = new Image(getClass().getResourceAsStream(path));
            warehouse.get(position - 1).setImage(res);
            warehouse.get(position - 1).setVisible(true);
        }
    }

    /**
     * removes the resource from the selected warehouse position
     * @param position represents the warehouse position
     */
    public void removeResource(int position){
        if(position > 0 && position <= 6) {
            warehouse.get(position - 1).setVisible(false);
        }
    }

    /**
     * places the development card in the selected slot
     * @param slot represents the development card slot
     * @param path represents the path to the image related to the development card
     */
    public void setDevelopmentCard(int slot, String path){
        if(slot > 0 && slot <= 3) {
            Image res = new Image(getClass().getResourceAsStream(path));
            developmentCards.get(slot - 1).setImage(res);
            developmentCards.get(slot - 1).setVisible(true);
        }
    }

    /**
     * removes the development card from the selected slot
     * @param slot represents the development card slot
     */
    public void removeDevelopmentCard(int slot){
        if(slot > 0 && slot <= 3) {
            developmentCards.get(slot - 1).setVisible(false);
        }
    }

    /**
     * places the leader card in the selected position
     * @param position represents the leader card position
     * @param path represents the path to the image related to the leader card
     */
    public void setLeaderCard(int position, String path){
        if(position > 0 && position <= 4) {
            Image res = new Image(getClass().getResourceAsStream(path));
            leaderCards.get(position - 1).setImage(res);
            leaderCards.get(position - 1).setVisible(true);
        }
    }

    /**
     * removes the leader card from the selected position
     * @param position represents the leader card position
     */
    public void removeLeaderCard(int position){
        if(position > 0 && position <= 4) {
            leaderCards.get(position - 1).setVisible(false);
        }
    }

    /**
     * activates the pope favor associated to the selected position
     * @param position represents the pope favor's position
     * @param path represents the path to the image related to the activated pope favor
     */
    public void activatePopeFavor(int position, String path){  //TODO MANCANO LE IMMAGINI RELATIVE AI POPE FAVOR ATTIVI
        if(position > 0 && position <= 3){
            Image favor = new Image(getClass().getResourceAsStream(path));
            popeFavors.get(position - 1).setImage(favor);
        }
    }

    /**
     * sets the strongbox coins to the specified number
     * @param number represents the number of coins
     */
    public void setCoinsNumber(Integer number){
        coinsNumber.setText(number.toString());
    }

    /**
     * sets the strongbox shields to the specified number
     * @param number represents the number of shields
     */
    public void setShieldsNumber(Integer number){
        shieldsNumber.setText(number.toString());
    }

    /**
     * sets the strongbox stones to the specified number
     * @param number represents the number of stones
     */
    public void setStonesNumber(Integer number){
        stonesNumber.setText(number.toString());
    }

    /**
     * sets the strongbox servants to the specified number
     * @param number represents the number of servants
     */
    public void setServantsNumber(Integer number){
        servantsNumber.setText(number.toString());
    }

    /**
     * changes the position of the player inside his faith track
     * @param position represents the new player's position
     */
    public void changePosition(int position){

        tile.setLayoutX(positions.get(position).getX());
        tile.setLayoutY(positions.get(position).getY());

    }

    /**
     * sets the ImageView associated to each slot in the warehouse
     * @param warehouse contains the ImageViews associated the warehouse
     */
    private void setWarehouse(List<ImageView> warehouse){

        warehouse.add(topResource);
        warehouse.add(firstMidResource);
        warehouse.add(secondMidResource);
        warehouse.add(firstBottomResource);
        warehouse.add(secondBottomResource);
        warehouse.add(thirdBottomResource);

    }

    /**
     * sets the ImageView associated to each development card
     * @param developmentCards contains the ImageViews associated the development cards
     */
    private void setDevelopmentCards(List<ImageView> developmentCards){

        developmentCards.add(firstSlot);
        developmentCards.add(secondSlot);
        developmentCards.add(thirdSlot);

    }

    /**
     * sets the ImageView associated to each leader card
     * @param leaderCards contains the ImageViews associated the leader cards
     */
    private void setLeaderCards(List<ImageView> leaderCards){

        leaderCards.add(firstLeaderCard);
        leaderCards.add(secondLeaderCard);
        leaderCards.add(thirdLeaderCard);
        leaderCards.add(fourthLeaderCard);

    }

    /**
     * sets the ImageView associated to each pope favor
     * @param popeFavors contains the ImageViews associated the pope favors
     */
    private void setPopeFavors(List<ImageView> popeFavors){

        popeFavors.add(firstPopeFavor);
        popeFavors.add(secondPopeFavor);
        popeFavors.add(thirdPopeFavor);

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
