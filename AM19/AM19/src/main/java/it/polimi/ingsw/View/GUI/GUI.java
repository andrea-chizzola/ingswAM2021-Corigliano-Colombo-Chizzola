package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.InteractionObserver;
import it.polimi.ingsw.Client.MessageSender;
import it.polimi.ingsw.GUI.Gui;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.GUI.ViewControllers.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GUI extends Application implements View, SubjectView {

    //TODO QUI VA LA LISTA DEI CONTROLLER USATI/CHE SERVE RICHIAMARE
    private List<ViewController> controllers = new ArrayList<>();

    private GameBoardController gameBoardController;

    private TurnSelectionController turnSelectionController;

    /**
     * this attribute represents an observer of the interactions of a player
     */
    InteractionObserver interactionObserver;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/FXML/login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 575, 534);
        stage.setScene(scene);
        LoginController controller = fxmlLoader.getController();
        GUIHandler handler = GUIHandler.instance();
        controller.attachGUIReference(handler.getGUIinstance());

        stage.show();
    }

    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void initialize() {

        GameBoardController gameBoardController = new GameBoardController();
        controllers.add(gameBoardController);
        Platform.runLater(() -> GUIHandler.loadRoot(gameBoardController.getScene(), gameBoardController, "/FXML/gameboard.fxml"));

    }

    @Override
    public void reply(boolean answer, String body, String nickName) {

    }

    @Override
    public void showGameStatus(boolean answer, String body, String nickName, TurnType state) {

    }

    @Override
    public void showAvailableTurns(List<String> turns, String player) {

    }

    @Override
    public void showMarketUpdate(List<Marble> tray) {

    }

    @Override
    public void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, String nickName) {

    }

    @Override
    public void showDecksUpdate(Map<Integer, String> decks) {

    }

    @Override
    public void showBoxes(List<ResQuantity> warehouse, List<ResQuantity> strongBox, String nickName) {

    }

    @Override
    public void showSlotsUpdate(Map<Integer, String> slots, String nickName) {

    }

    @Override
    public void showLeaderCards(Map<Integer, String> cards, Map<Integer, ItemStatus> status, String nickName) {

        //if(!nickName.equals(GUIHandler.instance().getModel().getPersonalNickname())) return;

        GameBoardController gameBoardController = new GameBoardController();
        Platform.runLater(() -> {
            GUIHandler.newWindow(gameBoardController, "/FXML/gameboard.fxml");
            gameBoardController.manageLeaderCards(cards, status);
        });

    }

    @Override
    public void showFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections, Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo) {

        String nickname = GUIHandler.instance().getModel().getPersonalNickname();
    }

    @Override
    public void showTopToken(Optional<String> action) {

    }

    @Override
    public void showEndGame(Map<String, Integer> players) {

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

}
