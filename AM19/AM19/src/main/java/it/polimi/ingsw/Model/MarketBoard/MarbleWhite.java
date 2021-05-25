package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.CLI.CLIColors;

import java.util.LinkedList;

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


    /**
     * This method checks if the the marble passed as parameter is among those allowed
     * @param marble marble
     * @param board  the board of the player
     * @return true if the the marble passed as parameter is among those allowed, false otherwise
     */
    @Override
    public boolean checkMarble(Marble marble, Board board) {

        if(marble.getResourceAssociated().isSameResource(this.getResourceAssociated()))
            return true;
        for(Marble modifications : this.whiteTransformations(board)) {
            if(modifications.getResourceAssociated().isSameResource(marble.getResourceAssociated()))
                return true;
        }
        return false;
    }

    /**
     * @return the resource associated with the marble
     */
    @Override
    public Resource getResourceAssociated() {
        return new White();
    }


    @Override
    public String toString() {
        return "MarbleWhite";
    }
}