package it.polimi.ingsw.Model.Boards;
import it.polimi.ingsw.Model.Resources.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * public class representing the strongbox associated to a player
 */
public class StrongBox {

    /**
     * resources associates a resource, identified with its color, to the related quantity
     */
    private final HashMap<Resource, Integer> resources;

    /**
     * creates a new empty StrongBox
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

    /**
     * @return List of ResQuantity which represents the status of the StrongBox
     */
    public List<ResQuantity> showStrongBox(){
        List<ResQuantity> list = new ArrayList<>();

        for(Resource resource : resources.keySet()){
            list.add(new ResQuantity(resource,resources.get(resource)));
        }
        return list;
    }

    /**
     * method used for testing purpose and demo
     * this method adds 100 resources of each type to the StrongBox
     */
    public void cheat(){
        addResource(new Coin(),100);
        addResource(new Stone(),100);
        addResource(new Servant(),100);
        addResource(new Shield(),100);
    }

}
