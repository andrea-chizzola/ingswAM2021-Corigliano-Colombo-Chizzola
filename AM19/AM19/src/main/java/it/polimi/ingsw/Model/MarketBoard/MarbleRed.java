package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;

import java.util.LinkedList;

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
    public void addResource(Board board, int shelf) throws InvalidActionException{
        board.addFaith(1);
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

    @Override
    public String toString() {
        return "MarbleRed";
    }
}