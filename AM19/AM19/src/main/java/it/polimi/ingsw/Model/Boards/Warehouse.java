package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.*;

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
    public void subtract(int shelf) throws IllegalShelfException{

        if(shelf > 0 && shelf <= defaultCapacity.size() && !defaultShelf.containsKey(shelf))
            throw new IllegalShelfException("Empty Shelf!");

        if(defaultShelf.containsKey(shelf)) {
            if (defaultShelf.get(shelf).getQuantity() == 1) {
                defaultShelf.remove(shelf);
                return;
            }
            defaultShelf.put(shelf, new ResQuantity(defaultShelf.get(shelf).getResource(), defaultShelf.get(shelf).getQuantity() - 1));

            return;
        }

        if (!extraShelf.containsKey(shelf))
            throw new IllegalShelfException("This shelf does not exist!");

        if(extraShelf.get(shelf).getQuantity() <= 0)
            throw new IllegalShelfException("Empty extra shelf!");

        extraShelf.put(shelf, new ResQuantity(extraShelf.get(shelf).getResource(), extraShelf.get(shelf).getQuantity() - 1));
    }


    /**
     * This method adds one unit to the quantity of resources present in the shelf with number 'shelf'.
     * Before calling this method it is important to call the method checkInsertMultipleRes
     * @param shelf the number of the shelf
     * @param resource the resource you want to add
     */
    public void insertResource(int shelf, Resource resource){
        if(defaultCapacity.containsKey(shelf))
            insertResourceDefault(shelf,resource);
        if(extraShelf.containsKey(shelf))
            insertResourceExtra(shelf,resource);
    }

    /**
     * This method adds one unit to the quantity of resources present in the shelf with number 'shelf' in case of default shelf.
     * @param shelf the number of the shelf
     * @param resource the resource you want to add
     */
    private void insertResourceDefault(int shelf, Resource resource){
        if(!defaultShelf.containsKey(shelf))
            defaultShelf.put(shelf,new ResQuantity(resource,1));
        else
            defaultShelf.put(shelf, new ResQuantity(resource, defaultShelf.get(shelf).getQuantity() + 1));
    }

    /**
     * This method adds one unit to the quantity of resources present in the shelf with number 'shelf' in case of extra shelf.
     * @param shelf the number of the shelf
     * @param resource the resource you want to add
     */
    private void insertResourceExtra(int shelf, Resource resource){

        extraShelf.put(shelf, new ResQuantity(resource, extraShelf.get(shelf).getQuantity() + 1));
    }

    /**
     * This method allows to insert multiple resources in the selected shelves
     * @param resources List of Resources which represents all the resources to be inserted
     * @param shelves List of Integer which represents for each resource the shelf where it has to be inserted
     * @throws IllegalShelfException if it is not possible to insert the selected resources
     */
    public void insertMultipleResources(List<Resource> resources, List<Integer> shelves) throws IllegalShelfException{

        if(!checkInsertMultipleRes(resources, shelves))
            throw new IllegalShelfException("Wrong selected resources!");
        if (resources.stream().anyMatch(resource -> resource.isEmpty()))
            throw new IllegalShelfException("Wrong selected resources!");

        for (int i = 0; i < resources.size(); i++) {
            insertResource(shelves.get(i), resources.get(i));
        }
    }

    /**
     * This method checks if the resources passed ad parameters can be inserted in the Warehouse
     * @param resources List of resources which indicates all the resources that should be inserted
     * @param shelves List of integers which represents for each resource the shelf in which the resource should be inserted
     * @return true if the resources can be inserted, false otherwise
     */
    public boolean checkInsertMultipleRes(List<Resource> resources, List<Integer> shelves){

        HashMap<Integer,ResQuantity> map = new HashMap<>();
        HashMap<Integer,ResQuantity> mapDefault = new HashMap<>();
        HashMap<Integer,ResQuantity> mapExtra = new HashMap<>();

        for(int i=0; i< resources.size(); i++){
            int j = shelves.get(i);

            if(resources.get(i).isEmpty())
                continue;
            if(!extraShelf.containsKey(j) && !defaultCapacity.containsKey(j))
                return false;
            //if there are two different resources in one shelf
            if(map.containsKey(j) && !map.get(j).getResource().isSameResource(resources.get(i)))
                return false;

            int quantity = map.containsKey(j) ? (map.get(j).getQuantity()+1) : 1;
            map.put(j, new ResQuantity(resources.get(i), quantity));

            if(defaultCapacity.containsKey(j))
                mapDefault.put(j, map.get(j));
            else
                mapExtra.put(j, map.get(j));
        }

        return (checkInsertDefault(mapDefault) && checkInsertExtra(mapExtra));
    }

    /**
     * This method checks if the resources passed ad parameters can be inserted in the default shelves of the warehouse
     * @param mapDefault Map(Integer-ResQuantity) which represents the shelf, the resource and the quantity of resources
     * @return true if it is possible to insert the selected resources, false otherwise
     */
    private boolean checkInsertDefault(HashMap<Integer,ResQuantity> mapDefault){

        Set<Resource> set = new HashSet<>();
        for(ResQuantity resQuantity : mapDefault.values()){
            //if there a resource has to be inserted in two different shelves
            if(set.contains(resQuantity.getResource()))
                return false;
            set.add(resQuantity.getResource());
        }

        for(int i : mapDefault.keySet()){
            if(!defaultShelf.containsKey(i)){
                if(defaultShelf.values().stream().anyMatch(resQuantity -> resQuantity.getResource().isSameResource(mapDefault.get(i).getResource())))
                    return false;
                if(defaultCapacity.get(i) < mapDefault.get(i).getQuantity())
                    return false;
            }
            else {
                if (!defaultShelf.get(i).getResource().isSameResource(mapDefault.get(i).getResource()))
                    return false;
                if (defaultCapacity.get(i) < defaultShelf.get(i).getQuantity() + mapDefault.get(i).getQuantity())
                    return false;
            }
        }
        return true;
    }

    /**
     * This method checks if the resources passed ad parameters can be inserted in the extra shelves of the warehouse
     * @param mapExtra Map(Integer-ResQuantity) which represents the shelf, the resource and the quantity of resources
     * @return true if it is possible to insert the selected resources, false otherwise
     */
    private boolean checkInsertExtra(HashMap<Integer,ResQuantity> mapExtra){

        for(int i : mapExtra.keySet()){
            if(!extraShelf.get(i).getResource().isSameResource(mapExtra.get(i).getResource()))
                return false;
            if(extraCapacity.get(i) < extraShelf.get(i).getQuantity() + mapExtra.get(i).getQuantity())
                return false;
        }
        return true;
    }

    /**
     * This method swaps the the contents of two shelves.
     * @param source the number of the first shelf
     * @param target the number of the second shelf
     * @throws IllegalShelfException if the swap is not possible
     */
    public void swap(int source, int target) throws IllegalShelfException{

        if(source <= 0 || source > defaultCapacity.size() || target <= 0 || target > defaultCapacity.size())
            throw new IllegalShelfException("Illegal swap!");
        int sourceQuantity = defaultShelf.containsKey(source) ? defaultShelf.get(source).getQuantity() : 0;
        int targetQuantity = defaultShelf.containsKey(target) ? defaultShelf.get(target).getQuantity() : 0;
        int sourceCapacity = defaultCapacity.get(source);
        int targetCapacity = defaultCapacity.get(target);

        if(source == target)
            return;

        if(sourceQuantity >= targetQuantity) {
            if(sourceQuantity > targetCapacity)
                throw new IllegalShelfException("Illegal swap!");
            safeSwap(source, target, sourceQuantity, targetQuantity);
            return;
        }

        if(targetQuantity > sourceCapacity)
            throw new IllegalShelfException("Illegal swap!");

        safeSwap(target, source, targetQuantity, sourceQuantity);
    }

    /**
     * This method swaps the the contents of two shelves assuming that the parameters are correct.
     * Requirements: sourceQuantity >= target quantity
     * @param source the number of the first shelf
     * @param target the number of the second shelf
     * @param sourceQuantity quantity of first shelf
     * @param targetQuantity quantity of second shelf
     */
    private void safeSwap(int source, int target, int sourceQuantity, int targetQuantity) {

        if(sourceQuantity == 0)//sourceQuantity == 0 => targetQuantity == 0, swap is valid but nothing happens
            return;

        if(targetQuantity == 0){
            Resource sourceResource = defaultShelf.get(source).getResource();
            defaultShelf.put(target, new ResQuantity(sourceResource,sourceQuantity));
            defaultShelf.remove(source);
            return;
        }

        Resource sourceResource = defaultShelf.get(source).getResource();
        Resource targetResource = defaultShelf.get(target).getResource();
        defaultShelf.put(source, new ResQuantity(targetResource,targetQuantity));
        defaultShelf.put(target, new ResQuantity(sourceResource,sourceQuantity));
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

    /**
     * This method is useful to show the status of the warehouse
     * @return a List of ResQuantity which represents the status of the warehouse
     */
    public List<ResQuantity> showWarehouse(){

        List<ResQuantity> list = new LinkedList<>();
        ResQuantity resQuantity;
        ResQuantity defaultRes = new ResQuantity(new Coin(),0);

        for(int i : defaultCapacity.keySet()){
            resQuantity = defaultShelf.getOrDefault(i,defaultRes);
            list.add(resQuantity);
        }
        for(int i : extraShelf.keySet()){
            resQuantity = new ResQuantity(extraShelf.get(i).getResource(), extraShelf.get(i).getQuantity());
            list.add(resQuantity);
        }
        return list;
    }
}