package it.polimi.ingsw;

import java.util.Objects;

/**
 * this class represents the Discard action of Lorenzo il Magnifico.
 */
public class Discard implements Action{

    /**
     * represents the color of the cards that have to be discarded
     */
    CardColor cardColor;

    /**
     * indicates how many cards have to be discarded
     */
    int quantity;

    public Discard(CardColor cardColor, int quantity) {
        this.cardColor = cardColor;
        this.quantity = quantity;
    }

    /**
     * performs the action related to the action token picked
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    @Override
    public void doAction(SinglePlayer singlePlayer) {



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
