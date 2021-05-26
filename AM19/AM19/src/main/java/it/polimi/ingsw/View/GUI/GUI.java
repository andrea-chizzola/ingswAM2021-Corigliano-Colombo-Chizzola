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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GUI extends Application implements View, SubjectView {

    //QUI VA LA LISTA DEI CONTROLLER USATI/CHE SERVE RICHIAMARE


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/FXML/login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 575, 534);
        stage.setScene(scene);

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

    /**
     * this method is used to change the look of a given scene
     *
     * @param scene is the scene to be modified
     * @param fxmlPath is the path of the FXML file
     * @param <T> is the type of the returned controller
     * @return the controller of the scene
     */
    public static <T> T loadRoot(Scene scene, String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));

        Pane parent;

        try {
            parent = fxmlLoader.load();
            scene.setRoot(parent);
        } catch (IOException e) {
            //Chiudo il client
        }

        return fxmlLoader.getController();
    }

    /**
     * this method is used to copen a new window
     *
     * @param fxmlPath is the path of the FXML file
     * @param <T> is the type of the returned controller
     * @return the controller of the scene
     */
    public static <T> T newWindow(String fxmlPath){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));

        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 500, 500));
            stage.show();

            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
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
