package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.ResourcesExpectedException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * public class that represents the requirements of a DevelopmentCard
 */

public class ResourceReqDev implements Requirements {
    /**
     * this attribute is the list of requirements of the DevelopmentCard
     */
    private LinkedList<ResQuantity> requirements;

    /**
     *
     * @param requirements is the list of requirements of the DevelopmentCard
     */
    public ResourceReqDev(LinkedList<ResQuantity> requirements){
        this.requirements = new LinkedList<>();
        this.requirements.addAll(requirements);
    }

    /**
     *
     * @param board is the board on which we are checking the requisites
     * @throws InvalidActionException if the requirements of the card are not satisfied by the player's board
     * @throws ResourcesExpectedException if the card need more information to check the requirements. In this case,
     * ResourceReqDev will throw this exception because it needs the current state of the player's StrongBox and
     * Warehouse
     */
    @Override
    public boolean checkReq(Board board) throws InvalidActionException, ResourcesExpectedException{
        throw new ResourcesExpectedException("The card needs the status of the StrongBox and the Warehouse!");
    }

    /**
     * This method checks if the resources selected from the warehouse and from the strongBox meet the purchase requirements.
     * @param board represents the board associated to a player
     * @param shelves ArrayList of Integer which represents all the shelves selected
     * @param quantity ArrayList of Integer which represents the amount of resources selected for each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources selected from the strongBox
     *                  It is important that each resource is present at most once
     * @return true if the check is successful
     * @throws InvalidActionException if the resources selected are not enough or if they are too many
     */
    public boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException{

        Map<Resource,Integer> resourceStatus;
        try {
            resourceStatus = ResQuantity.createReqMap(board,shelves,quantity,strongbox);

            for(ResQuantity resQuantity : requirements){
                resQuantity.checkDevelopment(resourceStatus, board);
            }
        }
        catch (InvalidActionException e){throw e;}

        //It checks if the player has selected more resources than required, it is important because all the resources selected will be subtracted from the deposits
        if(resourceStatus.values().stream().mapToInt(Integer::intValue).sum() != 0)
            throw new InvalidActionException("Too many resources selected!");

        return true;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceReqDev that = (ResourceReqDev) o;
        return Objects.equals(requirements, that.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirements);
    }
}
