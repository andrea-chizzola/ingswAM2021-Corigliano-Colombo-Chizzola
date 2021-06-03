package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardController extends ViewController implements HelperWindow{

    /**
     * private class used to store the coordinates associated to each position of the faith track
     */
    private class Coordinates{

        private double x;
        private double y;

        /**
         * creates a new position given the associated coordinates
         * @param x represents the new position's x coordinate
         * @param y represents the new position's y coordinate
         */
        public Coordinates(double x, double y){
            this.x = x;
            this.y = y;
        }

        /**
         *
         * @return the x coordinate associated to the selected position
         */
        public double getX(){
            return x;
        }

        /**
         *
         * @return the y coordinate associated to the selected position
         */
        public double getY() {
            return y;
        }

    }

    @FXML
    private AnchorPane pane;
    @FXML
    private Label nicknameLabel;
    @FXML
    protected ImageView firstSlot;
    @FXML
    protected ImageView secondSlot;
    @FXML
    protected ImageView thirdSlot;
    @FXML
    protected ImageView firstLeaderCard;
    @FXML
    protected ImageView secondLeaderCard;
    @FXML
    protected ImageView thirdLeaderCard;
    @FXML
    protected ImageView fourthLeaderCard;
    @FXML
    protected ImageView firstPopeFavor;
    @FXML
    protected ImageView secondPopeFavor;
    @FXML
    protected ImageView thirdPopeFavor;
    @FXML
    protected ImageView topResource;
    @FXML
    protected ImageView firstMidResource;
    @FXML
    protected ImageView secondMidResource;
    @FXML
    protected ImageView firstBottomResource;
    @FXML
    protected ImageView secondBottomResource;
    @FXML
    protected ImageView thirdBottomResource;
    @FXML
    protected ImageView tile;
    @FXML
    protected Label coinsNumber;
    @FXML
    protected Label shieldsNumber;
    @FXML
    protected Label stonesNumber;
    @FXML
    protected Label servantsNumber;
    @FXML
    protected ImageView extraShelf1;
    @FXML
    protected ImageView extraShelf2;
    @FXML
    protected Label extraShelf2Label;
    @FXML
    protected Label extraShelf1Label;
    @FXML
    protected Label extraShelfString;


    protected List<Coordinates> positions;
    protected List<ImageView> warehouse;
    protected List<ImageView> warehouse2;
    protected List<ImageView> warehouse3;
    protected List<ImageView> developmentCards;
    protected List<ImageView> leaderCards;
    protected List<ImageView> popeFavors;
    private final String nickname;
    private final String cardPath = "/Images/front/";
    private final String boardPath = "/Images/punchboard/";

    /**
     * this method is the constructor of the class
     * @param nickname is the name of the player who owns the board
     */
    public BoardController(String nickname){
        this.nickname=nickname;
    }

    /**
     * This method is used to initialize the scene
     */
    @FXML
    public void initialize(){

        nicknameLabel.setText(nickname);

        warehouse2 = List.of(firstMidResource, secondMidResource);
        warehouse3 = List.of(firstBottomResource, secondBottomResource, thirdBottomResource);
        warehouse = List.of(topResource, firstMidResource, secondMidResource
                , firstBottomResource, secondBottomResource, thirdBottomResource);
        setWarehouse();

        developmentCards = List.of(firstSlot, secondSlot, thirdSlot);
        setDevelopmentCards();

        leaderCards = List.of(firstLeaderCard, secondLeaderCard, thirdLeaderCard, fourthLeaderCard);
        setLeaderCards();

        popeFavors = List.of(firstPopeFavor, secondPopeFavor, thirdPopeFavor);

        positions = new ArrayList<>();
        setPositions(positions);
    }

    /**
     * sets the ImageView associated to each slot in the warehouse
     */
    protected void setWarehouse(){
        topResource.setVisible(false);
        firstMidResource.setVisible(false);
        secondMidResource.setVisible(false);
        firstBottomResource.setVisible(false);
        secondBottomResource.setVisible(false);
        thirdBottomResource.setVisible(false);
    }

    /**
     * sets the ImageView associated to each development card
     */
    protected void setDevelopmentCards(){
        firstSlot.setVisible(false);
        secondSlot.setVisible(false);
        thirdSlot.setVisible(false);
    }

    /**
     * sets the ImageView associated to each leader card
     */
    protected void setLeaderCards(){

        firstLeaderCard.setVisible(false);
        secondLeaderCard.setVisible(false);
        thirdLeaderCard.setVisible(false);
        fourthLeaderCard.setVisible(false);

    }

    /**
     * @param position represents the selected position
     * @return the X coordinate associated to the selected position
     */
    protected double getXCoordinate(int position){
        return positions.get(position).getX();
    }

    /**
     * @param position represents the selected position
     * @return the Y coordinate associated to the selected position
     */
    protected  double getYCoordinate(int position){
        return positions.get(position).getY();
    }

    /**
     * changes the position of the player inside his faith track
     * @param position represents the new player's position
     */
    public void changePosition(int position){

        tile.setLayoutX(getXCoordinate(position));
        tile.setLayoutY(getYCoordinate(position));

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
            String image = getModelReference().getConfiguration().getLeaderCard(cards.get(slot)).getPath();
            if(status.get(slot) == ItemStatus.ACTIVE){
                Image card = new Image(getClass().getResourceAsStream(cardPath + image));
                leaderCards.get(slot - 1).setImage(card);
                leaderCards.get(slot - 1).setVisible(true);
            }else{
                Image card = new Image(getClass().getResourceAsStream(cardPath + image));
                leaderCards.get(slot - 1).setImage(card);
                leaderCards.get(slot - 1).setVisible(true);
                leaderCards.get(slot - 1).setOpacity(0.5);
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
            String path = getModelReference().getConfiguration().getDevelopmentCard(slots.get(slot)).getPath();
            Image card = new Image(getClass().getResourceAsStream("/Images/front/" + path));
            developmentCards.get(slot - 1).setImage(card);
            developmentCards.get(slot - 1).setVisible(true);
        }

    }

    /**
     * manages the activation and deactivation of the pope favors
     * @param sections contains the status of each pope favor
     */
    public void manageSections(List<ItemStatus> sections){

        for(ItemStatus status : sections){
            if(status == ItemStatus.ACTIVE){
                int section = sections.indexOf(status);
                switch (section){
                    case 0:
                        Image image = new Image(getClass().getResourceAsStream(boardPath + "quadratogialloattivo.png"));
                        popeFavors.get(section).setImage(image);
                        break;
                    case 1:
                        Image image1 = new Image(getClass().getResourceAsStream(boardPath + "quadratoarancioattivo.png"));
                        popeFavors.get(section).setImage(image1);
                        break;
                    case 2:
                        Image image2 = new Image(getClass().getResourceAsStream(boardPath + "quadratorossoattivo.png"));
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

        for(int i=0; i<warehouseRes.size(); i++){
            ResQuantity resQuantity = warehouseRes.get(i);
            boolean isExtra = i >= 3;
            if(resQuantity.getQuantity() == 0 && !isExtra){
                insertWarehouse(i+1,0,null, false);
            }else{
                switch (resQuantity.getResource().getColor()) {
                    case YELLOW:
                        Image image = new Image(getClass().getResourceAsStream(boardPath + "coin.png"));
                        insertWarehouse(i+1,resQuantity.getQuantity(),image,isExtra);
                        break;
                    case BLUE:
                        Image image1 = new Image(getClass().getResourceAsStream(boardPath + "shield.png"));
                        insertWarehouse(i+1,resQuantity.getQuantity(),image1,isExtra);
                        break;
                    case GRAY:
                        Image image2 = new Image(getClass().getResourceAsStream(boardPath + "stone.png"));
                        insertWarehouse(i+1,resQuantity.getQuantity(),image2,isExtra);
                        break;
                    case PURPLE:
                        Image image3 = new Image(getClass().getResourceAsStream(boardPath + "servant.png"));
                        insertWarehouse(i+1,resQuantity.getQuantity(),image3,isExtra);
                        break;
                }
            }
        }

    }

    /**
     * this helper method is used to insert a resource in the warehouse
     * @param shelf is the position of the extra shelf
     * @param quantity is the amount of resources to be inserted in the extra shelf
     * @param image is the image that represents the inserted resource
     * @param isExtra is true if the resource will be inserted in an extra slot
     */
    private void insertWarehouse(int shelf, int quantity, Image image, boolean isExtra){
        if(isExtra){
            insertWarehouseExtra(shelf,quantity,image);
            return;
        }
        if(shelf == 1){
            if(quantity == 1) {
                topResource.setImage(image);
                topResource.setVisible(true);
                topResource.setOpacity(1);
            }else
                topResource.setVisible(false);
        }
        if(shelf == 2) {
            insertWarehouseHelper(quantity, image, warehouse2);
        }

        if(shelf == 3)
            insertWarehouseHelper(quantity, image, warehouse3);
    }

    /**
     * this private helper is used to represents all the resources in a shelf
     * @param quantity is the amount of resources to be represented
     * @param image is the image that represents the resources
     * @param warehouse is the list of ImageViews that represents the warehouse
     */
    private void insertWarehouseHelper(int quantity, Image image, List<ImageView> warehouse) {
        for (int i = 0; i < warehouse.size(); i++) {
            if(i<quantity) {
                warehouse.get(i).setImage(image);
                warehouse.get(i).setVisible(true);
                warehouse.get(i).setOpacity(1);
            }else
                warehouse.get(i).setVisible(false);
        }
    }

    /**
     * this helper method is used to insert a resource in an extra shelf
     * @param shelf is the position of the extra shelf
     * @param quantity is the amount of resources to be inserted in the extra shelf
     * @param image is the image that represents the inserted resource
     */
    private void insertWarehouseExtra(int shelf, Integer quantity, Image image){
        extraShelfString.setVisible(true);
        if(shelf == 4){
            extraShelf1.setImage(image);
            extraShelf1.setVisible(true);
            extraShelf1Label.setText(quantity.toString());
            extraShelf1Label.setVisible(true);
        }
        if(shelf == 5){
            extraShelf2.setImage(image);
            extraShelf2.setVisible(true);
            extraShelf2Label.setText(quantity.toString());
            extraShelf2Label.setVisible(true);
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
     * sets the positions related to each tile of the faith track
     * @param positions contains the coordinates of each tile
     */
    protected void setPositions(List<Coordinates> positions){

        positions.add(new Coordinates(37, 111));
        positions.add(new Coordinates(80, 111));
        positions.add(new Coordinates(123, 111));
        positions.add(new Coordinates(123, 69));
        positions.add(new Coordinates(123, 27));
        positions.add(new Coordinates(166, 27));
        positions.add(new Coordinates(208, 27));
        positions.add(new Coordinates(251, 27));
        positions.add(new Coordinates(294, 27));
        positions.add(new Coordinates(337,27));
        positions.add(new Coordinates(337, 69));
        positions.add(new Coordinates(337, 111));
        positions.add(new Coordinates(380, 111));
        positions.add(new Coordinates(422, 111));
        positions.add(new Coordinates(464, 111));
        positions.add(new Coordinates(506, 111));;
        positions.add(new Coordinates(549, 111));
        positions.add(new Coordinates(549, 69));
        positions.add(new Coordinates(549, 27));
        positions.add(new Coordinates(592, 27));
        positions.add(new Coordinates(634, 27));
        positions.add(new Coordinates(677, 27));
        positions.add(new Coordinates(720, 27));
        positions.add(new Coordinates(762, 27));
        positions.add(new Coordinates(804, 27));

    }


    /**
     * shows the scene
     */
    public void showWindow(){
        ((Stage) pane.getScene().getWindow()).show();
    }

    /**
     * hides the scene
     */
    public void hideWindow(){
        pane.getScene().getWindow().hide();
    }
}
