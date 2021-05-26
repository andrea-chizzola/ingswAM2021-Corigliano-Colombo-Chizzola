package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.InteractionObserver;
import it.polimi.ingsw.Client.MessageSender;
import it.polimi.ingsw.GUI.Gui;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;
import it.polimi.ingsw.View.SubjectView;
import it.polimi.ingsw.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GUI extends Application implements View, SubjectView {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/FXML/loading.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 575, 534);
        stage.setScene(scene);

        GUIHandler handler = GUIHandler.instance();
        handler.setCurrentScene(scene);

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

    }

    @Override
    public void showFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections, Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo) {

    }

    @Override
    public void showTopToken(Optional<String> action) {

    }

    @Override
    public void showEndGame(Map<String, Integer> players) {

    }

    @Override
    public void showDisconnection(String nickname) {

    }

    @Override
    public void newPlayer() {
       /* GUIHandler handler = GUIHandler.instance();
        LoadingController controller = handler.getLoadingController();
        Platform.runLater(controller::startLogin);*/
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
        GUIHandler handler = GUIHandler.instance();
        handler.setInteractionObserver(observer);
    }

    @Override
    public void notifyInteraction(PlayerInteraction interaction) {

    }

    @Override
    public void notifyInteraction(String message) {

    }

    @Override
    public void notifyNickname(String nickname) {

    }

}
