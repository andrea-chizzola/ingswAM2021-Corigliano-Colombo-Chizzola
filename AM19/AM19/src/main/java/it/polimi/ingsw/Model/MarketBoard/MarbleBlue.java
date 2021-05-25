package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.Resource;
import it.polimi.ingsw.Model.Resources.Shield;
import it.polimi.ingsw.View.CLIColors;

import java.util.LinkedList;


/**
 * This class implements Marble.
 * It represents the blue marble.
 */
public class MarbleBlue implements Marble {

    /**
     * This method adds the resource Shield to the warehouse on shelf with number 'shelf'.
     * @param board the board of the player
     * @param shelf the number of the self
     */
    @Override
    public void addResource(Board board, int shelf) {

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

    /**
     * @return the string associated with the color of the marble
     */
    @Override
    public CLIColors toColor() {
        return CLIColors.B_BLUE;
    }


    /**
     * This method checks if the the marble passed as parameter is among those allowed
     * @param marble marble
     * @param board  the board of the player
     * @return true if the the marble passed as parameter is among those allowed, false otherwise
     */
    @Override
    public boolean checkMarble(Marble marble, Board board) {
        return marble.getResourceAssociated().isSameResource(this.getResourceAssociated());
    }

    /**
     * @return the resource associated with the marble
     */
    @Override
    public Resource getResourceAssociated() {
        return new Shield();
    }


    @Override
    public String toString() {
        return "MarbleBlue";
    }

}
