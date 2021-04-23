package it.polimi.ingsw.Model.Resources;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleGray;
import it.polimi.ingsw.Model.MarketBoard.MarbleYellow;

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
     * This method returns the Marble associated to the resource.
     * @return the Marble associated to the resource
     */
    @Override
    public Marble getMarbleAssociated() {
        return new MarbleGray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stone)) return false;
        Stone stone = (Stone) o;
        return color == stone.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
