package it.polimi.ingsw.Model.Resources;


import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleBlue;
import it.polimi.ingsw.View.CLI.CLIColors;

import java.util.Objects;

/**
 * This class extends Resource
 * It represents the resource shield
 */
public class Shield extends Resource {
    /**
     * color represents the color associated with the resource shield
     */
    private final ResourceColor color = ResourceColor.BLUE;

    /**
     * This is a getter and gets the color of the resource
     * @return enum type of ResourceColor
     */
    @Override
    public ResourceColor getColor() {
        return color;
    }

    /**
     * This method adds to the strongbox a number of resources 'Shield' equal to 'quantity'
     * @param board the board of the player
     * @param quantity the quantity of resources to add
     */
    @Override
    public void addResourceStrongbox(Board board, int quantity) {
        board.getStrongBox().addResource(this, quantity);
    }

    /**
     * This method returns the Marble associated to the resource.
     * @return the Marble associated to the resource
     */
    @Override
    public Marble getMarbleAssociated() {
        return new MarbleBlue();
    }

    /**
     * @return the name of the resource
     */
    @Override
    public String toString(){
        return "Shield";
    }

    /**
     * @return a symbol associated to the resource
     */
    @Override
    public String getSymbol(){
        return "\u001B[34m" + "◆";
    }

    @Override
    public CLIColors toColor() {
        return CLIColors.B_BLUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shield shield = (Shield) o;
        return color == shield.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
