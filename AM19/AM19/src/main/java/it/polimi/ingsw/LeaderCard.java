package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Objects;

/**
 * public class representing leader cards and inheriting from abstract class Card
 */
public class LeaderCard extends Card{

    /**
     * status is true only if the leader card is currently activated, false otherwise
     */
    private boolean status;

    /**
     * @param victoryPoint indicates the victory points associated to the card
     * @param specialEffect indicates the production of a development card or the special effect of a leader card
     * @param requirements indicates the necessary requirements to buy the card
     */
    public LeaderCard(int victoryPoint, SpecialEffect specialEffect, Requirements requirements){

        super(victoryPoint, specialEffect, requirements);
        this.status = false;

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
    @Override
    public boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException {
        return super.checkReq(board, shelves, quantity, strongbox);
    }

    /**
     * @return returns true if the leader card is currently activated
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * @param status sets the status of the leader card as activated or deactivated
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LeaderCard that = (LeaderCard) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }
}
