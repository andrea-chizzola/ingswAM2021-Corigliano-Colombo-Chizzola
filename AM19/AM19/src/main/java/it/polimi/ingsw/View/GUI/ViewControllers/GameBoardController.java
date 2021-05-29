package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.GUI.Messages.*;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.util.*;

public class GameBoardController extends ViewController{

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
    private ImageView actionToken;
    @FXML
    private ImageView coins;
    @FXML
    private ImageView shields;
    @FXML
    private ImageView stones;
    @FXML
    private ImageView servants;
    @FXML
    private Button menuButton;
    @FXML
    private Button marketBoardButton;
    @FXML
    private Button decksButton;
    @FXML
    private Button otherPlayersButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button personalProduction;
    @FXML
    private Button actionButton;
    @FXML
    private Button swap1;
    @FXML
    private Button swap2;
    @FXML
    private Button swap3;
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
    private Label errorStrongbox;

    private List<Coordinates> positions;

    private List<Coordinates> blackPositions;

    private List<ImageView> warehouse;

    private List<ImageView> developmentCards;

    private List<ImageView> leaderCards;

    private List<ImageView> popeFavors;

    private DecksController decksController;
    private MarketboardController marketboardController;
    private Accumulator accumulator;
    private BuildMessage builder;


    @FXML
    private void initialize(){

        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMenuClicked);
        marketBoardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMarketBoardClicked);
        otherPlayersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onOtherPlayersClicked);
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onQuitClicked);
        decksButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDecksClicked);
        actionButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::sendMessage);

        actionButton.setDisable(true);
        errorStrongbox.setVisible(false);

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

        decksController = new DecksController();
        marketboardController = new MarketboardController();

        marketboardController.attachGUIReference(getGUIReference());
        marketboardController.attachModelReference(getModelReference());
        GUIHandler.createHelperWindow(marketboardController, "/FXML/marketboard.fxml");
        marketboardController.hideWindow();

        decksController.attachGUIReference(getGUIReference());
        decksController.attachModelReference(getModelReference());
        GUIHandler.createHelperWindow(decksController, "/FXML/decks.fxml");
        decksController.hideWindow();

        useWarehouse();
        useStrongbox();
        useSlots();
        useLeaders();
        usePersonalProduction();
        useSwap();
        resetTurn();
    }

    private void sendMessage(Event event) {
        getGUIReference().notifyInteraction(builder.buildMessage(accumulator));
        resetTurn();
        actionButton.setDisable(true);
    }

    private void onQuitClicked(Event event) {
        try {
            String message = MessageFactory.buildDisconnection("Disconnection request.", getModelReference().getPersonalNickname());
            getGUIReference().notifyInteraction(message);
        } catch (MalformedMessageException e){
            e.printStackTrace();
        }
    }

    private void onOtherPlayersClicked(Event event) {

        String myNickname = getModelReference().getPersonalNickname();

        for(String nickname : getModelReference().getNicknames()){
            if (!myNickname.equals(nickname)) {
                OtherBoardsController controller = new OtherBoardsController(nickname);
                controller.attachGUIReference(getGUIReference());
                controller.attachModelReference(getModelReference());
                GUIHandler.newWindow(controller, "/FXML/otherBoards.fxml");
            }
        }

    }

    private void onMarketBoardClicked(Event event) {
        marketboardController.showWindow();
    }

    private void onDecksClicked(Event event) {
        decksController.showWindow();
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


    public void manageTopToken(Optional<String> action){
        if(action.isEmpty())
            return;

        String image = getModelReference().getConfiguration().getActionTokenCard(action.get()).getImage();
        Image token = new Image("/Images/punchboard/" + image);
        actionToken.setImage(token);
        actionToken.setVisible(true);
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
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setOpacity(1);
                        break;
                    case BLUE:
                        Image image1 = new Image(getClass().getResourceAsStream("/Images/punchboard/shield.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image1);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(true);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setOpacity(1);
                        break;
                    case GRAY:
                        Image image2 = new Image(getClass().getResourceAsStream("/Images/punchboard/stone.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image2);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(true);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setOpacity(1);
                        break;
                    case PURPLE:
                        Image image3 = new Image(getClass().getResourceAsStream("/Images/punchboard/servant.png"));
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setImage(image3);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setVisible(true);
                        warehouse.get(warehouseRes.indexOf(resQuantity)).setOpacity(1);
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

    public void setDecks(Map<Integer, String> decks){
        decksController.showDecksUpdate(decks);
    }

    public void setMarketBoard(List<Marble> tray){
        List<String> trayImages = new LinkedList<>();
        for(int i=0; i<tray.size(); i++){
            trayImages.add(getMarbleImage(tray.get(i)));
        }
        marketboardController.showMarketUpdate(trayImages);
    }

    //TODO aggiungere immagine di risorse e di biglie alle classi Resource e Marble
    private String getMarbleImage(Marble marble){
        switch(marble.getResourceAssociated().getColor()){
            case RED: {
                return "/Images/market/MarbleRed.PNG";
            }
            case BLUE: {
                return "/Images/market/MarbleBlue.PNG";
            }
            case GRAY: {
                return "/Images/market/MarbleGray.PNG";
            }
            case WHITE: {
                return "/Images/market/MarbleWhite.PNG";
            }
            case PURPLE: {
                return "/Images/market/MarblePurple.PNG";
            }
            case YELLOW: {
                return "/Images/market/MarbleYellow.PNG";
            }
            default:
                return "/Images/market/MarbleWhite.PNG";
        }
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
     * adds the events handlers to the images associated with the warehouse
     */
    private void useWarehouse(){

        helpUseWarehouse(topResource,1);
        helpUseWarehouse(firstMidResource,2);
        helpUseWarehouse(secondMidResource,2);
        helpUseWarehouse(firstBottomResource,3);
        helpUseWarehouse(secondBottomResource,3);
        helpUseWarehouse(thirdBottomResource,3);
    }

    private void helpUseWarehouse(ImageView imageView,Integer slot){
        List<ResQuantity> list = getModelReference().getBoard(getModelReference().getPersonalNickname()).getWarehouse();
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, action -> {imageView.setVisible(false);});
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, action -> {
            imageView.setOpacity(0.5);
            imageView.setDisable(true);
            imageView.setVisible(true);
            accumulator.setWarehouse(slot.toString());
            accumulator.setWarehouse(list.get(slot-1).getResource().getColor().toString());});
    }

    /**
     * enable or disable the event handlers of the images associated with the warehouse
     * @param b if b=true the events handlers are set as active, otherwise they are set as inactive
     */
    private void enableWarehouse(Boolean b){
        topResource.setDisable(!b);
        firstMidResource.setDisable(!b);
        secondMidResource.setDisable(!b);
        firstBottomResource.setDisable(!b);
        secondBottomResource.setDisable(!b);
        thirdBottomResource.setDisable(!b);
    }


    /**
     * adds the events handlers to the images associated with the strongbox
     */
    private void useStrongbox(){

        helpUseStrongbox(coins,coinsNumber,"COIN");
        helpUseStrongbox(shields,shieldsNumber,"SHIELD");
        helpUseStrongbox(stones,stonesNumber,"STONE");
        helpUseStrongbox(servants,servantsNumber,"SERVANT");

    }

    private void helpUseStrongbox(ImageView imageView, Label label, String resource){
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(0.8));
        visiblePause.setOnFinished(event -> errorStrongbox.setVisible(false));

        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, action -> {imageView.setVisible(false);});
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, action -> {
            imageView.setVisible(true);
            Integer i = Integer.parseInt(label.getText());
            if(i == 0) {
                errorStrongbox.setVisible(true);
                visiblePause.play();
                return;
            }
            i--;
            label.setText(i.toString());
            accumulator.setStrongbox(resource);
            accumulator.setStrongbox("1");});

    }

    /**
     * enable or disable the event handlers of the images associated with the strongbox
     * @param b if b=true the events handlers are set as active, otherwise they are set as inactive
     */
    private void enableStrongbox(Boolean b){
        coins.setDisable(!b);
        shields.setDisable(!b);
        servants.setDisable(!b);
        stones.setDisable(!b);
    }


    /**
     * adds the events handlers to the images associated with the slots
     */
    private void useSlots(){
        helpUseSlots(firstSlot,1);
        helpUseSlots(secondSlot,2);
        helpUseSlots(thirdSlot,3);
    }

    private void helpUseSlots(ImageView imageView, Integer slot){

        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, action -> {imageView.setVisible(false);});
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, action -> {
            imageView.setOpacity(0.5);
            imageView.setDisable(true);
            imageView.setVisible(true);
            accumulator.setDevelopmentCards(slot.toString());
            accumulator.setSlot(slot);});
    }

    /**
     * enable or disable the event handlers of the images associated with the slots
     * @param b if b=true the events handlers are set as active, otherwise they are set as inactive
     */
    private void enableSlots(Boolean b){
        firstSlot.setDisable(!b);
        secondSlot.setDisable(!b);
        thirdSlot.setDisable(!b);
    }

    private void useSwap(){
        helpUseSwap(swap1,1);
        helpUseSwap(swap2,2);
        helpUseSwap(swap3,3);
    }

    private void helpUseSwap(Button button, int shelf){
        button.setOnAction(action -> {
            accumulator.setSwap(shelf);
            button.setOpacity(0.3);
            button.setDisable(true);});
    }

    /**
     * adds the events handlers to the images associated with the leader cards
     */
    private void useLeaders(){
        helpUseLeaders(firstLeaderCard,1);
        helpUseLeaders(secondLeaderCard,2);
        helpUseLeaders(thirdLeaderCard,3);
        helpUseLeaders(fourthLeaderCard,4);
    }

    private void helpUseLeaders(ImageView imageView, Integer number){

        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, action -> {imageView.setVisible(false);});
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, action -> {
            imageView.setOpacity(0.8);
            imageView.setDisable(true);
            imageView.setVisible(true);
            accumulator.setLeaderCards(number.toString());
            accumulator.setLeaderCard(number);});
    }

    /**
     * enable or disable the event handlers of the images associated with the leader cards
     * @param b if b=true the events handlers are set as active, otherwise they are set as inactive
     */
    private void enableLeaderCards(Boolean b){
        firstLeaderCard.setDisable(!b);
        secondLeaderCard.setDisable(!b);
        thirdLeaderCard.setDisable(!b);
        fourthLeaderCard.setDisable(!b);
    }

    /**
     * adds the events handlers to the button personalProduction
     */
    private void usePersonalProduction(){
        personalProduction.addEventHandler(MouseEvent.MOUSE_RELEASED, action ->{
            personalProduction.setOpacity(0.5);
            personalProduction.setDisable(true);
            personalProduction.setVisible(true);
            accumulator.setPersonalProduction();});
    }

    /**
     * resets all the images view and buttons that was changed during the game
     */
    public void resetTurn(){
        actionButton.setDisable(true);
        enableWarehouse(false);
        enableStrongbox(false);
        enableLeaderCards(false);
        enableSlots(false);
        personalProduction.setDisable(true);
        personalProduction.setVisible(false);

        resetImage(firstSlot,1);
        resetImage(secondSlot,1);
        resetImage(thirdSlot,1);

        resetImage(firstLeaderCard,0.5);
        resetImage(secondLeaderCard,0.5);
        resetImage(thirdLeaderCard,0.5);
        resetImage(fourthLeaderCard,0.5);

        swap1.setVisible(false);
        swap2.setVisible(false);
        swap3.setVisible(false);
    }

    private void resetImage(ImageView imageView, double opacity){
        imageView.setOpacity(opacity);
    }

    public void initializeLeaders(){
        this.accumulator = new Accumulator();
        this.builder = new BuildLeaderUpdate();
        enableLeaderCards(true);
        actionButton.setDisable(false);
    }

    /**
     * sets the correct buttons and images to play the turn
     */
    public void setDoProduction(){
        this.accumulator = new Accumulator();
        this.builder = new BuildDoProduction();
        enableWarehouse(true);
        enableStrongbox(true);
        enableLeaderCards(true);
        enableSlots(true);
        personalProduction.setOpacity(1);
        personalProduction.setDisable(false);
        personalProduction.setVisible(true);
        actionButton.setDisable(false);

    }

    /**
     * sets the correct buttons and images to play the turn
     */
    public void setBuyCard(){
        this.accumulator = new Accumulator();
        this.builder = new BuildBuyCard();
        enableWarehouse(true);
        enableStrongbox(true);
        enableSlots(true);
        actionButton.setDisable(false);
    }

    /**
     * sets the correct buttons and images to play the turn
     */
    public void setManageLeader(){
        this.accumulator = new Accumulator();
        this.builder = new BuildLeaderAction();
        enableLeaderCards(true);
        actionButton.setDisable(false);
    }


    /**
     * sets the correct buttons and images to play the turn
     */
    public void setSwap(){
        this.accumulator = new Accumulator();
        this.builder = new BuildSwap();
        enableWarehouse(true);
        swap1.setVisible(true);
        swap1.setOpacity(1);
        swap2.setVisible(true);
        swap2.setOpacity(1);
        swap3.setVisible(true);
        swap3.setOpacity(1);
        actionButton.setDisable(false);
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
