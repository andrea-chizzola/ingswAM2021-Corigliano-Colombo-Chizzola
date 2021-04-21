package it.polimi.ingsw.Model.Turn;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.MarketBoard.Marble;

import java.util.ArrayList;

/**
 * This class provides methods needed for Taking Resources from the Market.
 */
public class TakeResources extends Turn {

    /**
     * This method allows to take a specific row from the MarketBoard.
     * @param board the board of the player
     * @param row the row the player wants to take
     * @return ArrayList of Marbles, which corresponds to the row
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public ArrayList<Marble> takeMarketRow(Board board, int row) throws InvalidActionException {

        ArrayList<Marble> list;
        try {
            list = board.getGameBoard().getMarketBoard().getRow(row);
        }
        catch (IllegalMarketException e){throw new InvalidActionException(e.getMessage());}
        return list;
    }

    /**
     * This method allows to take a specific column from the MarketBoard.
     * @param board the board of the player
     * @param column the column the player wants to take
     * @return ArrayList of Marbles, which corresponds to the row
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public ArrayList<Marble> takeMarketColumn(Board board, int column) throws InvalidActionException {
        ArrayList<Marble> list;
        try {
            list = board.getGameBoard().getMarketBoard().getColumn(column);
        }
        catch (IllegalMarketException e){throw new InvalidActionException(e.getMessage());}
        return list;
    }

    /**
     * This method allows to insert the resource, specified by the marble, into the shelf with number 'shelf'
     * @param board the board of the player
     * @param marble
     * @param shelf the number of the shelf
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void insertResource(Board board, Marble marble, int shelf) throws InvalidActionException, MarbleWhiteException{

        try {
            marble.addResource(board, shelf);
        }
        catch (InvalidActionException e){throw e;}

    }

    /**
     * This method allows to discard the resource.
     * @param board the board of the player
     * @param marble
     */
    @Override
    public void discardResource(Board board, Marble marble){
        marble.discard(board);
    }
}
