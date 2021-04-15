package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.LorenzoWonException;
import it.polimi.ingsw.Exceptions.MarbleWhiteException;
import it.polimi.ingsw.Exceptions.PlayerWonException;
import it.polimi.ingsw.Model.Boards.Board;

/**
 * This interface represents the marbles and it provides useful methods for managing them.
 */
public interface Marble {

    /**
     *This method allow to add a resource.
     * @param board is the board of the player
     * @param shelf is the shelf in which we want to insert the marble
     * @throws InvalidActionException if the resource is inserted in the wrong shelf.
     * @throws MarbleWhiteException if the marble is white.
     */
    void addResource(Board board, int shelf) throws InvalidActionException, MarbleWhiteException, PlayerWonException;

    /**
     * This method allow to discard a resource.
     * @param board the board of the player
     */
    void discard(Board board) throws PlayerWonException, LorenzoWonException;
}
