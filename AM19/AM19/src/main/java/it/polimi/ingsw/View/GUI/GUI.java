package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.ClientController.InteractionObserver;
import it.polimi.ingsw.Client.ReducedModel.ReducedBoard;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Boards.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.GUI.ViewControllers.*;
import it.polimi.ingsw.View.GUI.ViewControllers.EndGameController;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;
import it.polimi.ingsw.View.SubjectView;
import it.polimi.ingsw.View.View;
import javafx.application.Platform;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * this class implements the methods to manage the GUI,
 * and to notify the observers of the View.
 */
public class GUI implements View, SubjectView {

    /**
     * this attribute represents the path of the FXML files
     */
    private final String path = "/FXML/";

    /**
     * this attribute represents a reference to the model
     */
    private final ReducedGameBoard model;

    /**
     * this attribute represents a reference to the observer of the view
     */
    private InteractionObserver interactionObserver;

    /**
     * this attribute represents a reference to the controller of the player's personal board
     */
    private InteractiveBoardController interactiveBoardController;

    /**
     * this attribute contains reference to the view controllers of the board of other players
     */
    private final Map<String, BoardController> playerBoards;

    /**
     * this method is the constructor of the class
     *
     * @param model is a reference to the ReducedModel
     */
    public GUI (ReducedGameBoard model){

        this.model = model;
        playerBoards = new HashMap<>();
    }

    /**
     * @return a reference to the model
     */
    public ReducedGameBoard getModelReference(){
        return model;
    }

    /**
     * this method is used to initialize the state of the view
     */
    @Override
    public void initialize() {
        List<String> nicknames = model.getNicknames();
        String self = model.getPersonalNickname();

        interactiveBoardController = new InteractiveBoardController(self);
        playerBoards.put(self, interactiveBoardController);
        Platform.runLater(() ->
            GUIHandler.loadMainRoot(interactiveBoardController, path + "gameboard.fxml")
        );

        for(String name : nicknames){
            if(!name.equals(self)) {
                BoardController controller = new BoardController(name);
                playerBoards.put(name, controller);
                Platform.runLater( () ->
                        { GUIHandler.createHelperWindow(controller, path + "otherBoards.fxml", 1080, 720);
                            interactiveBoardController.addPlayer(name); });
            }
        }
    }

    /**
     * this method is used to show a reply of the server
     * @param answer is a boolean that tells if the message is an error
     * @param body is the content of the message
     * @param nickName is the receiver of the message
     */
    @Override
    public void reply(boolean answer, String body, String nickName) {
        NotificationController controller = new NotificationController(answer, body);
        Platform.runLater(() ->
                GUIHandler.newWindow(controller,path + "notification.fxml", 400, 250));
    }

    /**
     * this method is used to show a message
     * @param body is the content of the message
     * @param nickName represents the nickname of involved player
     */
    @Override
    public void showGameStatus(String body, String nickName, TurnType state) {
        int nPlayers = model.getNicknames().size();
        int nRes = getInitializationResources();
        String message;

        //these IF are used to improve user experience.
        if(state == TurnType.INITIALIZATION_RESOURCE && nRes == 0) return;
        if(state == TurnType.TURN_SELECTION && nPlayers == 1) message = "Perform your action";
        else message = body;

        Platform.runLater(() ->
                interactiveBoardController.showGameStatus(message));
    }

    /**
     * this method is used to catch the player's selected turn
     * @param turns is the list of available turns
     * @param player is the nickname of the current player
     */
    @Override
    public void showAvailableTurns(List<String> turns, String player) {
        String self = model.getCurrentPlayer();
        if(player.equals(self))
        {
            Platform.runLater(() ->
                    interactiveBoardController.setAvailableTurns(turns));
        }
    }

    /**
     * this method is used to show an update of the marketBoard
     * @param tray is the current state of the market tray
     */
    @Override
    public void showMarketUpdate(List<Marble> tray) {
        Platform.runLater(() -> interactiveBoardController.setMarketBoard(tray));
    }

    /**
     * this method is used to catch an action of the player on a set of selected marbles
     * @param marblesTray represents the selected marbles
     * @param whiteModifications represents the available modifications for
     * @param nickName represents the nickname of the player who made the selection
     */
    @Override
    public void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, String nickName) {
        ReducedBoard board = model.getBoard(nickName);
        int nSlots = board.getWarehouse().size();
        String self = model.getPersonalNickname();
        MarbleSelectionController controller = new MarbleSelectionController();
        if(self.equals(nickName)) {
            Platform.runLater(() -> {
                interactiveBoardController.resetTurn();
                GUIHandler.createNonCloseableWindow(controller, path + "marbleSelection.fxml", 445, 275);
                controller.showMarblesUpdate(marblesTray, whiteModifications, nSlots);
            });
        }
        else{
            Platform.runLater(() -> {
                GUIHandler.newWindow(controller, path + "marbleSelection.fxml", 445, 275);
                controller.showMarblesUpdate(marblesTray, whiteModifications, nSlots);
                controller.disableInteraction();
            });
        }
    }

    /**
     * this method is used to show an update of the shared decks on the GameBoard
     * @param decks contains the top cards of each deck
     */
    @Override
    public void showDecksUpdate(Map<Integer, String> decks) {
        Platform.runLater(() -> interactiveBoardController.setDecks(decks));
    }

    /**
     * this method is used to show an update of one's warehouse and strongbox
     * @param warehouse represent the current state of the warehouse
     * @param strongBox represent the current state of the strongbox
     * @param nickName represents the name of the player affected by the changes
     */
    @Override
    public void showBoxes(List<ResQuantity> warehouse, List<ResQuantity> strongBox, String nickName) {

        BoardController controller = playerBoards.get(nickName);

        Platform.runLater(() -> {
            controller.setResourceWarehouse(warehouse);
            controller.setResourceStrongbox(strongBox);
        });

    }

    /**
     * this method is used to show an update of one's card slots
     * @param slots represent the current state of one's card slots
     * @param nickName represents the nickname of involved player
     */
    @Override
    public void showSlotsUpdate(Map<Integer, String> slots, String nickName) {
        BoardController controller = playerBoards.get(nickName);
        Platform.runLater(() -> controller.manageDevelopmentCards(slots));
    }

    /**
     * this method is used to show an update of one's LeaderCards
     * @param cards represent the current state of one's leader cards
     * @param nickName represents the nickname of involved player
     */
    @Override
    public void showLeaderCards(Map<Integer, String> cards, Map<Integer, ItemStatus> status, String nickName) {
        BoardController controller = playerBoards.get(nickName);
        Platform.runLater(() -> controller.manageLeaderCards(cards, status));
    }

    /**
     * this method is used to show an update of one's FaithTrack
     * @param faith is the amount of faith obtained by the players
     * @param sections is the state of the players' sections
     * @param faithLorenzo is the faith obtained by Lorenzo
     * @param sectionsLorenzo is the status of Lorenzo's sections
     */
    @Override
    public void showFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections, Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo) {

       for(String name : faith.keySet()) {
            BoardController controller = playerBoards.get(name);
            Platform.runLater(() -> {
                controller.changePosition(faith.get(name));
                controller.manageSections(sections.get(name));
            });
        }
        if (faithLorenzo.isPresent() && sectionsLorenzo.isPresent()) {
            Platform.runLater(() -> {
                        interactiveBoardController.visualizeBlackCross();
                        interactiveBoardController.changeBlackPosition(faithLorenzo.get());});
        }
    }

    /**
     * this method is used to show the action token picked by Lorenzo
     * @param action is the ID of the top token
     */
    @Override
    public void showTopToken(Optional<String> action) {
        Platform.runLater(()-> interactiveBoardController.manageTopToken(action));
    }

    /**
     * this method is used to show the points achieved at the end of the game
     * @param players contains the name of the players and the points obtained
     * @param winner contains the name of the winner
     */
    @Override
    public void showEndGame(Map<String, Integer> players, String winner) {

        EndGameController endGameController = new EndGameController();
        Platform.runLater(() -> {
            GUIHandler.newWindow(endGameController,path + "EndGame.fxml", 600, 400);
            endGameController.showEndGame(players,winner);
        });

    }

    /**
     * this method is used to show the disconnection of a player
     * @param nickname is the name of the disconnected player
     */
    @Override
    public void showDisconnection(String nickname) {
        String message = nickname + " has been disconnected from the server";
        NotificationController controller = new NotificationController(true, message);
        Platform.runLater(() ->
                GUIHandler.newWindow(controller,path + "notification.fxml", 400, 250));
    }

    /**
     * this method is used to catch the LeaderCards selected by a player
     */
    @Override
    public void selectLeaderAction() {
        Platform.runLater(()-> interactiveBoardController.initializeLeaders());
    }

    /**
     * this method is used to catch the player's selected row or column of the MarketBoard
     */
    @Override
    public void selectMarketAction() {
        Platform.runLater(()-> interactiveBoardController.setMarketAction());
    }

    /**
     * this method is used to catch the action of a player on a LeaderCard
     */
    @Override
    public void leaderAction() {
        Platform.runLater(()-> interactiveBoardController.setManageLeader());
    }

    /**
     * this method is used to catch the action of a player of a shared DevelopmentCard
     */
    @Override
    public void buyCardAction() {
        Platform.runLater(()-> interactiveBoardController.setBuyCard());
    }

    /**
     * this method is used to catch the action of a player on their productions
     */
    @Override
    public void doProductionsAction() {
        Platform.runLater(()-> interactiveBoardController.setDoProduction());
    }

    /**
     * this action is used to catch the resources chosen by a player
     */
    @Override
    public void getResourcesAction() {

        int number = getInitializationResources();
        InitializeResController controller = new InitializeResController();
        if(number>0) {
            Platform.runLater(() -> {
                GUIHandler.createNonCloseableWindow(controller, path + "initializeResources.fxml", 600, 400);
                controller.initResources(number);
            });
        }
        else{
            controller.noInitializationResources();
        }
    }

    /**
     * this helper method is used to retrieve the number of initialization
     * resources of the player
     *
     * @return the number of initialization resources
     */
    private int getInitializationResources(){
        int playerPosition, number;
        String self = model.getPersonalNickname();
        playerPosition = model.getNicknames().indexOf(self);
        number = model.getConfiguration().getInitialResources().get(playerPosition);
        return number;
    }

    /**
     * this method is used to catch a swap in the Warehouse
     */
    @Override
    public void swapAction() {
        Platform.runLater(()-> interactiveBoardController.setSwap());
    }

    /**
     * this method is used to show the board of a player (different from the current one)
     * @param nickname is the nickname of the target player
     */
    @Override
    public void showOthers(String nickname) {
        if(!playerBoards.containsKey(nickname)) return;
        BoardController controller = playerBoards.get(nickname);
        Platform.runLater(controller::showWindow);
    }

    /**
     * this method is used to undo the player's action
     */
    @Override
    public void undoAction(){
        String self = model.getPersonalNickname();
        List<String> turns = model.getAvailableTurns();
        showAvailableTurns(turns, self);
    }

    /**
     * this method is used to attach a ClientController to a view
     * @param observer is the observer to be attached
     */
    @Override
    public void attachInteractionObserver(InteractionObserver observer) {
        interactionObserver = observer;
    }

    /**
     * this method is used to notify a performed interaction
     * @param interaction is the notified interaction
     */
    @Override
    public void notifyInteraction(PlayerInteraction interaction) {
        interactionObserver.updateInteraction(interaction);
    }

    /**
     * this method is used to notify a performed interaction
     * @param message is the representation of the interaction
     */
    @Override
    public void notifyInteraction(String message) {
        interactionObserver.updateInteraction(message);
    }

    /**
     * this method is used to notify the SOLO game
     *
     * @param message is the representation of the interaction
     */
    @Override
    public void notifySoloInteraction(String message) {
        interactionObserver.updateInteractionSolo(message);
    }

    /**
     * this method is used to notify a performed interaction
     * @param nickname is the name of the player
     */
    @Override
    public void notifyNickname(String nickname) {
        interactionObserver.updatePersonalNickname(nickname);
    }

    /**
     * this method is used to notify a parsing error to the observer
     */
    @Override
    public void notifyParsingError(){
        interactionObserver.close();
    }

}
