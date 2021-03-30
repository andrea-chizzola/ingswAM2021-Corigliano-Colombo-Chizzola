package it.polimi.ingsw;


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
}
