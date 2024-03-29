package it.polimi.ingsw.Model.Resources;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarblePurple;
import it.polimi.ingsw.View.CLI.CLIColors;

import java.util.Objects;

/**
 * This class extends Resource
 * It represents the resource servant
 */
public class Servant extends Resource {
    /**
     * color represents the color associated with the resource servant
     */
    private final ResourceColor color = ResourceColor.PURPLE;

    /**
     * This is a getter and gets the color of the resource
     * @return enum type of ResourceColor
     */
    @Override
    public ResourceColor getColor() {
        return color;
    }

    /**
     * This method returns the Marble associated to the resource.
     * @return the Marble associated to the resource
     */
    @Override
    public Marble getMarbleAssociated() {
        return new MarblePurple();
    }

    /**
     * This method adds to the strongbox a number of resources 'Servant' equal to 'quantity'
     * @param board the board of the player
     * @param quantity the quantity of resources to add
     */
    @Override
    public void addResourceStrongbox(Board board, int quantity) {
        board.getStrongBox().addResource(this, quantity);
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
     * @return @return true if the resource is not empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * @return the name of the resource
     */
    @Override
    public String toString(){
        return "Servant";
    }

    /**
     * @return a symbol associated to the resource
     */
    @Override
    public String getSymbol(){
        return "\u001B[35m" + "■";
    }

    @Override
    public CLIColors toColor() {
        return CLIColors.B_MAGENTA;
    }

    /**
     * @return the image associated with the resource
     */
    @Override
    public String getImage() {
        return "servant.png";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servant servant = (Servant) o;
        return color == servant.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
