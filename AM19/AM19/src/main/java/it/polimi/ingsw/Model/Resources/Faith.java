package it.polimi.ingsw.Model.Resources;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleRed;
import it.polimi.ingsw.Model.MarketBoard.MarbleYellow;

import java.util.Objects;

/**
 * This class extends Resource
 * It represents faith points
 */
public class Faith extends Resource {

    /**
     * color represents the color associated with faith points
     */
    private final ResourceColor color = ResourceColor.RED;

    /**
     * This is a getter and gets the color of the resource
     * @return enum type of ResourceColor
     */
    @Override
    public ResourceColor getColor() {
        return color;
    }


    /**
     * This method does nothing because resource faith can't be added to strongbox
     */
    @Override
    public void addResourceStrongbox(Board board, int quantity){
        board.addFaith(quantity);
    }

    /**
     * This method returns the Marble associated to the resource.
     * @return the Marble associated to the resource
     */
    @Override
    public Marble getMarbleAssociated() {
        return new MarbleRed();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faith)) return false;
        Faith faith = (Faith) o;
        return color == faith.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
