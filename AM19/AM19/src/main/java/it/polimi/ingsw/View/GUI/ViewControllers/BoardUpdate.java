package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Map;

public abstract class BoardUpdate extends ViewController{

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
    protected Circle tile;
    @FXML
    protected Label coinsNumber;
    @FXML
    protected Label shieldsNumber;
    @FXML
    protected Label stonesNumber;
    @FXML
    protected Label servantsNumber;

    protected List<Coordinates> positions;

    protected List<ImageView> warehouse;

    protected List<ImageView> warehouse2;

    protected List<ImageView> warehouse3;

    protected List<ImageView> developmentCards;

    protected List<ImageView> leaderCards;

    protected List<ImageView> popeFavors;

    /**
     * changes the position of the player inside his faith track
     * @param position represents the new player's position
     */
    public void changePosition(int position){

        tile.setLayoutX(positions.get(position).getX());
        tile.setLayoutY(positions.get(position).getY());

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
            String path = getModelReference().getConfiguration().getLeaderCard(cards.get(slot)).getPath();
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
            if(resQuantity.getQuantity() == 0){
                insertWarehouse(i+1,0,null);
            }else{
                switch (resQuantity.getResource().getColor()) {
                    case YELLOW:
                        Image image = new Image(getClass().getResourceAsStream("/Images/punchboard/coin.png"));
                        insertWarehouse(i+1,resQuantity.getQuantity(),image);
                        // warehouse.get(warehouseRes.indexOf(resQuantity)).setOpacity(1);
                        break;
                    case BLUE:
                        Image image1 = new Image(getClass().getResourceAsStream("/Images/punchboard/shield.png"));
                        insertWarehouse(i+1,resQuantity.getQuantity(),image1);
                        //warehouse.get(warehouseRes.indexOf(resQuantity)).setOpacity(1);
                        break;
                    case GRAY:
                        Image image2 = new Image(getClass().getResourceAsStream("/Images/punchboard/stone.png"));
                        insertWarehouse(i+1,resQuantity.getQuantity(),image2);
                        //warehouse.get(warehouseRes.indexOf(resQuantity)).setOpacity(1);
                        break;
                    case PURPLE:
                        Image image3 = new Image(getClass().getResourceAsStream("/Images/punchboard/servant.png"));
                        insertWarehouse(i+1,resQuantity.getQuantity(),image3);
                        // warehouse.get(warehouseRes.indexOf(resQuantity)).setOpacity(1);
                        break;
                }
            }
        }

    }

    private void insertWarehouse(int shelf, int quantity, Image image){
        if(shelf == 1){
            if(quantity == 1) {
                topResource.setImage(image);
                topResource.setVisible(true);
                topResource.setOpacity(1);
            }else
                topResource.setVisible(false);
        }
        if(shelf == 2)
            for (int i = 0; i < warehouse2.size(); i++) {
                if(i<quantity) {
                    warehouse2.get(i).setImage(image);
                    warehouse2.get(i).setVisible(true);
                    warehouse2.get(i).setOpacity(1);
                }else
                    warehouse2.get(i).setVisible(false);
            }

        if(shelf == 3)
            for (int i = 0; i < warehouse3.size(); i++) {
                if(i<quantity) {
                    warehouse3.get(i).setImage(image);
                    warehouse3.get(i).setVisible(true);
                    warehouse3.get(i).setOpacity(1);
                }else
                    warehouse3.get(i).setVisible(false);
            }
    }

    /**
     * sets the ImageView associated to each slot in the warehouse
     * @param warehouse contains the ImageViews associated the warehouse
     */
    protected void setWarehouse(List<ImageView> warehouse){

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
    protected void setDevelopmentCards(List<ImageView> developmentCards){

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
    protected void setLeaderCards(List<ImageView> leaderCards){

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
    protected void setPopeFavors(List<ImageView> popeFavors){

        popeFavors.add(firstPopeFavor);
        popeFavors.add(secondPopeFavor);
        popeFavors.add(thirdPopeFavor);

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
