package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Objects;


/**
 * This class extends Resource
 * It represents the resource coin
 */
public class Coin extends Resource {

    /**
     * color represents the color associated with the resource coin
     */
    private final ResourceColor color = ResourceColor.YELLOW;

    /**
     * This is a getter and gets the color of the resource
     * @return enum type of ResourceColor
     */
    @Override
    public ResourceColor getColor() {
        return color;
    }

    /**
     * This method adds to the strongbox a number of resources 'Coin' equal to 'quantity'
     * @param board the board of the player
     * @param quantity the quantity of resources to add
     */
    @Override
    public void addResourceStrongbox(Board board, int quantity) {
        board.getStrongBox().addResource(this, quantity);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coin)) return false;
        Coin coin = (Coin) o;
        return color == coin.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
