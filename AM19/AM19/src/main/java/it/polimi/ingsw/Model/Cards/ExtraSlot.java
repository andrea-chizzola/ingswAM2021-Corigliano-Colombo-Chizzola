package it.polimi.ingsw.Model.Cards;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.Objects;

/**
 * this class represents the effect 'extra slots' of a LeaderCard
 */
public class ExtraSlot extends SpecialEffect{

    /**
     * this attribute represent the size of the extra slot and
     * the kind of resource that it can contain
     */
    private ResQuantity resQuantity;

    public ExtraSlot(ResQuantity resQuantity){
        this.resQuantity = new ResQuantity(resQuantity.getResource(), resQuantity.getQuantity());
    }

    /**
     * this method adds an extra-slot to a player's wharehouse
     * @param board represents the board associated to a player
     */
    @Override
    public void applyEffect(Board board) {
        board.getWarehouse().addShelf(new ResQuantity(resQuantity.getResource(), resQuantity.getQuantity()));
    }

    /**
     * @return a ResQuantity which represents the special effect
     */
    @Override
    public ResQuantity getSpecialEffect() {
        return resQuantity;
    }

    @Override
    public boolean isExtraShelf() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraSlot extraSlot = (ExtraSlot) o;
        return Objects.equals(resQuantity, extraSlot.resQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resQuantity);
    }
}
