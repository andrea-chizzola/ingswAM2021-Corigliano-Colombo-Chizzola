package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.GUI.Messages.*;
import it.polimi.ingsw.View.PlayerInteractions.SeeOthersInteraction;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.*;

public class InteractiveBoardController extends BoardController {

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
    private Button turnsButton;
    @FXML
    private Button marketBoardButton;
    @FXML
    private Button decksButton;
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
    private Button slotButton1;
    @FXML
    private Button slotButton2;
    @FXML
    private Button slotButton3;
    @FXML
    private Label errorStrongbox;
    @FXML
    private Label errorExtraShelf;
    @FXML
    private MenuButton leader1select;
    @FXML
    private MenuItem INSERT1;
    @FXML
    private MenuItem DISCARD1;
    @FXML
    private MenuButton leader2select;
    @FXML
    private MenuItem INSERT2;
    @FXML
    private MenuItem DISCARD2;
    @FXML
    private MenuButton otherPlayersMenu;

    private List<Coordinates> blackPositions;
    private DecksController decksController;
    private MarketboardController marketboardController;
    private ChosenResourcesController chosenResourcesController;
    private TurnSelectionController turnSelectionController;
    private Accumulator accumulator;
    private BuildMessage builder;

    /**
     * this method is the constructor of the class
     * @param nickname is the name of the player who owns the board
     */
    public InteractiveBoardController(String nickname) {
        super(nickname);
    }

    /**
     * This method is used to initialize the scene
     */
    @FXML
    @Override
    public void initialize(){
        super.initialize();
        turnsButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> onTurnsButtonClicked());
        marketBoardButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event ->onMarketBoardClicked());
        quitButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> onQuitClicked());
        decksButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> onDecksClicked());
        actionButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> notifyInteraction());

        actionButton.setDisable(true);
        errorStrongbox.setVisible(false);
        errorExtraShelf.setVisible(false);

        blackPositions = new ArrayList<>();
        setPositions(blackPositions);
        blackCross.setVisible(false);

        decksController = new DecksController();
        marketboardController = new MarketboardController();
        turnSelectionController = new TurnSelectionController();

        GUIHandler.createHelperWindow(marketboardController,"/FXML/marketboard.fxml", 350, 400);
        marketboardController.hideWindow();

        GUIHandler.createHelperWindow(decksController, "/FXML/decks.fxml", 685, 710);
        decksController.hideWindow();

        GUIHandler.createHelperWindow(turnSelectionController, "/FXML/TurnSelection.fxml", 450, 300);
        turnSelectionController.hideWindow();

        otherPlayersMenu.setVisible(false);

        useWarehouse();
        useExtraShelves();
        useStrongbox();
        useSlots();
        useSlotsButtons();
        useLeaders();
        usePersonalProduction();
        useSwap();
        useLeaderSelection();
        resetTurn();
    }

    /**
     * this helper method is used to notify the action of the player to the controller
     */
    private void notifyInteraction() {
        getGUIReference().notifyInteraction(builder.buildMessage(accumulator));
        resetTurn();
        actionButton.setDisable(true);
    }

    /**
     * this method is called to handle a quit request
     */
    private void onQuitClicked() {
        try {
            String message = MessageFactory.buildDisconnection("Disconnection request.", getModelReference().getPersonalNickname());
            getGUIReference().notifyInteraction(message);
        } catch (MalformedMessageException e){
            e.printStackTrace();
        }
    }

    /**
     * this method is used to notify the willingness of the player of seeing a board of another player
     * @param nickname is the name of the target player
     */
    private void onOtherPlayersClicked(String nickname) {
        getGUIReference().notifyInteraction(new SeeOthersInteraction(nickname));
    }

    /**
     * this method is used to add the name of a player to the otherPlayersMenu
     * @param nickname
     */
    public void addPlayer(String nickname){
        MenuItem item = new MenuItem(nickname);
        otherPlayersMenu.getItems().add(item);
        item.setOnAction(e -> onOtherPlayersClicked(nickname));
        otherPlayersMenu.setVisible(true);
    }

    /**
     * this method is used to handle the request of a player of seeing the MarketBoard
     */
    private void onMarketBoardClicked() {
        marketboardController.showWindow();
    }

    /**
     * this method is used to handle the request of a player of seeing the shared decks
     */
    private void onDecksClicked() {
        decksController.showWindow();
    }

    /**
     * this method is used to handle the request of a player of using the menu
     */
    private void onTurnsButtonClicked() {
        turnSelectionController.showWindow();
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
     * this method is used to update the state of the shared decks
     * @param decks is a map that represents the new state of the shared decks
     */
    public void setDecks(Map<Integer, String> decks){
        decksController.showDecksUpdate(decks);
    }

    /**
     * this method is used to update the state of the MarketBoard
     * @param tray is a list that represents the new state of the marketboard
     */
    public void setMarketBoard(List<Marble> tray){
        marketboardController.showMarketUpdate(tray);
    }

    /**
     * this method is used to set the available turns in the TurnSelection helper window
     * @param turns is a list that represents the available turns
     */
    public void setAvailableTurns(List<String> turns){
        turnSelectionController.setAvailableActions(turns);
        turnSelectionController.showWindow();
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
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, action -> {imageView.setVisible(false);});
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, action -> {
            imageView.setOpacity(0.5);
            imageView.setDisable(true);
            imageView.setVisible(true);
            accumulator.setWarehouse(slot);});
    }

    /**
     * enable or disable the event handlers of the images associated with the warehouse
     * @param b if b=true the events handlers are set as active, otherwise they are set as inactive
     */
    private void enableWarehouse(Boolean b){
        topResource.setDisable(!b);
        topResource.setOpacity(1);
        firstMidResource.setDisable(!b);
        firstMidResource.setOpacity(1);
        secondMidResource.setDisable(!b);
        secondMidResource.setOpacity(1);
        firstBottomResource.setDisable(!b);
        firstBottomResource.setOpacity(1);
        secondBottomResource.setDisable(!b);
        secondBottomResource.setOpacity(1);
        thirdBottomResource.setDisable(!b);
        thirdBottomResource.setOpacity(1);
    }

    /**
     * this method is used to make the strongbox invisible
     */
    private void setExtraShelves(){
        extraShelfString.setVisible(false);
        extraShelf1.setVisible(false);
        extraShelf1Label.setVisible(false);
        extraShelf2.setVisible(false);
        extraShelf2Label.setVisible(false);
    }

    /**
     * this helper method is used to make the extra shelves of the warehouse clickable
     */
    private void useExtraShelves(){
        setExtraShelves();
        helpUseExtraShelf(extraShelf1,extraShelf1Label,4);
        helpUseExtraShelf(extraShelf2,extraShelf2Label,5);
    }

    /**
     * this helper method is used to make an extra shelf of the warehouse clickable
     * @param imageView is the ImageView that represents the extra slot
     * @param label represents the amount of resources in the shelf
     * @param shelf represents the position of the shelf
     */
    private void helpUseExtraShelf(ImageView imageView, Label label, int shelf){
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(0.8));
        visiblePause.setOnFinished(event -> errorExtraShelf.setVisible(false));

        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, action -> {imageView.setVisible(false);});
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, action -> {
            imageView.setVisible(true);
            int i = Integer.parseInt(label.getText());
            if(i == 0) {
                errorExtraShelf.setVisible(true);
                visiblePause.play();
                return;
            }
            i--;
            label.setText(String.valueOf(i));
            accumulator.setWarehouse(shelf);});
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

    /**
     * this helper method is used to make a resource in the Strongbox clickable
     * @param imageView is the ImageView that represents the resource
     * @param label is the amount of that resource
     * @param resource is the name of the resource
     */
    private void helpUseStrongbox(ImageView imageView, Label label, String resource){
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(0.8));
        visiblePause.setOnFinished(event -> errorStrongbox.setVisible(false));

        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, action -> {imageView.setVisible(false);});
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, action -> {
            imageView.setVisible(true);
            int i = Integer.parseInt(label.getText());
            if(i == 0) {
                errorStrongbox.setVisible(true);
                visiblePause.play();
                return;
            }
            i--;
            label.setText(String.valueOf(i));
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
            accumulator.setDevelopmentCards(slot.toString());});
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

    private void useSlotsButtons(){
        helpUseSlotsButtons(slotButton1,1);
        helpUseSlotsButtons(slotButton2,2);
        helpUseSlotsButtons(slotButton3,3);
    }

    private void helpUseSlotsButtons(Button button, Integer slot){

        button.setOnAction(action -> {
            button.setOpacity(0.5);
            enableSlotsButtons(false);
            accumulator.setSlot(slot);});
    }

    private void enableSlotsButtons(Boolean b){
        slotButton1.setDisable(!b);
        slotButton2.setDisable(!b);
        slotButton3.setDisable(!b);
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

    private void enableSwap(Boolean b){
        swap1.setDisable(!b);
        swap2.setDisable(!b);
        swap3.setDisable(!b);
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
            accumulator.setLeaderCards(number.toString());});
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


    private void useLeaderSelection(){
        helpUseLeaderSelection(leader1select,INSERT1,1,"INSERT");
        helpUseLeaderSelection(leader2select,INSERT2,2,"INSERT");
        helpUseLeaderSelection(leader1select,DISCARD1,1,"DISCARD");
        helpUseLeaderSelection(leader2select,DISCARD2,2,"DISCARD");
    }

    private void helpUseLeaderSelection(MenuButton button, MenuItem item, int position, String action){
        item.setOnAction(event ->{
            accumulator.setAction(action);
            accumulator.setLeaderCard(position);
            enableLeaderSelections(false);
            button.setOpacity(0.3);});
    }

    private void enableLeaderSelections(boolean b){
        leader1select.setDisable(!b);
        leader2select.setDisable(!b);
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

        slotButton1.setVisible(false);
        slotButton2.setVisible(false);
        slotButton3.setVisible(false);

        leader1select.setVisible(false);
        leader2select.setVisible(false);
    }

    private void resetImage(ImageView imageView, double opacity){
        imageView.setOpacity(opacity);
    }

    public void initializeLeaders(){
        this.accumulator = new Accumulator(GUIHandler.getGUIReference().getModelReference());
        this.builder = new BuildLeaderUpdate();
        enableLeaderCards(true);
        actionButton.setDisable(false);
    }

    /**
     * sets the correct buttons and images to play the turn
     */
    public void setDoProduction(){
        this.accumulator = new Accumulator(GUIHandler.getGUIReference().getModelReference());
        this.builder = new BuildDoProduction();
        enableWarehouse(true);
        enableStrongbox(true);
        enableLeaderCards(true);
        enableSlots(true);
        personalProduction.setOpacity(1);
        personalProduction.setDisable(false);
        personalProduction.setVisible(true);
        actionButton.setDisable(false);

        chosenResourcesController = new ChosenResourcesController();
        chosenResourcesController.setAccumulator(accumulator);
        GUIHandler.createNonCloseableWindow(chosenResourcesController,"/FXML/chosenResources.fxml", 565, 535);

    }

    /**
     * sets the correct buttons and images to play the turn
     */
    public void setMarketAction(){
        resetTurn();
        marketboardController.setAccumulator();
        marketboardController.activateButtons();
        marketboardController.showWindow();
    }

    /**
     * sets the correct buttons and images to play the turn
     */
    public void setBuyCard(){
        this.accumulator = new Accumulator(GUIHandler.getGUIReference().getModelReference());
        this.builder = new BuildBuyCard();
        decksController.setAccumulator(accumulator);
        enableWarehouse(true);
        enableStrongbox(true);
        enableSlotsButtons(true);
        slotButton1.setVisible(true);
        slotButton2.setVisible(true);
        slotButton3.setVisible(true);
        slotButton1.setOpacity(1);
        slotButton2.setOpacity(1);
        slotButton3.setOpacity(1);
        actionButton.setDisable(false);
    }

    /**
     * sets the correct buttons and images to play the turn
     */
    public void setManageLeader(){
        this.accumulator = new Accumulator(GUIHandler.getGUIReference().getModelReference());
        this.builder = new BuildLeaderAction();
        enableLeaderSelections(true);
        if(firstLeaderCard.isVisible())
            leader1select.setVisible(true);
        if(secondLeaderCard.isVisible())
            leader2select.setVisible(true);
        leader1select.setOpacity(1);
        leader2select.setOpacity(1);
        actionButton.setDisable(false);
    }


    /**
     * sets the correct buttons and images to play the turn
     */
    public void setSwap(){
        this.accumulator = new Accumulator(GUIHandler.getGUIReference().getModelReference());
        this.builder = new BuildSwap();
        enableWarehouse(true);
        enableSwap(true);
        swap1.setVisible(true);
        swap1.setOpacity(1);
        swap2.setVisible(true);
        swap2.setOpacity(1);
        swap3.setVisible(true);
        swap3.setOpacity(1);
        actionButton.setDisable(false);
    }

    //TODO ovverride showwindow e hide window. Aggiungere l'etichetta del player
}

