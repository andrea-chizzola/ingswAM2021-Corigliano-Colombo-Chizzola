package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Objects;

/**
 * abstract class representing the common features of both Leader cards and Development cards
 */
public abstract class Card{

    /**
     * victoryPoint represents the victory points associated to a card
     * specialEffect represents a production in case of a development card or a special effect in case of a leader card
     * requirements represents the necessary requirements to buy a card
     */
    private int victoryPoint;
    private SpecialEffect specialEffect;
    private Requirements requirements;

    /**
     * @param victoryPoint indicates the victory points associated to the card
     * @param specialEffect indicates the production of a development card or the special effect of a leader card
     * @param requirements indicates the necessary requirements to buy the card
     */
    public Card(int victoryPoint, SpecialEffect specialEffect, Requirements requirements) {

        this.victoryPoint = victoryPoint;
        this.specialEffect = specialEffect;
        this.requirements = requirements;

    }


    /**
     * @return returns the victory points associated to the card
     */
    public int getVictoryPoint(){
        return victoryPoint;
    }

    /**
     * @return the special effect of the target card.
     */
    public SpecialEffect getSpecialEffect(){
        return specialEffect;
    }

    /**
     *
     * @param board
     * @return
     * @throws InvalidActionException
     * @throws ResourcesExpectedException
     */
    public boolean checkReq(Board board) throws InvalidActionException, ResourcesExpectedException{

        requirements.checkReq(board);
        return true;
    }
    /**
     * This method checks if the requirements of the card are met, otherwise an InvalidActionException is thrown.
     * @param board
     * @param shelves
     * @param quantity
     * @param strongbox
     * @return true if the player related to the parameter board has the necessary requirements to buy the card
     * @throws InvalidActionException
     */
     public boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException{

         try {
             requirements.checkReq(board,shelves,quantity,strongbox);
         }
         catch (InvalidActionException e){throw e;}
         return true;
     }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return victoryPoint == card.victoryPoint &&
                specialEffect.equals(card.specialEffect) &&
                requirements.equals(card.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(victoryPoint, specialEffect, requirements);
    }

    //checkReq() method missing


}
