package it.polimi.ingsw;

import java.util.LinkedList;
import java.util.Objects;

/**
 * public class that represents a production effect
 */

public class Production extends SpecialEffect{
    /**
     * this attribute represents the list of required materials
     */
    LinkedList<ResQuantity> materials;
    /**
     * this attribute represents the list of products
     */
    LinkedList<ResQuantity> products;

    /**
     *
     * @param materials the list of required materials
     * @param products the list of products
     */
    public Production(LinkedList<ResQuantity> materials, LinkedList<ResQuantity> products){
        this.materials = new LinkedList<>();
        this.materials.addAll(materials);

        this.products = new LinkedList<>();
        this.products.addAll(products);
    }

    /**
     *
     * @return the production of the card
     */
    @Override
    public Production getProduction(){
        LinkedList<ResQuantity> mat = new LinkedList<>(materials);
        LinkedList<ResQuantity> prod = new LinkedList<>(products);
        return new Production(mat, prod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Production that = (Production) o;
        return Objects.equals(materials, that.materials) &&
                Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materials, products);
    }
}
