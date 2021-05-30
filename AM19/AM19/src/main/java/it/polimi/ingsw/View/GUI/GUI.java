package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.InteractionObserver;
import it.polimi.ingsw.Client.ReducedModel.ReducedConfiguration;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.ResourceColor;
import it.polimi.ingsw.View.GUI.ViewControllers.*;
import it.polimi.ingsw.View.GUI.ViewControllers.EndGameController;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;
import it.polimi.ingsw.View.SubjectView;
import it.polimi.ingsw.View.View;
import javafx.application.Platform;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GUI implements View, SubjectView {

    //TODO QUI VA LA LISTA DEI CONTROLLER USATI/CHE SERVE RICHIAMARE
    private List<ViewController> controllers = new ArrayList<>();

    private GameBoardController gameBoardController;

    private TurnSelectionController turnSelectionController;

    private LoadingController loadingController;

    private DecksController decksController;

    private ReducedGameBoard model;

    private InteractionObserver interactionObserver;

    //TODO: le immagini dovrebbero essere ricostruite dalla GUI. Ai controller dovrebbero arrivare i path già pronti

    private final String path = "/Images/front/";

    public GUI (ReducedGameBoard model){
        this.model = model;
    }

    public ReducedGameBoard getModelReference(){
        return model;
    }

    public void setLoadingController(LoadingController loadingController) {
        this.loadingController = loadingController;
    }

    @Override
    public void initialize() {

        gameBoardController = new GameBoardController();
        controllers.add(gameBoardController);
        gameBoardController.attachGUIReference(this);
        gameBoardController.attachModelReference(model);
        Platform.runLater(() -> GUIHandler.newWindow(gameBoardController, "/FXML/gameboard.fxml"));

    }

    @Override
    public void reply(boolean answer, String body, String nickName) {

    }

    @Override
    public void showGameStatus(boolean answer, String body, String nickName, TurnType state) {

    }

    @Override
    public void showAvailableTurns(List<String> turns, String player) {

        TurnSelectionController controller = new TurnSelectionController();
        controller.attachGUIReference(this);
        controller.attachModelReference(model);
        controller.setAvailableActions(turns);

        Platform.runLater(() ->
                GUIHandler.newWindow(controller,"/FXML/TurnSelection.fxml"));
    }

    @Override
    public void showMarketUpdate(List<Marble> tray) {
        Platform.runLater(() -> gameBoardController.setMarketBoard(tray));
    }

    @Override
    public void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, String nickName) {
        int whites=0;
        List<String> marbles = new LinkedList<>();
        for(int i=0; i<marblesTray.size(); i++){
            if(marblesTray.get(i).getResourceAssociated().getColor() == ResourceColor.WHITE){
                whites++;
            }
            else marbles.add(getMarbleImage(marblesTray.get(i)));
        }
        List<String> transformations = whiteModifications.stream().map(Marble::toString).collect(Collectors.toList());
        MarbleSelectionController controller = new MarbleSelectionController();
        controller.attachGUIReference(this);
        controller.attachModelReference(model);
        int finalWhites = whites;
        Platform.runLater(()->{
            GUIHandler.newWindow(controller, "/FXML/marbleSelection.fxml");
            controller.showMarblesUpdate(marbles, transformations, finalWhites);
        });
    }

    private String getMarbleImage(Marble marble){
        switch(marble.getResourceAssociated().getColor()){
            case RED: {
                return "MarbleRed.PNG";
            }
            case BLUE: {
                return "MarbleBlue.PNG";
            }
            case GRAY: {
                return "MarbleGray.PNG";
            }
            case WHITE: {
                return "MarbleWhite.PNG";
            }
            case PURPLE: {
                return "MarblePurple.PNG";
            }
            case YELLOW: {
                return "MarbleYellow.PNG";
            }
            default:
                return "MarbleWhite.PNG";
        }
    }

    @Override
    public void showDecksUpdate(Map<Integer, String> decks) {
        Map<Integer, String> topCards = new HashMap<>();
        ReducedConfiguration config = model.getConfiguration();
        for(int i : decks.keySet()){
            String id = decks.get(i);
            String image = path + config.getDevelopmentCard(id).getPath();
            topCards.put(i, image);
        }

        Platform.runLater(() -> gameBoardController.setDecks(topCards));
    }

    @Override
    public void showBoxes(List<ResQuantity> warehouse, List<ResQuantity> strongBox, String nickName) {

        if(!nickName.equals(model.getPersonalNickname())) return;

        Platform.runLater(() -> {
            gameBoardController.setResourceWarehouse(warehouse);
            gameBoardController.setResourceStrongbox(strongBox);
        });

    }

    @Override
    public void showSlotsUpdate(Map<Integer, String> slots, String nickName) {

        if(!nickName.equals(model.getPersonalNickname())) return;

        Platform.runLater(() -> gameBoardController.manageDevelopmentCards(slots));

    }

    @Override
    public void showLeaderCards(Map<Integer, String> cards, Map<Integer, ItemStatus> status, String nickName) {

        if(!nickName.equals(model.getPersonalNickname())) return;

        Platform.runLater(() -> gameBoardController.manageLeaderCards(cards, status));

    }

    @Override
    public void showFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections, Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo) {

        String nickname = model.getPersonalNickname();
        Platform.runLater(() -> {
            gameBoardController.changePosition(faith.get(nickname));
            gameBoardController.manageSections(sections.get(nickname));
            if(faithLorenzo.isPresent() && sectionsLorenzo.isPresent()){
                gameBoardController.visualizeBlackCross();
                gameBoardController.changeBlackPosition(faithLorenzo.get());
            }
        });

    }

    @Override
    public void showTopToken(Optional<String> action) {
        Platform.runLater(()-> gameBoardController.manageTopToken(action));
    }

    @Override
    public void showEndGame(Map<String, Integer> players) {

        EndGameController endGameController = new EndGameController();
        Platform.runLater(() -> {
            GUIHandler.newWindow(endGameController,"/FXML/EndGame.fxml");
            endGameController.showEndGame(players);
        });

    }

    @Override
    public void showDisconnection(String nickname) {

        DisconnectionController disconnectionController = new DisconnectionController(nickname);
        Platform.runLater(() -> GUIHandler.newWindow(disconnectionController, "/FXML/disconnection.fxml"));

    }

    @Override
    public void selectLeaderAction() {
        Platform.runLater(()-> gameBoardController.initializeLeaders());
    }

    @Override
    public void selectMarketAction() {
        Platform.runLater(()-> gameBoardController.setMarketAction());
    }

    @Override
    public void leaderAction() {
        Platform.runLater(()-> gameBoardController.setManageLeader());
    }

    @Override
    public void buyCardAction() {
        Platform.runLater(()-> gameBoardController.setBuyCard());
    }

    @Override
    public void doProductionsAction() {
        Platform.runLater(()-> gameBoardController.setDoProduction());
    }

    @Override
    public void getResourcesAction() {

        int playerPosition, number;
        String self = model.getPersonalNickname();
        playerPosition = model.getNicknames().indexOf(self);
        number = model.getConfiguration().getInitialResources().get(playerPosition);

        InitializeResController controller = new InitializeResController();
        controller.attachGUIReference(this);
        controller.startInitialization(number);

        if(number>0) {
            Platform.runLater(() -> {
                GUIHandler.newWindow(controller, "/FXML/initializeResources.fxml");
                controller.initResources(number);
            });
        }

    }

    @Override
    public void showPersonalProduction() {

    }

    @Override
    public boolean swapAction() {
        Platform.runLater(()-> gameBoardController.setSwap());
        return true;
    }

    @Override
    public void attachInteractionObserver(InteractionObserver observer) {
        interactionObserver = observer;
    }

    @Override
    public void notifyInteraction(PlayerInteraction interaction) {
        interactionObserver.updateInteraction(interaction);
    }

    @Override
    public void notifyInteraction(String message) {
        interactionObserver.updateInteraction(message);
    }

    @Override
    public void notifyNickname(String nickname) {
        interactionObserver.updatePersonalNickname(nickname);
    }


    public void showLoadingScreen(){

        LoadingController loadingController = new LoadingController();
        //Platform.runLater(() -> loadRoot(loadingController.getScene(), "/FXML/loading.fxml"));

    }

    public DecksController getDecksController() {
        return decksController;
    }

    public void setDecksController(DecksController decksController) {
        this.decksController = decksController;
    }
}
