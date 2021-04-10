package it.polimi.ingsw;

import java.util.Objects;

/**
 * this class represents the effect 'discount on a resource' of a LeaderCard
 */
public class Discount extends SpecialEffect {
    /**
     * this attribute represents the kind of resource to be discounted
     */
    private ResQuantity resQuantity;


    public Discount(ResQuantity resQuantity){
        this.resQuantity = new ResQuantity(resQuantity.getResource(), resQuantity.getQuantity());
    }

    /**
     * this method applies the discount effect to the player board
     * @param board represents the board associated to a player
     */
    @Override
    public void applyEffect(Board board) {
        board.getModifications().addDiscount(resQuantity.getResource(), resQuantity.getQuantity());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(resQuantity, discount.resQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resQuantity);
    }
}
