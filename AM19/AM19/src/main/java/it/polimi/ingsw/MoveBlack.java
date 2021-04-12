package it.polimi.ingsw;

/**
 * public class representing the action related to the action token which moves Lorenzo ahead of two positions
 */
public class MoveBlack implements  Action{

    /**
     * indicates how many positions the black cross has to be moved
     */
    public int quantity;

    /**
     * creates a new move black token
     * @param quantity indicates how many positions the black cross has to be moved
     */
    public MoveBlack(int quantity) {
        this.quantity = quantity;
    }

    /**
     * moves Lorenzo's black cross ahead of two positions
     * @param singlePlayer
     */
    @Override
    public void doAction(SinglePlayer singlePlayer) {

        singlePlayer.moveBlackCross(quantity);

    }
}
