package it.polimi.ingsw.Model.Resources;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class associates a quantity to a resource
 */

public class ResQuantity {
    /**
     * resource represents the type of resource
     */
    private final Resource resource;

    /**
     * quantity indicates the number of resources
     */
    private final int quantity;

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
     * This method creates a map with key Resource and value Integer which contains a summary of all the resources selected
     * and checks if they are effectively present in the warehouse and in the strongbox.
     * @param board the board of the player
     * @param shelves ArrayList of Integer which represents all the shelves from which to get the resources
     * @param quantity ArrayList of Integer which represents the amount of resources to be removed from each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources to be taken from the StrongBox
     *                  It is important that each resource is present at most once
     * @return a Map with key Resource and value Integer
     * @throws InvalidActionException if the resources selected are more than the resources effectively present.
     */
    public static Map<Resource,Integer> createReqMap(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException{

        Map<Resource, Integer> reqMap = new HashMap<>();

        try {
            Map<Resource, Integer> reqWarehouseMap = ResQuantity.createWarehouseMap(board, shelves, quantity);
            Map<Resource, Integer> reqStrongboxMap = ResQuantity.createStrongboxMap(board, strongbox);
            reqMap = Stream.concat(reqWarehouseMap.entrySet().stream(),
                    reqStrongboxMap.entrySet().stream())
                    .collect(Collectors
                            .groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
        }
        catch (InvalidActionException e) { throw e;}

        return reqMap;
    }

    /**
     * This method creates a map with key Resource and value Integer which contains a summary of all the resources in the selected shelves
     * and checks if the resources selected are effectively present in the warehouse.
     * @param board the board of the player
     * @param shelves ArrayList of Integer which represents all the shelves from which to get the resources
     * @param quantity ArrayList of Integer which represents the amount of resources to be removed from each shelf
     * @return  a Map with key Resource and value Integer
     * @throws InvalidActionException if the resources selected are more than the resources effectively present.
     */
    static private Map<Resource,Integer> createWarehouseMap(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity) throws InvalidActionException{

        Map<Resource,Integer> reqMap = new HashMap<>();

        if(shelves.size() != quantity.size()) throw new InvalidActionException("Resources selected from warehouse are incorrect!");

        for (int i=0; i<shelves.size(); i++ ){
            try{ //if quantity.get(i) == 0 it is not important if the shelf exists or if it contains enough resources, removing this if cause bugs, do not remove it.
                if(quantity.get(i) != 0) {
                    Resource resource = board.getWarehouse().getResource(shelves.get(i));

                    if (board.getWarehouse().getQuantity(shelves.get(i)) < quantity.get(i))
                        throw new InvalidActionException("Selected more resources than are present!");

                    if (reqMap.containsKey(resource)) {
                        reqMap.put(resource, reqMap.get(resource) + quantity.get(i)); }
                    else{
                        reqMap.put(resource, quantity.get(i));}
                }
            }
            catch (IllegalShelfException e){throw new InvalidActionException("Invalid Shelf!");}
        }

        return reqMap;
    }

    /**
     * This method creates a map with key Resource and value Integer which contains a summary of all the resources present in the ArrayList strongbox
     * and checks if the resources selected are effectively present in it.
     * @param board the board of the player
     * @param strongbox ArrayList of ResQuantity which represents all the resources to be taken from the StrongBox
     *                  It is important that each resource is present at most once
     * @return a Map with key Resource and value Integer
     * @throws InvalidActionException if the resources selected are more than the resources effectively present.
     */
    static private Map<Resource,Integer> createStrongboxMap(Board board, ArrayList<ResQuantity> strongbox) throws InvalidActionException{

        Map<Resource, Integer> reqMap = new HashMap<>();

        for(ResQuantity resQuantity : strongbox){
            if(board.getStrongBox().getQuantity(resQuantity.getResource()) < resQuantity.getQuantity())
                throw new InvalidActionException("Selected more resources than are present!");

            reqMap.put(resQuantity.getResource(), resQuantity.getQuantity());
        }

        return reqMap;
    }


    /**
     * This method subtracts (according to the input parameters) from the warehouse and the strongBox the resources selected.
     * It is required that the resources selected are effectively present in the warehouse and strongbox.
     * @param board the board of the player
     * @param shelves ArrayList of Integer which represents all the shelves from which to subtract the resources
     * @param quantity ArrayList of Integer which represents the amount of resources to be removed from each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources to be subtracted from the StrongBox
     * @return true if the operation ends correctly
     * @throws InvalidActionException if the resources selected are more than the resources effectively present.
     */
    public static boolean useResources(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException{

        //use resources from strongbox
        for(ResQuantity resQuantity : strongbox){
            resQuantity.useResourceStrongbox(board);
        }

        //use resources from warehouse
        try {
            for (int i = 0; i < shelves.size(); i++) {
                //If quantity.get(i) == 0 => the nested for the loop has no iterations!
                for (int j = 0; j < quantity.get(i); j++)
                    board.getWarehouse().subtract(shelves.get(i));
            }
        }
        //If the exception is caught it means that the requirements are not met and the game state may become inconsistent.
        catch(IllegalShelfException e){throw new InvalidActionException("Requirements not met! Error!");}

        return true;
    }


    /**
     * This method removes from the strongbox a number of resources equal to 'quantity'
     * @param board the board of the player
     */
    public void useResourceStrongbox(Board board) {
        board.getStrongBox().takeResource(this.resource, this.quantity);
    }


    /**
     * This method adds to the strongbox a number of resources equal to 'quantity'.
     * The type of resource added is the one contained in this object.
     * @param board
     */
    public void addResourceStrongbox(Board board){
        this.resource.addResourceStrongbox(board, this.quantity);
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
     * This method checks if there are enough resources in the Map passed as parameter(considering the eventual discount).
     * If the resources in the Map are enough it modifies the Map and subtract those resources, otherwise it throws an InvalidActionException
     * @param resourceStatus the map on which operations are done
     * @param board the board of the player
     * @throws InvalidActionException if the resources in the map are enough
     */
    public void checkDevelopment(Map<Resource,Integer> resourceStatus, Board board) throws InvalidActionException{

        //check discount
        int i = (this.quantity - board.getModifications().getDiscount(this.resource));
        int j = (i <= 0) ? resourceStatus.getOrDefault(this.resource, 0) : (resourceStatus.getOrDefault(this.resource, 0) - i);

        if(j < 0)
            throw new InvalidActionException("Insufficient resources!");

        resourceStatus.put(this.resource, j);
    }

    /**
     * This method checks if there are enough resources in the Map passed as parameter.
     * If the resources in the Map are enough it modifies the Map and subtract those resources, otherwise it throws an InvalidActionException
     * @param resourceStatus the map on which operations are done
     * @throws InvalidActionException if the resources in the map are enough
     */
    public void checkLeader(Map<Resource,Integer> resourceStatus) throws InvalidActionException{

        int i = resourceStatus.getOrDefault(this.resource, 0) - this.quantity;

        if(i < 0)
            throw new InvalidActionException("Insufficient resources!");

        resourceStatus.put(this.resource, i);
    }

    /**
     * This method checks if there are enough resources in the Map passed as parameter.
     * If the resources in the Map are enough it modifies the Map and subtract those resources, otherwise it throws an InvalidActionException
     * @param resourceStatus the map on which operations are done
     * @throws InvalidActionException if the resources in the map are enough
     */
    public void checkProduction(Map<Resource,Integer> resourceStatus) throws InvalidActionException{

        int i = resourceStatus.getOrDefault(this.resource, 0) - this.quantity;

        if(i < 0)
            throw new InvalidActionException("Insufficient resources!");

        resourceStatus.put(this.resource, i);
    }

    /**
     * @return a String that represent the object
     */
    @Override
    public String toString(){
        return "res:" + resource.toString() + "quantity:" + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResQuantity res = (ResQuantity) o;
        return (this.quantity != res.quantity || this.resource.equals(res.resource));
    }
}
