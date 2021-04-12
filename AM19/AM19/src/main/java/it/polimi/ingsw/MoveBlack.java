package it.polimi.ingsw;

/**
 * public class representing the action related to the action token which moves Lorenzo ahead of two positions
 */
public class MoveBlack implements  Action{

    /**
     * moves Lorenzo's black cross ahead of two positions
     * @param singlePlayer
     */
    @Override
    public void doAction(SinglePlayer singlePlayer) {

        singlePlayer.moveBlackCross(2);

    }
}
