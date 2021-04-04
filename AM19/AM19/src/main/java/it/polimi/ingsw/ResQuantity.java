package it.polimi.ingsw;

/**
 * This class associates a quantity to a resource
 */

public class ResQuantity {
    /**
     * resource represents the type of resource
     */
    private Resource resource;

    /**
     * quantity indicates the number of resources
     */
    private int quantity;

    /**
     * This is the constructor of this class
     * @param resource represents the type of resource
     * @param quantity indicates the number of resources, it is up to the caller to set this parameter correctly
     */
    public ResQuantity(Resource resource, int quantity) {
        this.resource = resource;
        this.quantity = quantity;
    }

    /**
     * This is a getter and gets the type of resource
     * @return resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * This is a getter and gets the quantity (number of resources)
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This is a setter and sets the quantity (number of resources)
     * * @param quantity, it is up to the caller to set this parameter correctly
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * This method allows to add an integer to the capacity
     * @param number the integer which is added to the capacity, it is up to the caller to set this parameter correctly
     */
    public void add(int number){
        quantity+=number;
    }
}
