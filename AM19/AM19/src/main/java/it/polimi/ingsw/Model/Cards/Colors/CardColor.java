package it.polimi.ingsw.Model.Cards.Colors;

import java.util.Objects;

/**
 * This abstract Class represents the color of the DevelopmentCards.
 * This class is extended by the classes Blue, Yellow, Purple.
 */

public abstract class CardColor {
    /**
     * this attribute contains the instance of DevColor that represents the color
     */
    private DevColor color;

    /**
     * the constructor of CardColor initializes the attribute color
     */
    public CardColor(DevColor color){
        this.color = color;
    }

    /**
     *
     * @return the value of the attribute color
     */
    public DevColor getColor(){
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardColor cardColor = (CardColor) o;
        return color == cardColor.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
