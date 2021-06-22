package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.*;

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
     * @param board represents the board associated to a player
     * @return true if the check is successful
     * @throws InvalidActionException if the card requirements are not met
     */
    @Override
    public boolean checkReq(Board board) throws InvalidActionException{

        for(CardQuantity cardQuantity : cards)
            board.checkCards(cardQuantity.getCardColor(),cardQuantity.getLevel(),cardQuantity.getQuantity());
        return true;
    }

    /**
     * This method checks if the cards owned by the player meet the requirements to activate the Leader Card.
     * @param board represents the board associated to a player
     * @param shelves
     * @param quantity
     * @param strongbox
     * @return true if the check is successful
     * @throws InvalidActionException if the card requirements are not met
     */
    @Override
    public boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException {
        return checkReq(board);
    }

    /**
     * @return HasMap(Resource,Integer) with all the resources present in the requirements
     */
    @Override
    public HashMap<Resource, Integer> getRequirements() {
        HashMap<Resource,Integer> map = new HashMap<>();
        return map;
    }

    /**
     * @return LinkedList of CardQuantity with all the cards present in the requirements
     */
    public LinkedList<CardQuantity> getCardRequirements(){
        LinkedList<CardQuantity> list = new LinkedList<>();
        list.addAll(cards);
        return list;
    }

    @Override
    public String toString(){
        StringBuilder req = new StringBuilder();
        for(int i=0; i<cards.size(); i++){
            req.append(cards.get(i).toString()).append("\n");
        }
        return  "+ Card Requirement: \n" +
                req;
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
