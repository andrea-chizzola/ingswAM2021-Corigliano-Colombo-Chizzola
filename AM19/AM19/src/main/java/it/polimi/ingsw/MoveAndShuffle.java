package it.polimi.ingsw;

/**
 * public class representing the action related to the action token which moves Lorenzo ahead of one position and shuffles the action token deck
 */
public class MoveAndShuffle implements Action{

    /**
     * moves Lorenzo's black cross ahead of one position and shuffles the action token deck (the used tokens and the unused ones together)
     * @param singlePlayer
     */
    @Override
    public void doAction(SinglePlayer singlePlayer) {

        singlePlayer.moveBlackCross(1);

        singlePlayer.getActionTokenDeck().mergeAndShuffle();

    }
}
