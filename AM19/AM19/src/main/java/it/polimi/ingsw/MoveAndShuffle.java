package it.polimi.ingsw;

/**
 * public class representing the action related to the action token which moves Lorenzo ahead of one position and shuffles the action token deck
 */
public class MoveAndShuffle implements Action{

    /**
     * indicates how many positions the black cross has to be moved
     */
    public int quantity;

    /**
     * creates a new move black and shuffle token
     * @param quantity indicates how many positions the black cross has to be moved
     */
    public MoveAndShuffle(int quantity) {
        this.quantity = quantity;
    }

    /**
     * moves Lorenzo's black cross ahead of one position and shuffles the action token deck (the used tokens and the unused ones together)
     * @param singlePlayer
     */
    @Override
    public void doAction(SinglePlayer singlePlayer) {

        singlePlayer.moveBlackCross(quantity);

        singlePlayer.getActionTokenDeck().mergeAndShuffle();

    }
}
