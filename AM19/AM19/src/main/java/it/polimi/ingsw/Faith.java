package it.polimi.ingsw;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faith)) return false;
        Faith faith = (Faith) o;
        return color == faith.color;
    }
}
