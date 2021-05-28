package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Client.ReducedModel.ReducedConfiguration;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.GUI.GUIHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ImageView blackCross;
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

    private List<Coordinates> blackPositions;

    private List<ImageView> warehouse;

    private List<ImageView> developmentCards;

    private List<ImageView> leaderCards;

    private List<ImageView> popeFavors;

    private ReducedGameBoard model = GUIHandler.instance().getModel();


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
        setPopeFavors(popeFavors);

        positions = new ArrayList<>();
        setPositions(positions);

        blackPositions = new ArrayList<>();
        setBlackPositions(blackPositions);
        blackCross.setVisible(false);

    }

    private void onQuitClicked(Event event) {
        try {
            String message = MessageFactory.buildDisconnection("Disconnection request.", model.getPersonalNickname());
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

    public Scene getScene() {
        return pane.getScene();
    }

    /**
     * manages the activation and deactivation of the pope favors
     * @param sections contains the status of each pope favor
     */
    public void manageSections(List<ItemStatus> sections){  //TODO MANCANO LE IMMAGINI RELATIVE AI POPE FAVOR ATTIVI
        for(ItemStatus status : sections){
            if(status == ItemStatus.ACTIVE){
                int section = sections.indexOf(status);
                switch (section){
                    case 0:
                        Image image = new Image(getClass().getResourceAsStream("/Images/punchboard/quadrato giallo attivo.png"));
                        popeFavors.get(section).setImage(image);
                        break;
                    case 1:
                        Image image1 = new Image(getClass().getResourceAsStream("/Images/punchboard/quadrato arancio attivo.png"));
                        popeFavors.get(section).setImage(image1);
                        break;
                    case 2:
                        Image image2 = new Image(getClass().getResourceAsStream("/Images/punchboard/quadrato rosso attivo.png"));
                        popeFavors.get(section).setImage(image2);
                        break;
                }

            }
        }
    }

    /**
     * manages the selection of the development cards
     * @param slots contains the development cards
     */
    public void manageDevelopmentCards(Map<Integer, String> slots){
        for(ImageView image : developmentCards){
            image.setVisible(false);
        }
        for(Integer slot : slots.keySet()){
            String path = model.getConfiguration().getDevelopmentCard(slots.get(slot)).getPath();
            Image card = new Image(getClass().getResourceAsStream("/Images/front/" + path));
            developmentCards.get(slot - 1).setImage(card);
            developmentCards.get(slot - 1).setVisible(true);
        }
    }

    /**
     * manages the selection and activation of the leader cards
     * @param cards contains the leader cards
     * @param status contains the status of each leader card
     */
    public void manageLeaderCards(Map<Integer, String> cards, Map<Integer, ItemStatus> status){
        for(ImageView image : leaderCards){
            image.setVisible(false);
        }
        for(Integer slot : cards.keySet()){
            String path = model.getConfiguration().getLeaderCard(cards.get(slot)).getPath();
            if(status.get(slot) == ItemStatus.ACTIVE){
                Image card = new Image(getClass().getResourceAsStream("/Images/front/" + path));
                leaderCards.get(slot - 1).setImage(card);
                leaderCards.get(slot - 1).setVisible(true);
            }else{
                Image card = new Image(getClass().getResourceAsStream("/Images/front/" + path));
                leaderCards.get(slot - 1).setImage(card);
                leaderCards.get(slot - 1).setVisible(true);
                leaderCards.get(slot - 1).setOpacity(0.5);
            }
        }
    }

    /**
     * stores the selected resources inside the strongbox
     * @param strongbox contains the resources to store
     */
    public void setResourceStrongbox(List<ResQuantity> strongbox){

        for(ResQuantity resQuantity : strongbox) {
            switch (resQuantity.getResource().getColor()) {
                case YELLOW:
                    setCoinsNumber(resQuantity.getQuantity());
                    break;
                case BLUE:
                    setShieldsNumber(resQuantity.getQuantity());
                    break;
                case GRAY:
                    setStonesNumber(resQuantity.getQuantity());
                    break;
                case PURPLE:
                    setServantsNumber(resQuantity.getQuantity());
                    break;
            }
        }
    }

    /**
     * stores the selected resources inside the warehouse
     * @param warehouseRes contains the resources to store
     */
    public void setResourceWarehouse(List<ResQuantity> warehouseRes){

        for(ResQuantity resQuantity : warehouseRes){
            if(resQuantity.getQuantity() == 0){
                warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(false);
            }else{
                switch (resQuantity.getResource().getColor()) {
                    case YELLOW:
                        Image image = new Image(getClass().getResourceAsStream("/Images/punchboard/coin.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(true);
                        break;
                    case BLUE:
                        Image image1 = new Image(getClass().getResourceAsStream("/Images/punchboard/shield.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image1);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(true);
                        break;
                    case GRAY:
                        Image image2 = new Image(getClass().getResourceAsStream("/Images/punchboard/stone.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image2);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(true);
                        break;
                    case PURPLE:
                        Image image3 = new Image(getClass().getResourceAsStream("/Images/punchboard/servant.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image3);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(true);
                        break;
                }
            }
        }

    }

    /**
     * sets the strongbox coins to the specified number
     * @param number represents the number of coins
     */
    private void setCoinsNumber(Integer number){
        coinsNumber.setText(number.toString());
    }

    /**
     * sets the strongbox shields to the specified number
     * @param number represents the number of shields
     */
    private void setShieldsNumber(Integer number){
        shieldsNumber.setText(number.toString());
    }

    /**
     * sets the strongbox stones to the specified number
     * @param number represents the number of stones
     */
    private void setStonesNumber(Integer number){
        stonesNumber.setText(number.toString());
    }

    /**
     * sets the strongbox servants to the specified number
     * @param number represents the number of servants
     */
    private void setServantsNumber(Integer number){
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
     * changes Lorenzo's position inside the faith track
     * @param position represents Lorenzo's position
     */
    public void changeBlackPosition(int position){

        blackCross.setLayoutX(blackPositions.get(position).getX());
        blackCross.setLayoutY(blackPositions.get(position).getY());

    }

    /**
     * makes Lorenzo's black cross visible in case of a single player game
     */
    public void visualizeBlackCross(){
        blackCross.setVisible(true);
    }

    /**
     * sets the ImageView associated to each slot in the warehouse
     * @param warehouse contains the ImageViews associated the warehouse
     */
    private void setWarehouse(List<ImageView> warehouse){

        warehouse.add(topResource);
        topResource.setVisible(false);
        warehouse.add(firstMidResource);
        firstMidResource.setVisible(false);
        warehouse.add(secondMidResource);
        secondMidResource.setVisible(false);
        warehouse.add(firstBottomResource);
        firstBottomResource.setVisible(false);
        warehouse.add(secondBottomResource);
        secondBottomResource.setVisible(false);
        warehouse.add(thirdBottomResource);
        thirdBottomResource.setVisible(false);

    }

    /**
     * sets the ImageView associated to each development card
     * @param developmentCards contains the ImageViews associated the development cards
     */
    private void setDevelopmentCards(List<ImageView> developmentCards){

        developmentCards.add(firstSlot);
        firstSlot.setVisible(false);
        developmentCards.add(secondSlot);
        secondSlot.setVisible(false);
        developmentCards.add(thirdSlot);
        thirdSlot.setVisible(false);

    }

    /**
     * sets the ImageView associated to each leader card
     * @param leaderCards contains the ImageViews associated the leader cards
     */
    private void setLeaderCards(List<ImageView> leaderCards){

        leaderCards.add(firstLeaderCard);
        firstLeaderCard.setVisible(false);
        leaderCards.add(secondLeaderCard);
        secondLeaderCard.setVisible(false);
        leaderCards.add(thirdLeaderCard);
        thirdLeaderCard.setVisible(false);
        leaderCards.add(fourthLeaderCard);
        fourthLeaderCard.setVisible(false);

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

    /**
     * sets the positions related to each tile of the faith track (only for Lorenzo's black cross)
     * @param positions contains the coordinates of each tile
     */
    private void setBlackPositions(List<Coordinates> positions){

        positions.add(new Coordinates(39, 95));
        positions.add(new Coordinates(81, 95));
        positions.add(new Coordinates(120, 95));
        positions.add(new Coordinates(120, 64));
        positions.add(new Coordinates(120, 30));
        positions.add(new Coordinates(166, 30));
        positions.add(new Coordinates(211, 30));
        positions.add(new Coordinates(249, 30));
        positions.add(new Coordinates(293, 30));
        positions.add(new Coordinates(337,30));
        positions.add(new Coordinates(337, 64));
        positions.add(new Coordinates(337, 95));
        positions.add(new Coordinates(378, 95));
        positions.add(new Coordinates(420, 95));
        positions.add(new Coordinates(465, 95));
        positions.add(new Coordinates(509, 95));
        positions.add(new Coordinates(552, 95));
        positions.add(new Coordinates(552, 64));
        positions.add(new Coordinates(552, 30));
        positions.add(new Coordinates(594, 30));
        positions.add(new Coordinates(633, 30));
        positions.add(new Coordinates(675, 30));
        positions.add(new Coordinates(717, 30));
        positions.add(new Coordinates(759, 30));
        positions.add(new Coordinates(805, 30));

    }

}
