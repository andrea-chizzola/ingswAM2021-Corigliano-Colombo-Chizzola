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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OtherBoardsController extends ViewController{

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
    private Circle tile;
    @FXML
    private Label coinsNumber;
    @FXML
    private Label shieldsNumber;
    @FXML
    private Label stonesNumber;
    @FXML
    private Label servantsNumber;
    @FXML
    private Label nicknameLabel;

    private List<Coordinates> positions;

    private List<ImageView> warehouse;

    private List<ImageView> developmentCards;

    private List<ImageView> leaderCards;

    private List<ImageView> popeFavors;

    private String nickname;

    private ReducedGameBoard model = GUIHandler.instance().getModel();

    public OtherBoardsController(String nickname) {
        this.nickname = nickname;
    }

    @FXML
    private void initialize(){

        nicknameLabel.setText(nickname);

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

        fillBoard();

    }

    /**
     * retrieves the status of the board associated to the player
     */
    public void fillBoard(){

        ReducedBoard board = model.getBoard(nickname);

        changePosition(board.getFaithPoints());
        manageSections(board.getSections());
        manageDevelopmentCards(board.getSlots());
        manageLeaderCards(board.getLeadersID(), board.getLeadersStatus());
        setResourceStrongbox(board.getStrongbox());
        setResourceWarehouse(board.getWarehouse());

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
     * manages the selection and activation of the leader cards
     * @param cards contains the leader cards
     * @param status contains the status of each leader card
     */
    private void manageLeaderCards(Map<Integer, String> cards, Map<Integer, ItemStatus> status) {

        for (Integer slot : cards.keySet()) {
            String path = model.getConfiguration().getLeaderCard(cards.get(slot)).getPath();
            if (status.get(slot) == ItemStatus.ACTIVE) {
                Image card = new Image(getClass().getResourceAsStream("/Images/front/" + path));
                leaderCards.get(slot - 1).setImage(card);
            } else {
                Image card = new Image(getClass().getResourceAsStream("/Images/front/" + path));
                leaderCards.get(slot - 1).setImage(card);
                leaderCards.get(slot - 1).setOpacity(0.5);
            }
        }
    }

    /**
     * manages the selection of the development cards
     * @param slots contains the development cards
     */
    private void manageDevelopmentCards(Map<Integer, String> slots){

        for(Integer slot : slots.keySet()){
            String path = model.getConfiguration().getDevelopmentCard(slots.get(slot)).getPath();
            Image card = new Image(getClass().getResourceAsStream("/Images/front/" + path));
            developmentCards.get(slot - 1).setImage(card);
        }
    }

    /**
     * manages the activation and deactivation of the pope favors
     * @param sections contains the status of each pope favor
     */
    private void manageSections(List<ItemStatus> sections){
        for(ItemStatus status : sections){
            if(status == ItemStatus.ACTIVE){
                int section = sections.indexOf(status);
                switch (section){
                    case 0:
                        Image image = new Image(getClass().getResourceAsStream("/Images/punchboard/quadratogialloattivo.png"));
                        popeFavors.get(section).setImage(image);
                        break;
                    case 1:
                        Image image1 = new Image(getClass().getResourceAsStream("/Images/punchboard/quadratoarancioattivo.png"));
                        popeFavors.get(section).setImage(image1);
                        break;
                    case 2:
                        Image image2 = new Image(getClass().getResourceAsStream("/Images/punchboard/quadratorossoattivo.png"));
                        popeFavors.get(section).setImage(image2);
                        break;
                }

            }
        }
    }

    /**
     * stores the selected resources inside the strongbox
     * @param strongbox contains the resources to store
     */
    private void setResourceStrongbox(List<ResQuantity> strongbox){

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
    private void setResourceWarehouse(List<ResQuantity> warehouseRes){

        for(ResQuantity resQuantity : warehouseRes){
            if(resQuantity.getQuantity() == 0){
                warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(false);
            }else{
                switch (resQuantity.getResource().getColor()) {
                    case YELLOW:
                        Image image = new Image(getClass().getResourceAsStream("/Images/punchboard/coin.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image);
                        break;
                    case BLUE:
                        Image image1 = new Image(getClass().getResourceAsStream("/Images/punchboard/shield.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image1);
                        break;
                    case GRAY:
                        Image image2 = new Image(getClass().getResourceAsStream("/Images/punchboard/stone.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image2);
                        break;
                    case PURPLE:
                        Image image3 = new Image(getClass().getResourceAsStream("/Images/punchboard/servant.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image3);
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
