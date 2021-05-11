package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.ResQuantity;

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

    /**
     * @return a ResQuantity which represents the special effect
     */
    @Override
    public ResQuantity getSpecialEffect() {
        return resQuantity;
    }

    @Override
    public boolean isDiscount() {
        return true;
    }

    @Override
    public String toString(){
        return  "+ Resource Discount: \n" +
                resQuantity.toString();
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
