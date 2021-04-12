package it.polimi.ingsw;

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
     * @param singlePlayer
     */
    @Override
    public void doAction(SinglePlayer singlePlayer) {



    }
}
