package it.polimi.ingsw;

import java.util.Objects;

/**
 * this class represents the effect 'discount on a resource' of a LeaderCard
 */
public class Discount implements SpecialEffect {
    /**
     * this attribute represents the kind of resource to be discounted
     */
    private Resource resource;


    public Discount(Resource resource){
        this.resource = resource;
    }
    @Override
    public void applyEffect(Board board) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(resource, discount.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource);
    }
}
