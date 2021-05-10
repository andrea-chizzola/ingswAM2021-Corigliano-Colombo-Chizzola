package it.polimi.ingsw.Model.ActionTokens;

import it.polimi.ingsw.Model.Boards.SinglePlayer;

import java.util.Objects;

/**
 * public class representing the action related to the action token which moves Lorenzo ahead of one position and shuffles the action token deck
 */
public class MoveAndShuffle extends Action{

    /**
     * this attribute represents the amount of faith points Lorenzo gains
     */
    int amount;

    /**
     * creates a new move black and shuffle token
     * @param amount indicates how many positions the black cross has to be moved
     */
    public MoveAndShuffle(int amount, String id){
        super(id);
        this.amount=amount;
    }

    /**
     * moves Lorenzo's black cross ahead of one position and shuffles the action token deck (the used tokens and the unused ones together)
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    @Override
    public void doAction(SinglePlayer singlePlayer){

        singlePlayer.moveBlackCross(amount);
        singlePlayer.getActionTokenDeck().mergeAndShuffle();

    }

    @Override
    public String toString(){
        return "Action Token: " + "\n" +
                "Move&Shuffle - " + amount;
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
