package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.Coin;
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
    private LinkedList<ResQuantity> materials;
    /**
     * this attribute represents the list of products
     */
    private LinkedList<ResQuantity> products;

    /**
     * this attribute represents the amount of custom materials in the production
     */
    private int customMaterials;

    /**
     * this attribute represents the amount of custom products in the production
     */
    private int customProducts;

    /**
     *
     * @param materials the list of required materials
     * @param products the list of products
     * @param customMaterials is the amount of custom materials
     * @param customProducts is the amount of custom products
     */
    public Production(LinkedList<ResQuantity> materials, LinkedList<ResQuantity> products, int customMaterials, int customProducts){
        this.materials = new LinkedList<>();
        this.materials.addAll(materials);

        this.products = new LinkedList<>();
        this.products.addAll(products);

        this.customMaterials = customMaterials;
        this.customProducts = customProducts;
    }

    /**
     *
     * @return the production of the card
     */
    @Override
    public Production getProduction(){
        LinkedList<ResQuantity> mat = new LinkedList<>(materials);
        LinkedList<ResQuantity> prod = new LinkedList<>(products);
        return new Production(mat, prod, customMaterials, customProducts);
    }

    /**
     * @return the amount of custom materials
     */
    public int getCustomMaterials() {
        return customMaterials;
    }

    /**
     * @return the amount of custom products
     */
    public int getCustomProducts() {
        return customProducts;
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


    /**
     * @return a ResQuantity which represents the special effect
     */
    @Override
    public ResQuantity getSpecialEffect() {
        return new ResQuantity(new Coin(), 0);
    }

    /**
     * @return a copy of the production materials
     */
    public LinkedList<ResQuantity> getMaterials() {
        return new LinkedList<>(materials);
    }

    /**
     * @return a copy of the production products
     */
    public LinkedList<ResQuantity> getProducts() {
        return new LinkedList<>(products);
    }

    public String toString(){
        StringBuilder mat = new StringBuilder(), prod = new StringBuilder();
        for(int i=0; i<materials.size(); i++){
            mat.append(materials.get(i).toString())
                    .append("\n");
        }
        for(int i=0; i<products.size(); i++){
            prod.append(products.get(i).toString())
                    .append("\n");
        }
        return  "+ Production: \n" +
                "- Requirements: " + "\n" +
                mat +
                ((customMaterials != 0)? "custom materials: " + customMaterials + "\n" : "")+
                "- Products" + "\n" +
                prod +
                ((customProducts != 0)? "custom products: " + customProducts : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Production that = (Production) o;
        return customMaterials == that.customMaterials &&
                customProducts == that.customProducts &&
                Objects.equals(materials, that.materials) &&
                Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materials, products, customMaterials, customProducts);
    }

}
