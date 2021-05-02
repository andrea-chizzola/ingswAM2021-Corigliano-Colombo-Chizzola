package it.polimi.ingsw.Model.Boards;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.HashMap;

/**
 * public class representing the strongbox associated to a player
 */
public class StrongBox {

    /**
     * resources associates a resource, identified with its color, to the related quantity
     */
    private HashMap<Resource, Integer> resources;

    /**
     * creates a new  empty StrongBox
     */
    public StrongBox(){
        
        resources = new HashMap<>();

    }

    /**
     * @return returns a map containing the resources currently in the strongbox with their quantity
     */
    public HashMap<Resource, Integer> getResources() {
        HashMap<Resource, Integer> map = new HashMap<>(resources);
        return map;
    }

    /**
     * adds a specific (POSITIVE) quantity of a resource to the strongbox
     * @param resource represents the resource we want to add to the strongbox
     * @param quantity represents how much of that resource we want to add
     */
    public void addResource(Resource resource, int quantity) {

        if(resources.containsKey(resource)) {
            resources.put(resource, (resources.get(resource) + quantity));
        }else resources.put(resource, quantity);
    }

    /**
     * removes a specific (POSITIVE) quantity of a resource from the strongbox. The caller has to check if there are enough resources
     * @param resource represents the resource we want to remove from the strongbox
     * @param quantity represents how much of that resource we want to remove
     */
    public void takeResource(Resource resource, int quantity){

        if(resources.containsKey(resource) && quantity <= resources.get(resource)) {
            resources.put(resource, (resources.get(resource) - quantity));
        }else resources.put(resource, 0);
    }

    /**
     * gets the quantity of a certain resource
     * @param resource represents the resource we want to know the quantity of
     * @return returns the quantity of the specified resource
     */
    public int getQuantity(Resource resource){

        return resources.getOrDefault(resource, 0);
    }

    /**
     * @return returns the total resources left in the strongbox
     */
    public int calculateTotalResources(){

        return resources.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

    }

}
