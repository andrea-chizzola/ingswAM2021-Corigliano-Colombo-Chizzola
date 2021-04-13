package it.polimi.ingsw;

import java.util.Objects;

/**
 * public class representing the action related to the action token which moves Lorenzo ahead of one position and shuffles the action token deck
 */
public class MoveAndShuffle implements Action{
    /**
     * this attribute represents the amount of faith points Lorenzo gains
     */
    int amount;

    /**
     * creates a new move black and shuffle token
     * @param amount indicates how many positions the black cross has to be moved
     */
    public MoveAndShuffle(int amount){
        this.amount=amount;
    }

    /**
     * moves Lorenzo's black cross ahead of one position and shuffles the action token deck (the used tokens and the unused ones together)
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    @Override
    public void doAction(SinglePlayer singlePlayer) {

        singlePlayer.moveBlackCross(amount);

        singlePlayer.getActionTokenDeck().mergeAndShuffle();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveAndShuffle that = (MoveAndShuffle) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
