package it.polimi.ingsw.Model.ActionTokens;

import it.polimi.ingsw.Model.Boards.SinglePlayer;

import java.util.Objects;

/**
 * public class representing the action related to the action token which moves Lorenzo ahead of two positions
 */
public class MoveBlack extends  Action{

    /**
     * this attribute represents the amount of faith points Lorenzo gains
     */
    int amount;

    /**
     * creates a new move black token
     * @param amount indicates how many positions the black cross has to be moved
     */
    public MoveBlack(int amount, String id, String image ) {
        super(id, image);
        this.amount = amount;
    }

    /**
     * moves Lorenzo's black cross ahead of two positions
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    @Override
    public void doAction(SinglePlayer singlePlayer){

        singlePlayer.moveBlackCross(amount);
        singlePlayer.getActionTokenDeck().changeList();

    }

    @Override
    public String toString(){
        return "Action Token: " + "\n" +
                "Move - " + amount;
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
