package it.polimi.ingsw;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stone)) return false;
        Stone stone = (Stone) o;
        return color == stone.color;
    }
}
