package it.polimi.ingsw.Model.Resources;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleGray;
import it.polimi.ingsw.Model.MarketBoard.MarbleYellow;
import it.polimi.ingsw.View.CLIColors;

import java.util.Objects;

/**
 * This class extends Resource
 * It represents the resource stone
 */
public class Stone extends Resource {

    /**
     * color represents the color associated with the resource stone
     */
    private final ResourceColor color = ResourceColor.GRAY;

    /**
     * This is a getter and gets the color of the resource
     * @return enum type of ResourceColor
     */
    @Override
    public ResourceColor getColor() {
        return color;
    }

    /**
     * This method adds to the strongbox a number of resources 'Stone' equal to 'quantity'
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
     * @return true if the resource is not empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * This method returns the Marble associated to the resource.
     * @return the Marble associated to the resource
     */
    @Override
    public Marble getMarbleAssociated() {
        return new MarbleGray();
    }

    /**
     * @return the name of the resource
     */
    @Override
    public String toString(){
        return "Stone";
    }

    /**
     * @return a symbol associated to the resource
     */
    @Override
    public String getSymbol(){
        return "\u001B[36m" + "■";
    }

    @Override
    public CLIColors toColor() {
        return CLIColors.B_CYAN;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stone stone = (Stone) o;
        return color == stone.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
