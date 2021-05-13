package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.Modifications;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.CLIColors;

import java.util.LinkedList;
import java.util.List;

/**
 * This class implements Marble.
 * It represents the white marble.
 */
public class MarbleWhite implements Marble {

    /**
     * This method manages the actions of white marble.
     * @param board the board of the player
     * @param shelf not used
     */
    @Override
    public void addResource(Board board, int shelf) {
    }

    /**
     * This method allow to discard the resource.
     * @param board the board of the player
     */
    @Override
    public void discard(Board board){
    }

    /**
     * This method returns a LinkedList of Marble which contains all the marbles that can be used instead of the white one.
     * If there are not marbles that can be used instead of the white one the LinkedList is empty.
     * @param board is the board of the player
     * @return LinkedList of Marble which contains all the marbles that can be used instead of the white one
     */
    public LinkedList<Marble> whiteTransformations(Board board){
        return board.getModifications().getWhiteTransformations();
    }

    /**
     * @return the string associated with the color of the marble
     */
    @Override
    public CLIColors toColor() {
        return CLIColors.B_WHITE;
    }

    @Override
    public boolean isWhite() {
        return true;
    }

    @Override
    public Resource getResourceAssociated() {
        return new Shield();
    }



    @Override
    public String toString() {
        return "MarbleWhite";
    }
}