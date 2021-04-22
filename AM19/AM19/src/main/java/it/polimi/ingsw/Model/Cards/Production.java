package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.LinkedList;
import java.util.Map;
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


    /**
     *  This method checks if there are enough resources (in the Map passed as parameter) to activate the production.
     *  If the resources in the Map are enough it modifies the Map and subtract those resources, otherwise it throws an InvalidActionException
     * @param resourceStatus
     * @return true if there are enough resources in the map
     * @throws InvalidActionException if there aren't enough resources in the map
     */
    public boolean checkProduction(Map<Resource,Integer> resourceStatus) throws InvalidActionException{
        for(ResQuantity resQuantity : materials)
            resQuantity.checkProduction(resourceStatus);
        return true;
    }

    /**
     *This method adds to the resources of the player all the resources contained in the products.
     * @param board the board of the player
     */
    public void addProducts(Board board){

        for(ResQuantity resQuantity : products)
            resQuantity.addResourceStrongbox(board);
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
