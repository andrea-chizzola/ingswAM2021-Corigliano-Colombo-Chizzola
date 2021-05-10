package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.Coin;
import it.polimi.ingsw.View.CLIColors;

import java.util.LinkedList;

/**
 * This class implements Marble.
 * It represents the yellow marble.
 */
public class MarbleYellow implements Marble {


    /**
     * This method adds the resource Coin to the warehouse on shelf with number 'shelf'.
     * @param board the board of the player
     * @param shelf the number of the self
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Board board, int shelf) throws InvalidActionException {
        try {
            board.getWarehouse().addResource(shelf, new Coin());
        }
        catch(IllegalShelfException e){
            throw new InvalidActionException(e.getMessage());
        }
    }

    /**
     * This method allow to discard the resource.
     * @param board the board of the player
     */
    @Override
    public void discard(Board board){
        board.addFaithToOthers(1);
    }

    @Override
    public LinkedList<Marble> whiteTransformations(Board board) {
        LinkedList<Marble> list = new LinkedList<>();
        return list;
    }

    /**
     * @return the string associated with the color of the marble
     */
    @Override
    public CLIColors toColor() {
        return CLIColors.B_YELLOW;
    }

    @Override
    public String toString() {
        return "MarbleYellow";
    }
}