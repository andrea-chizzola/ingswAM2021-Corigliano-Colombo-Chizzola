package it.polimi.ingsw.Model.Turn;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.ArrayList;

/**
 * Turn is an interface which  provides the methods needed to perform the different possibilities of actions during the turn.
 */
public abstract class Turn {

    /**
     * This method allows to take a specific row from the MarketBoard.
     * @param board
     * @param row
     * @return
     * @throws InvalidActionException
     */
    public ArrayList<Marble> takeMarketRow(Board board, int row) throws InvalidActionException{
        return new ArrayList<>();
    }

    /**
     * This method allows to take a specific column from the MarketBoard.
     * @param board
     * @param column
     * @return
     * @throws InvalidActionException
     */
    public ArrayList<Marble> takeMarketColumn(Board board, int column) throws InvalidActionException{
        return new ArrayList<>();
    }

    /**
     * This method allows to insert the resource, specified by the marble, into the shelf with number 'shelf'
     * @param board
     * @param marble
     * @param shelf
     * @throws InvalidActionException
     */
    public void insertResource(Board board, Marble marble, int shelf) throws InvalidActionException {

    }

    /**
     * This method allows to discard the resource.
     * @param board
     * @param marble
     */
    public void discardResource(Board board, Marble marble){

    }

    /**
     * This method allows two swap the content of two shelves of the warehouse
     * @param board the board of the player
     * @param source the number of the first shelf
     * @param target the number of the second shelf
     * @throws InvalidActionException if the action is invalid
     */
    public void swapWarehouse(Board board, int source, int target) throws InvalidActionException{

        try {
            board.getWarehouse().swap(source, target);
        }
        catch (IllegalShelfException e){throw new InvalidActionException(e.getMessage());}
    }

    /**
     *
     * @param board
     * @param slot
     * @param color
     * @param level
     * @throws InvalidActionException
     * @throws ResourcesExpectedException
     */
    public void buyCard(Board board, int slot, CardColor color, int level) throws InvalidActionException, ResourcesExpectedException {

    }

    /**
     * This method allows to buy the development card with color 'color' and level 'level' on the top of the deck
     * and it allows to add that card in the slot with number 'slot'
     * @param board
     * @param slot
     * @param color
     * @param level
     * @param shelves
     * @param quantity
     * @param strongbox
     * @throws InvalidActionException
     */
    public void buyCard(Board board, int slot, CardColor color, int level, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException {

    }

    /**
     * This method allows to activate the production of all the productions passed as parameters.
     * @param board
     * @param productions
     * @param shelves
     * @param quantity
     * @param strongbox
     * @throws InvalidActionException
     */
    public void doProduction(Board board, ArrayList<Production> productions, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException{

    }

    /**
     * This method allows to activate the leader card in position with number 'position'.
     * @param board
     * @param position
     * @throws InvalidActionException
     */
    public void activateCard(Board board, int position) throws InvalidActionException {

    }

    /**
     * This method allows to remove the leader card in position with number 'position'.
     * @param board
     * @param position
     * @throws InvalidActionException
     */
    public void removeCard(Board board, int position) throws InvalidActionException{

    }


}
