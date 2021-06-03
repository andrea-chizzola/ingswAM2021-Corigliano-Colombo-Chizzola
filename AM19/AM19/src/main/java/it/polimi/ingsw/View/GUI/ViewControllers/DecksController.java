package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Client.ReducedModel.ReducedConfiguration;
import it.polimi.ingsw.Exceptions.IllegalIDException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import it.polimi.ingsw.View.PlayerInteractions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DecksController extends ViewController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView card1;

    @FXML
    private ImageView card2;

    @FXML
    private ImageView card3;

    @FXML
    private ImageView card4;

    @FXML
    private ImageView card5;

    @FXML
    private ImageView card6;

    @FXML
    private ImageView card7;

    @FXML
    private ImageView card8;

    @FXML
    private ImageView card9;

    @FXML
    private ImageView card10;

    @FXML
    private ImageView card11;

    @FXML
    private ImageView card12;

    private List<ImageView> cards;
    private Accumulator accumulator;
    private final String path = "/Images/front/";

    @FXML
    public void initialize() {
        cards = List.of(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12);
        emptyDecks();
        setDecks();
    }

    public void setAccumulator(Accumulator accumulator){
        this.accumulator = accumulator;
        enableCards(true);
    }

    private void setDecks(){
        enableCards(false);
        setCardHandler(card1,1);
        setCardHandler(card2,2);
        setCardHandler(card3,3);
        setCardHandler(card4,4);
        setCardHandler(card5,5);
        setCardHandler(card6,6);
        setCardHandler(card7,7);
        setCardHandler(card8,8);
        setCardHandler(card9,9);
        setCardHandler(card10,10);
        setCardHandler(card11,11);
        setCardHandler(card12,12);
    }

    private void enableCards(boolean b){
        card1.setDisable(!b);
        card2.setDisable(!b);
        card3.setDisable(!b);
        card4.setDisable(!b);
        card5.setDisable(!b);
        card6.setDisable(!b);
        card7.setDisable(!b);
        card8.setDisable(!b);
        card9.setDisable(!b);
        card10.setDisable(!b);
        card11.setDisable(!b);
        card12.setDisable(!b);

    }

    private void setCardHandler(ImageView imageView, int position){
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> imageView.setVisible(false));
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            enableCards(false);
            imageView.setVisible(true);
            accumulator.setPosition(position);
            mainPane.getScene().getWindow().hide();});
    }

    public void showDecksUpdate(Map<Integer, String> decks) {
        emptyDecks();
        ReducedConfiguration config = getModelReference().getConfiguration();

        for(int i : decks.keySet()){
            String id = decks.get(i);
            String image = null;
            try {
                image = path + config.getDevelopmentCard(id).getPath();
            } catch (IllegalIDException e) {
                e.printStackTrace();
            }
            ImageView view = cards.get(i);
            view.setVisible(true);
            view.setImage(new Image(image));
        }
    }

    private void emptyDecks() {
        for (ImageView view : cards) {
            view.setVisible(false);
        }
    }

    public void showWindow(){
        ((Stage) mainPane.getScene().getWindow()).show();
    }

    public void hideWindow(){
        mainPane.getScene().getWindow().hide();
    }
}


