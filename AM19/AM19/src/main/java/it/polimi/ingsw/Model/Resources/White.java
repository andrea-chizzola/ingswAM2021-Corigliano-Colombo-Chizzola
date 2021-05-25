package it.polimi.ingsw.Model.Resources;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleWhite;
import it.polimi.ingsw.View.CLI.CLIColors;

public class White extends Resource{

    /**
     * color represents the color associated with the empty resource
     */
    private final ResourceColor color = ResourceColor.WHITE;

    /**
     * This method returns the color of the resource
     *
     * @return enumeration type of ResourceColor
     */
    @Override
    public ResourceColor getColor() {
        return this.color;
    }

    /**
     * This method adds to the strongbox a number of resources equal to 'quantity'
     *
     * @param board    the board of the player
     * @param quantity the quantity of resources to add
     */
    @Override
    public void addResourceStrongbox(Board board, int quantity) {

    }

    /**
     * @param resource Resource to check
     * @return true if the resources passed as parameter is of the same type of this
     */
    @Override
    public boolean isSameResource(Resource resource) {
        return resource.getColor().equals(this.color);
    }


    /**
     * @return true if the resource is not empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * This method returns the Marble associated to the resource.
     * @return the Marble associated to the resource
     */
    @Override
    public Marble getMarbleAssociated() {
        return new MarbleWhite();
    }

    /**
     * @return a symbol associated to the resource
     */
    @Override
    public String getSymbol() {
        return null;
    }

    /**
     * @return the color associated to the resource
     */
    @Override
    public CLIColors toColor() {
        return null;
    }
}
