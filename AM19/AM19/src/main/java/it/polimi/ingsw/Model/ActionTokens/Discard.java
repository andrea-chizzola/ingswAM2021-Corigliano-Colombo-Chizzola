package it.polimi.ingsw.Model.ActionTokens;

import it.polimi.ingsw.Model.Boards.SinglePlayer;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.Objects;

/**
 * this class represents the Discard action of Lorenzo il Magnifico.
 */
public class Discard extends Action {

    /**
     * represents the color of the cards that have to be discarded
     */
    private CardColor cardColor;

    /**
     * indicates how many cards have to be discarded
     */
    private int quantity;

    /**
     * created a new discard token
     * @param cardColor represents the color of the card that will be discarded
     * @param quantity indicated how many cards will be discarded
     * @param id represents the token's id
     * @param image represents the token's image
     */
    public Discard(CardColor cardColor, int quantity, String id, String image) {
        super(id, image);
        this.cardColor = cardColor;
        this.quantity = quantity;
    }

    /**
     * performs the action related to the action token picked
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    @Override
    public void doAction(SinglePlayer singlePlayer){

        singlePlayer.getGameBoard().getDevelopmentDeck().extract(quantity, cardColor);

        if(!singlePlayer.getGameBoard().getDevelopmentDeck().isColorAvailable(cardColor)){
            singlePlayer.getGameBoard().setEndGameStarted(true);
        }

        singlePlayer.getActionTokenDeck().changeList();

    }

    @Override
    public String toString(){
        return "Action Token: " + "\n" +
                "Discard: " + "\n" +
                quantity + " " + cardColor.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discard discard = (Discard) o;
        return quantity == discard.quantity &&
                Objects.equals(cardColor, discard.cardColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardColor, quantity);
    }
}
