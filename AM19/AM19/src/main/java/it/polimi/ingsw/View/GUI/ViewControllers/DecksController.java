package it.polimi.ingsw.View.GUI.ViewControllers;

import it.polimi.ingsw.Client.ReducedModel.ReducedConfiguration;
import it.polimi.ingsw.Exceptions.IllegalIDException;
import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

/**
 * this class represents a controller of a decks scene
 */
public class DecksController extends ViewController implements HelperWindow{

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

    /**
     * this method is used to set the current instance of the message builder
     * @param accumulator is the reference to the message builder
     */
    public void setAccumulator(Accumulator accumulator){
        this.accumulator = accumulator;
        enableCards(true);
    }

    /**
     * this method is used to set the properties ImageView that
     * contains the top cards of each shared deck
     */
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

    /**
     * this method is used to make the cards clickable
     * @param status represents the status of the cards.
     * It is true if the cards should be made clickable
     */
    private void enableCards(boolean status){
        card1.setDisable(!status);
        card2.setDisable(!status);
        card3.setDisable(!status);
        card4.setDisable(!status);
        card5.setDisable(!status);
        card6.setDisable(!status);
        card7.setDisable(!status);
        card8.setDisable(!status);
        card9.setDisable(!status);
        card10.setDisable(!status);
        card11.setDisable(!status);
        card12.setDisable(!status);
    }

    /**
     * this method is used to set a card invisible,
     * and to initialize the event associated to the mouse click
     * @param imageView is the node that represents the image
     * @param position is the position of the card on the screen
     */
    private void setCardHandler(ImageView imageView, int position){
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> imageView.setVisible(false));
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            enableCards(false);
            imageView.setVisible(true);
            accumulator.setPosition(position);
            mainPane.getScene().getWindow().hide();});
    }

    /**
     * this method is used to update the top card in each deck
     * @param decks is a map that contains the current state of the shared decks
     */
    public void showDecksUpdate(Map<Integer, String> decks) {
        emptyDecks();
        ReducedConfiguration config = getModelReference().getConfiguration();

        for(int i : decks.keySet()){
            String id = decks.get(i);
            String image = null;
            try {
                image = path + config.getDevelopmentCard(id).getPath();
            } catch (IllegalIDException e) {
                //e.printStackTrace();
            }
            ImageView view = cards.get(i);
            view.setVisible(true);
            view.setImage(new Image(image));
        }
    }

    /**
     * this method is used to hide the decks from the scene
     */
    private void emptyDecks() {
        for (ImageView view : cards) {
            view.setVisible(false);
        }
    }

    /**
     * this method is used to show the helper window
     */
    public void showWindow(){
        ((Stage) mainPane.getScene().getWindow()).show();
    }

    /**
     * this method is used to hide the helper window
     */
    public void hideWindow(){
        mainPane.getScene().getWindow().hide();
    }
}


