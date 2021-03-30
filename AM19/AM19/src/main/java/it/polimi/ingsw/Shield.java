package it.polimi.ingsw;


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
}
