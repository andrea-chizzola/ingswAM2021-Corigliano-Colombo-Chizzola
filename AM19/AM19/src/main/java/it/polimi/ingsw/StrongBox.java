package it.polimi.ingsw;
import java.util.HashMap;

/**
 * public class representing the strongbox associated to a player
 */
public class StrongBox {

    /**
     * resources associates a resource, identified with its color, to the related quantity
     */
    private HashMap<ResourceColor, Integer> resources;

    /**
     * creates a new StrongBox with each resource set to 0
     */
    public StrongBox(){
        
        resources = new HashMap<ResourceColor, Integer>();
        resources.put(ResourceColor.BLUE, 0);
        resources.put(ResourceColor.GRAY, 0);
        resources.put(ResourceColor.YELLOW, 0);
        resources.put(ResourceColor.PURPLE, 0);

    }

    /**
     * adds a specific (POSITIVE) quantity of a resource to the strongbox
     * @param resource represents the resource we want to add to the strongbox
     * @param quantity represents how much of that resource we want to add
     */
    public void addResource(Resource resource, int quantity) {

        ResourceColor color = resource.getColor();

        resources.put(color, (resources.get(color) + quantity));

    }

    /**
     * removes a specific (POSITIVE) quantity of a resource from the strongbox
     * @param resource represents the resource we want to remove from the strongbox
     * @param quantity represents how much of that resource we want to remove
     */
    public void takeResource(Resource resource, int quantity){

        ResourceColor color = resource.getColor();

        resources.put(color, resources.get(color) - quantity);

    }

    /**
     * gets the quantity of a certain resource
     * @param resource represents the resource we want to know the quantity of
     * @return returns the quantity of the specified resource
     */
    public int getQuantity(Resource resource){

        ResourceColor color = resource.getColor();

        return resources.get(color);

    }

}
