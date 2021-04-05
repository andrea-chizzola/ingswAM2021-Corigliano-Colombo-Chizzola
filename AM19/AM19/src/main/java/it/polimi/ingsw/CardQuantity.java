package it.polimi.ingsw;

import java.util.Objects;

/**
 * this class represents the color, the level, and the amount of a type of DevelopmentCards.
 */
public class CardQuantity {
    /**
     * this attribute represents the color of the cards
     */
    private CardColor cardColor;
    /**
     * this attribute represents the number of the cards
     */
    private int quantity;
    /**
     * this attribute represents the number of the cards
     */
    private int level;


    public CardQuantity(CardColor cardColor, int quantity, int level){
        this.cardColor = cardColor;
        this.quantity = quantity;
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardQuantity that = (CardQuantity) o;
        return quantity == that.quantity &&
                level == that.level &&
                Objects.equals(cardColor, that.cardColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardColor, quantity, level);
    }
}
