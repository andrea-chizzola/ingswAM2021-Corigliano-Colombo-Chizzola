package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.LorenzoWonException;
import it.polimi.ingsw.Exceptions.MarbleWhiteException;
import it.polimi.ingsw.Exceptions.PlayerWonException;
import it.polimi.ingsw.Model.Boards.Board;

/**
 * This class implements Marble.
 * It represents the red marble.
 */
public class MarbleRed implements Marble {

    /**
     * This method adds one faith point to the FaithTrack
     * @param board the board of the player
     * @param shelf not used
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Board board, int shelf) throws InvalidActionException, MarbleWhiteException, PlayerWonException {
        board.addFaith(1);
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
        return "MarbleRed";
    }
}