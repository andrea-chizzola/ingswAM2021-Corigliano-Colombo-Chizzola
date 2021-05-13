package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.Faith;
import it.polimi.ingsw.Model.Resources.Resource;
import it.polimi.ingsw.Model.Resources.Servant;
import it.polimi.ingsw.Model.Resources.Shield;
import it.polimi.ingsw.View.CLIColors;

import java.util.LinkedList;

/**
 * This class implements Marble.
 * It represents the purple marble.
 */
public class MarblePurple implements Marble {


    /**
     * This method adds the resource Shield to the warehouse on shelf with number 'shelf'.
     * @param board the board of the player
     * @param shelf the number of the self
     */
    @Override
    public void addResource(Board board, int shelf) {
        /*
        try {
            board.getWarehouse().addResource(shelf, new Shield());
        }
        catch(IllegalShelfException e){
            throw new InvalidActionException(e.getMessage());
        }*/
        board.getWarehouse().insertResource(shelf, getResourceAssociated());
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
    public boolean isWhite() {
        return false;
    }

    @Override
    public Resource getResourceAssociated() {
        return new Servant();
    }
    /**
     * @return the string associated with the color of the marble
     */
    @Override
    public CLIColors toColor() {
        return CLIColors.B_MAGENTA;
    }



    @Override
    public String toString() {
        return "MarblePurple";
    }
}