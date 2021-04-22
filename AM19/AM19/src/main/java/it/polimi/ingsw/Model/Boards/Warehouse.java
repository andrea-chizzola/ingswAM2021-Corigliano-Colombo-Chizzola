package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class represents the warehouse which is a component of the board.
 */
public class Warehouse {

    /**
     * defaultShelf is a map which maps an Integer (the number of the shelf) to a ResQuantity (the contents of the shelf).
     * It is used only for the default shelves which are created at the beginning of the game.
     */
    private final Map<Integer, ResQuantity> defaultShelf;
    /**
     * defaultCapacity is a map which maps an Integer (the number of the shelf) to another Integer (the capacity of the shelf).
     * It is used only for the default shelves which are created at the beginning of the game.
     */
    private final Map<Integer,Integer> defaultCapacity;
    /**
     * extraShelf is a map which maps an Integer (the number of the shelf) to a ResQuantity (the contents of the shelf).
     * It is used only for the extra shelves which are created during the game due to special effects of LeaderCards.
     */
    private final Map<Integer,ResQuantity> extraShelf;
    /**
     * extraCapacity is a map which maps an Integer (the number of the shelf) to another Integer (the capacity of the shelf).
     * It is used only for the extra shelves which are created during the game due to special effects of LeaderCards.
     */
    private final Map<Integer,Integer> extraCapacity;
    /**
     * extraShelfKey represents in each moment the key where a new extra shelf will be added
     */
    private int extraShelfKey;

    /**
     * This is the constructor of this class.
     */
    public Warehouse(String file) {
        defaultShelf = new HashMap<>();
        defaultCapacity = new HashMap<>();
        extraShelf = new HashMap<>();
        extraCapacity = new HashMap<>();

        LinkedList<Integer> capacity = ConfigurationParser.getCapacityWarehouse(file);
        for(int i=0; i<capacity.size(); i++){
            defaultCapacity.put(i+1, capacity.get(i));
        }
        extraShelfKey = defaultCapacity.size() + 1;
    }

    /**
     * This method gets the resource present in the shelf with number 'shelf'.
     * @param shelf the number of the shelf
     * @return Resource present in the shelf with number 'shelf'
     * @throws IllegalShelfException if: the shelf does not exists, it has not a resource.
     */
    public Resource getResource(int shelf) throws IllegalShelfException{

        if(shelf > 0 && shelf <= defaultCapacity.size() && !defaultShelf.containsKey(shelf))
            throw new IllegalShelfException("Empty shelf!");

        if(defaultShelf.containsKey(shelf))
            return defaultShelf.get(shelf).getResource();

        if(!extraShelf.containsKey(shelf))
            throw new IllegalShelfException("This shelf does not exist!");

        return extraShelf.get(shelf).getResource();
    }

    /**
     * This method gets the quantity of resources present in the shelf with number 'shelf'.
     * @param shelf the number of the shelf
     * @return quantity of resources present in the shelf with number 'shelf'
     * @throws IllegalShelfException if: the shelf does not exists, it has not a resource.
     */
    public int getQuantity(int shelf) throws IllegalShelfException{

        if(shelf > 0 && shelf <= defaultCapacity.size() && !defaultShelf.containsKey(shelf))
            throw new IllegalShelfException("Empty shelf!");

        if(defaultShelf.containsKey(shelf))
            return defaultShelf.get(shelf).getQuantity();

        if(!extraShelf.containsKey(shelf))
            throw new IllegalShelfException("This shelf does not exist!");

        return extraShelf.get(shelf).getQuantity();
    }

    /**
     * This method gets the capacity of the shelf with number 'shelf'.
     * @param shelf the number of the shelf
     * @return capacity of the shelf with number 'shelf'
     * @throws IllegalShelfException if the shelf does not exists
     */
    public int getCapacity(int shelf) throws IllegalShelfException{

        if(defaultCapacity.containsKey(shelf))
            return defaultCapacity.get(shelf);

        if(extraCapacity.containsKey(shelf))
            return extraCapacity.get(shelf);

        throw new IllegalShelfException("This shelf does not exist!");
    }

    /**
     * This method subtracts one unit from the quantity of resources present in the shelf with number 'shelf'.
     * @param shelf the number of the shelf
     * @throws IllegalShelfException if: the shelf does not exists, it has not a resource, it is empty.
     */
    public boolean subtract(int shelf) throws IllegalShelfException{

        if(shelf > 0 && shelf <= defaultCapacity.size() && !defaultShelf.containsKey(shelf))
            throw new IllegalShelfException("Empty Shelf!");

        if(defaultShelf.containsKey(shelf)) {
            if (defaultShelf.get(shelf).getQuantity() == 1) {
                defaultShelf.remove(shelf);
                return true;
            }
            defaultShelf.put(shelf, new ResQuantity(defaultShelf.get(shelf).getResource(), defaultShelf.get(shelf).getQuantity() - 1));

            return true;
        }

        if (!extraShelf.containsKey(shelf))
            throw new IllegalShelfException("This shelf does not exist!");

        if(extraShelf.get(shelf).getQuantity() <= 0)
            throw new IllegalShelfException("Empty extra shelf!");

        extraShelf.put(shelf, new ResQuantity(extraShelf.get(shelf).getResource(), extraShelf.get(shelf).getQuantity() - 1));

        return true;
    }

    /**
     * This method adds one unit to the quantity of resources present in the shelf with number 'shelf'.
     * @param shelf the number of the shelf
     * @param resource the resource you want to add
     * @throws IllegalShelfException if: the shelf does not exists, already exists a default shelf with this resource,
     *                               the resource present int the shelf with number 'shelf' is different from the resource you want to add,
     *                               the shelf is full.
     */
    public void addResource(int shelf, Resource resource) throws IllegalShelfException{

        if(shelf <= 0)
            throw new IllegalShelfException("This shelf does not exist!");

        if(shelf <= defaultCapacity.size()) {
            try{addResourceDefault(shelf,resource);}
            catch(IllegalShelfException e) { throw e;}
        }
        else{
            try{addResourceExtra(shelf, resource);}
            catch(IllegalShelfException e) { throw e;}
        }
    }

    /**
     * This method adds one unit to the quantity of resources present in the shelf with number 'shelf' in case of default shelf.
     * @param shelf the number of the shelf
     * @param resource the resource you want to add
     * @throws IllegalShelfException if: already exists a default shelf with this resource,
     *                               the resource present int the shelf with number 'shelf' is different from the resource you want to add,
     *                               the shelf is full.
     */
    private boolean addResourceDefault(int shelf, Resource resource) throws IllegalShelfException{

        if(!defaultShelf.containsKey(shelf)){

            if(defaultShelf.values().stream().anyMatch(resQuantity -> resQuantity.getResource().getColor().equals(resource.getColor())))
                throw new IllegalShelfException("Already exists a shelf with this resource!");

            defaultShelf.put(shelf, new ResQuantity(resource,1));
            return true;
        }

        if (!defaultShelf.get(shelf).getResource().getColor().equals(resource.getColor()))
            throw new IllegalShelfException("Wrong resource!"); // different resource

        if (defaultShelf.get(shelf).getQuantity() >= defaultCapacity.get(shelf))
            throw new IllegalShelfException("Full shelf!") ; // full shelf

        defaultShelf.put(shelf, new ResQuantity(defaultShelf.get(shelf).getResource(), defaultShelf.get(shelf).getQuantity() + 1));

        return true;
    }

    /**
     * This method adds one unit to the quantity of resources present in the shelf with number 'shelf' in case of extra shelf.
     * @param shelf the number of the shelf
     * @param resource the resource you want to add
     * @throws IllegalShelfException if: the resource present int the shelf with number 'shelf' is different from the resource you want to add, the shelf is full.
     */
    private boolean addResourceExtra(int shelf, Resource resource) throws IllegalShelfException{

        if(!extraShelf.containsKey(shelf))
            throw new IllegalShelfException("This shelf does not exist");

        if(!extraShelf.get(shelf).getResource().getColor().equals(resource.getColor()))
            throw new IllegalShelfException("Wrong resource!"); // different resource

        if(extraShelf.get(shelf).getQuantity() >= extraCapacity.get(shelf))
            throw new IllegalShelfException("Full shelf!");//full shelf

        extraShelf.put(shelf, new ResQuantity(extraShelf.get(shelf).getResource(), extraShelf.get(shelf).getQuantity() + 1));

        return true;
    }

    /**
     * This method swaps the the contents of two shelves.
     * @param source the number of the first shelf
     * @param target the number of the second shelf
     * @throws IllegalShelfException if the swap is not possible
     */
    public boolean swap(int source, int target) throws IllegalShelfException{

        if(source <= 0 || source > defaultCapacity.size() || target <= 0 || target > defaultCapacity.size())
            throw new IllegalShelfException("Illegal swap!");
        int sourceQuantity = defaultShelf.containsKey(source) ? defaultShelf.get(source).getQuantity() : 0;
        int targetQuantity = defaultShelf.containsKey(target) ? defaultShelf.get(target).getQuantity() : 0;
        int sourceCapacity = defaultCapacity.get(source);
        int targetCapacity = defaultCapacity.get(target);

        if(source == target)
            return true;

        if(sourceQuantity >= targetQuantity) {
            if(sourceQuantity > targetCapacity)
                throw new IllegalShelfException("Illegal swap!");
            safeSwap(source, target, sourceQuantity, targetQuantity);
            return true;
        }

        if(targetQuantity > sourceCapacity)
            throw new IllegalShelfException("Illegal swap!");

        safeSwap(target, source, targetQuantity, sourceQuantity);
        return true;
    }

    /**
     * This method swaps the the contents of two shelves assuming that the parameters are correct.
     * Requirements: sourceQuantity >= target quantity
     * @param source the number of the first shelf
     * @param target the number of the second shelf
     * @param sourceQuantity quantity of first shelf
     * @param targetQuantity quantity of second shelf
     */
    private boolean safeSwap(int source, int target, int sourceQuantity, int targetQuantity) {

        if(sourceQuantity == 0)//sourceQuantity == 0 => targetQuantity == 0, swap is valid but nothing happens
            return true;

        if(targetQuantity == 0){
            Resource sourceResource = defaultShelf.get(source).getResource();
            defaultShelf.put(target, new ResQuantity(sourceResource,sourceQuantity));
            defaultShelf.remove(source);
            return true;
        }

        Resource sourceResource = defaultShelf.get(source).getResource();
        Resource targetResource = defaultShelf.get(target).getResource();
        defaultShelf.put(source, new ResQuantity(targetResource,targetQuantity));
        defaultShelf.put(target, new ResQuantity(sourceResource,sourceQuantity));
        return true;
    }

    /**
     * This method adds a new extra shelf and its quantity is set to zero.
     * @param resQuantity the resource of ResQuantity represents the resource of the new shelf,
     *                    quantity of ResQuantity represents the capacity of the new shelf, DO NOT SET A NEGATIVE CAPACITY
     */
    public void addShelf(ResQuantity resQuantity){

        extraShelf.put(extraShelfKey, new ResQuantity(resQuantity.getResource(),0));
        extraCapacity.put(extraShelfKey, resQuantity.getQuantity());
        extraShelfKey++;
    }

    /**
     * This method calculates the number of resources remaining in the strongbox
     * @return the number of resources remaining in the strongbox
     */
    public int calculateTotalResources(){

        int i = defaultShelf.values().stream().mapToInt(ResQuantity::getQuantity).sum();
        int j = extraShelf.values().stream().mapToInt(ResQuantity::getQuantity).sum();

        return i + j;
    }


    /**
     * This method creates a map with key Resource and value Integer which contains a summary of all the resources present in the warehouse.
     * @return a Map with key Resource and value Integer
     */
    public Map<Resource,Integer> getAllResources(){

        Map<Resource,Integer> map = new HashMap<>();

        for(ResQuantity resQuantity : defaultShelf.values()){
            map.put(resQuantity.getResource(),resQuantity.getQuantity());
        }

        for(ResQuantity resQuantity : extraShelf.values()){
            if(map.containsKey(resQuantity.getResource())){
                map.put(resQuantity.getResource(), map.get(resQuantity.getResource()) + resQuantity.getQuantity());}
            else{map.put(resQuantity.getResource(), resQuantity.getQuantity());}
        }

        return map;
    }
}