package it.polimi.ingsw;

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
    private  SpecialEffect specialEffect;
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

    //checkReq() method missing


}