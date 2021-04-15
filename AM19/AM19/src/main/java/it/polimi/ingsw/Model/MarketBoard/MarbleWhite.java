package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.LorenzoWonException;
import it.polimi.ingsw.Exceptions.MarbleWhiteException;
import it.polimi.ingsw.Exceptions.PlayerWonException;
import it.polimi.ingsw.Model.Boards.Board;

/**
 * This class implements Marble.
 * It represents the white marble.
 */
public class MarbleWhite implements Marble {

    /**
     * This method manages the actions of white marble.
     * @param board the board of the player
     * @param shelf not used
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Board board, int shelf) throws InvalidActionException, MarbleWhiteException {
        throw new MarbleWhiteException();
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
        return "MarbleWhite";
    }
}