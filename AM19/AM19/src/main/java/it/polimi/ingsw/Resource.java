package it.polimi.ingsw;

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
    public abstract void addResourceStrongbox(Board board, int quantity) throws PlayerWonException;

}

