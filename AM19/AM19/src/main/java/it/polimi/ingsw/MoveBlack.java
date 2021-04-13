package it.polimi.ingsw;

import java.util.Objects;

/**
 * public class representing the action related to the action token which moves Lorenzo ahead of two positions
 */
public class MoveBlack implements  Action{
    /**
     * this attribute represents the amount of faith points Lorenzo gains
     */
    int amount;

    public MoveBlack(int amount){
        this.amount=amount;
    }

    /**
     * moves Lorenzo's black cross ahead of two positions
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    @Override
    public void doAction(SinglePlayer singlePlayer) {

        singlePlayer.moveBlackCross(amount);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveBlack moveBlack = (MoveBlack) o;
        return amount == moveBlack.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
