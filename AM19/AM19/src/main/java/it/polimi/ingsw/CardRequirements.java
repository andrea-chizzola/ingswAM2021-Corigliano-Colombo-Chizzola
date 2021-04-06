package it.polimi.ingsw;

import java.util.LinkedList;
import java.util.Objects;

/**
 * this class represents the requirements of a LeaderCard.
 * It contains all the characteristics of all the cards required to activate the LeaderCard.
 */
public class CardRequirements implements Requirements{
    /**
     * this attribute is a list of the characteristics of the required cards
     */
    LinkedList<CardQuantity> cards;

    public CardRequirements(LinkedList<CardQuantity> cards){
        this.cards = new LinkedList<>();
        this.cards.addAll(cards);
    }

    @Override
    public boolean checkReq(Board board) {
        return false;
    }

    @Override
    public void buyCard(Board board) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardRequirements that = (CardRequirements) o;
        return Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }
}
