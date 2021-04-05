package it.polimi.ingsw;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servant)) return false;
        Servant servant = (Servant) o;
        return color == servant.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
