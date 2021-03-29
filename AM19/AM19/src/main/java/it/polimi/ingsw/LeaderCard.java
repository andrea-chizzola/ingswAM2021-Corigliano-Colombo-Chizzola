package it.polimi.ingsw;

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
     * @param status when created, a leader card is always unactivated
     */
    public LeaderCard(int victoryPoint, SpecialEffect specialEffect, Requirements requirements, boolean status){

        super(victoryPoint, specialEffect, requirements);
        this.status = false;

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
}
