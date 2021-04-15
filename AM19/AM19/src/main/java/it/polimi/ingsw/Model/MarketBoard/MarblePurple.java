package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.Servant;

/**
 * This class implements Marble.
 * It represents the purple marble.
 */
public class MarblePurple implements Marble {


    /**
     * This method adds the resource Servant to the warehouse on shelf with number 'shelf'.
     * @param board the board of the player
     * @param shelf the number of the self
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Board board, int shelf) throws InvalidActionException, MarbleWhiteException {
        try {
            board.getWarehouse().addResource(shelf, new Servant());
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
    public void discard(Board board) throws PlayerWonException, LorenzoWonException {
        board.addFaithToOthers(1);
    }

    @Override
    public String toString() {
        return "MarblePurple";
    }
}