package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.Resource;
import it.polimi.ingsw.View.CLIColors;

import java.util.LinkedList;

/**
 * This interface represents the marbles and it provides useful methods for managing them.
 */
public interface Marble {

    /**
     *This method allow to add a resource.
     * @param board is the board of the player
     * @param shelf is the shelf in which we want to insert the marble
     * @throws InvalidActionException if the resource is inserted in the wrong shelf.
     */
    void addResource(Board board, int shelf);

    /**
     * This method allow to discard a resource.
     * @param board the board of the player
     */
    void discard(Board board);

    /**
     * This method checks if the the marble passed as parameter is among those allowed
     * @param marble marble
     * @param board the board of the player
     * @return true if the the marble passed as parameter is among those allowed, false otherwise
     */
    boolean checkMarble(Marble marble, Board board);

    /**
     * This method returns a LinkedList of Marble which contains all the marbles that can be used instead of the white one.
     * If there are not marbles that can be used instead of the white one the LinkedList is empty.
     * @param board is the board of the player
     * @return LinkedList of Marble which contains all the marbles that can be used instead of the white one
     */
    LinkedList<Marble> whiteTransformations(Board board);


    /**
     * @return the string associated with the color of the marble
     */
    CLIColors toColor();

    /**
     * @return the resource associated with the marble
     */
    Resource getResourceAssociated();

}
