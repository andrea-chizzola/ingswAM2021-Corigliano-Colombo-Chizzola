package it.polimi.ingsw;

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
}
