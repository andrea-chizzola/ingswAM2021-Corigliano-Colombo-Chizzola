package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.InteractionObserver;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Client.MessageSender;
import it.polimi.ingsw.GUI.Gui;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.GUI.ViewControllers.*;
import it.polimi.ingsw.View.GUI.ViewControllers.*;
import it.polimi.ingsw.View.GUI.ViewControllers.EndGameController;
import it.polimi.ingsw.View.GUI.ViewControllers.LoginController;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;
import it.polimi.ingsw.View.SubjectView;
import it.polimi.ingsw.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GUI extends Application implements View, SubjectView {

    //TODO QUI VA LA LISTA DEI CONTROLLER USATI/CHE SERVE RICHIAMARE
    private List<ViewController> controllers = new ArrayList<>();

    private GameBoardController gameBoardController;

    private TurnSelectionController turnSelectionController;

    private LoadingController loadingController;

    DecksController decksController;

    /**
     * this attribute represents an observer of the interactions of a player
     */
    InteractionObserver interactionObserver;

    ReducedGameBoard model;

    @Override
    public void start(Stage stage) throws Exception {

        GUIHandler handler = GUIHandler.instance();

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 575, 534);
        stage.setScene(scene);
        ViewController controller = fxmlLoader.getController();
        controller.attachGUIReference(handler.getGUIinstance());

        stage.show();

        /*try {
            LinkedList<String> test = new LinkedList<>();
            test.add("TAKE_RESOURCES");
            test.add("MANAGE_LEADER");
            TurnSelectionController controller = new TurnSelectionController();
            GUIHandler handler = GUIHandler.instance();
            controller.attachGUIReference(handler.getGUIinstance());
            controller.setAvailableActions(test);

            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/FXML/TurnSelection.fxml"));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load(), 575, 534);
            stage.setScene(scene);
            controller.attachGUIReference(handler.getGUIinstance());

            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

        try {

            MarketboardController controller = new MarketboardController();
            GUIHandler handler = GUIHandler.instance();
            controller.attachGUIReference(handler.getGUIinstance());

        stage.show();

            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/FXML/marketboard.fxml"));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load(), 575, 534);
            stage.setScene(scene);
            controller.attachGUIReference(handler.getGUIinstance());

            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

    public void setLoadingController(LoadingController loadingController) {
        this.loadingController = loadingController;
    }

    @Override
    public void initialize() {

        gameBoardController = new GameBoardController();
        controllers.add(gameBoardController);
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

        GUIHandler handler = GUIHandler.instance();
        TurnSelectionController controller = new TurnSelectionController();
        controller.attachGUIReference(handler.getGUIinstance());
        controller.setAvailableActions(turns);

        Platform.runLater(() ->
                GUIHandler.newWindow(controller,"/FXML/TurnSelection.fxml"));
    }

    @Override
    public void showMarketUpdate(List<Marble> tray) {

    }

    @Override
    public void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, String nickName) {

    }

    @Override
    public void showDecksUpdate(Map<Integer, String> decks) {
        Map<Integer, String> topCards = new HashMap<>();
        decksController = new DecksController();
        topCards.put(1, "/Images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png");
        Platform.runLater(() ->
        {
            GUIHandler.newWindow(decksController, "/FXML/decks.fxml");
            decksController.showDecksUpdate(topCards);
        });
    }

    @Override
    public void showBoxes(List<ResQuantity> warehouse, List<ResQuantity> strongBox, String nickName) {

        if(!nickName.equals(GUIHandler.instance().getModel().getPersonalNickname())) return;

        Platform.runLater(() -> {
            gameBoardController.setResourceWarehouse(warehouse);
            gameBoardController.setResourceStrongbox(strongBox);
        });

    }

    @Override
    public void showSlotsUpdate(Map<Integer, String> slots, String nickName) {

        if(!nickName.equals(GUIHandler.instance().getModel().getPersonalNickname())) return;

        Platform.runLater(() -> gameBoardController.manageDevelopmentCards(slots));

    }

    @Override
    public void showLeaderCards(Map<Integer, String> cards, Map<Integer, ItemStatus> status, String nickName) {

        if(!nickName.equals(GUIHandler.instance().getModel().getPersonalNickname())) return;

        Platform.runLater(() -> gameBoardController.manageLeaderCards(cards, status));

    }

    @Override
    public void showFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections, Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo) {

        String nickname = GUIHandler.instance().getModel().getPersonalNickname();
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

    }

    @Override
    public void selectMarketAction() {

    }

    @Override
    public void leaderAction() {
        //prendo il controller della board principale con il get di GUIHandler
        //chiamo run-later su tale controller (il metodo di leaderAction che attiva alcuni bottoni
        // e ne disattiva altri, e cambia l'istanza di strategy).
        //avere una classe "buffer" insieme ai controller, che viene riempita al click dei bottoni e poi svuotata
        //quando creo il messaggio
    }

    @Override
    public void buyCardAction() {
    }

    @Override
    public void doProductionsAction() {

    }

    @Override
    public void getResourcesAction() {

    }

    @Override
    public void showPersonalProduction() {

    }

    @Override
    public boolean swapAction() {
        return false;
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
