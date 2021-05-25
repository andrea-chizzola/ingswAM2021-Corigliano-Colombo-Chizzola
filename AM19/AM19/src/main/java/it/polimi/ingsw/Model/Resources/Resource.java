package it.polimi.ingsw.Model.Resources;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.View.CLI.CLIColors;

/**
 * This abstract class represents the resources and it provides useful methods for managing them
 */
public abstract class Resource {

    /**
     * This method returns the color of the resource
     * @return enumeration type of ResourceColor
     */
    public abstract ResourceColor getColor();

    /**
     * This method adds to the strongbox a number of resources equal to 'quantity'
     * @param board the board of the player
     * @param quantity the quantity of resources to add
     */
    public abstract void addResourceStrongbox(Board board, int quantity);

    /**
     * @param resource Resource to check
     * @return true if the resources passed as parameter is of the same type of this
     */
    public abstract boolean isSameResource(Resource resource);

    /**
     * @return false if the resource ...
     */
    public abstract boolean isEmpty();

    /**
     * This method returns the Marble associated to the resource.
     * @return the Marble associated to the resource
     */
    public abstract Marble getMarbleAssociated();

    /**
     * @return a symbol associated to the resource
     */
    public abstract String getSymbol();

    /**
     * @return the color associated to the resource
     */
    public abstract CLIColors toColor();

}

