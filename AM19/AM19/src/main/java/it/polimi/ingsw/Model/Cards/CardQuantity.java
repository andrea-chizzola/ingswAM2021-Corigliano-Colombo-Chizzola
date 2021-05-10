package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Cards.Colors.CardColor;

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

    /**
     * This method gets the CardColor
     * @return CardColor
     */
    public CardColor getCardColor() {
        return cardColor;
    }

    /**
     * This method gets the level
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * This method gets the quantity
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString(){
        return  cardColor.toString() + " " +
                "L=" + level;
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
