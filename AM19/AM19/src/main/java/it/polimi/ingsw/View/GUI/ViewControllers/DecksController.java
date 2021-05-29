package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.PlayerInteractions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

    @FXML
    public void initialize() {
        cards = List.of(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12);
        emptyDecks();
    }

    public void showDecksUpdate(Map<Integer, String> decks) {
        emptyDecks();
        for (int i : decks.keySet()) {
            ImageView view = cards.get(i);
            view.setVisible(true);
            view.setImage(new Image(decks.get(i)));
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


