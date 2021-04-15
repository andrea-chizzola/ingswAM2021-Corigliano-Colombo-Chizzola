package it.polimi.ingsw;

import java.util.ArrayList;
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

    /**
     * This method checks if the cards owned by the player meet the requirements to activate the Leader Card.
     * An InvalidActionException is thrown if the requirements are not met.
     * @param board represents the board associated to a player
     * @return true if the check is successful
     * @throws InvalidActionException if the number of owned cards is not sufficient
     */
    @Override
    public boolean checkReq(Board board) throws InvalidActionException{

        for(CardQuantity cardQuantity : cards)
            board.checkCards(cardQuantity.getCardColor(),cardQuantity.getLevel(),cardQuantity.getQuantity());
        return true;
    }

    @Override
    public boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException {
        return checkReq(board);
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
