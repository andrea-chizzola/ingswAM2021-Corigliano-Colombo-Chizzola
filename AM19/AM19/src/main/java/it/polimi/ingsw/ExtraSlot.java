package it.polimi.ingsw;
import java.util.Objects;

/**
 * this class represents the effect 'extra slots' of a LeaderCard
 */
public class ExtraSlot implements SpecialEffect{

    /**
     * this attribute represent the size of the extra slot and
     * the kind of resource that it can contain
     */
    private ResQuantity resQuantity;

    public ExtraSlot(ResQuantity resQuantity){
        
        this.resQuantity = new ResQuantity(resQuantity.getResource(), resQuantity.getQuantity());
    }
    @Override
    public void applyEffect(Board board) {

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
