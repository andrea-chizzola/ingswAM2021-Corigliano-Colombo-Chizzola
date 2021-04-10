package it.polimi.ingsw;

import it.polimi.ingsw.Marble;
import it.polimi.ingsw.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * this class represents the active effects of LeaderCards on the personal board
 */
public class Modifications{
    /**
     * this attribute is a map that contains the available target resources for a white marble
     */
    private Map<Resource, Boolean> marbleTo;
    /**
     * this attribute is a map that contains the active discount on each kind of resources
     */
    private Map<Resource, Integer> discount;

    public Modifications(){
        marbleTo = new HashMap<>();
        discount = new HashMap<>();
    }
    /**
     * this method checks whether a white marble can be transformed to a target resource
     * @param res is the target Resource
     * @return true if the white marble can be transformed to the target marble, otherwise return false
     */
    public boolean marbleTo(Resource res){
        return marbleTo.getOrDefault(res, false);
    }

    /**
     * this method add a marble to the list of the possible color transformations of a white marble
     * @param res is the target resource
     */
    public void addMarbleTo(Resource res){
        marbleTo.put(res, true);
    }

    /**
     * this method return a discount associated with a resource
     * @param resource is the target resource
     * @return the computed discount
     */
    public int getDiscount(Resource resource){
        return discount.getOrDefault(resource, 0);
    }

    /**
     * this method increases the discount associated to a resource
     * @param resource is the target resource
     */
    public void addDiscount(Resource resource, int amount){
        discount.put(resource, this.getDiscount(resource) + amount);
    }
}

