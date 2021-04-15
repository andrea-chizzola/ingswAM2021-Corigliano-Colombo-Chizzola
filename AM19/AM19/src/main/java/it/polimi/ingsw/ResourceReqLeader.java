package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * this class represents the resource requirements of a LeaderCard
 */
public class ResourceReqLeader implements Requirements{
    private LinkedList<ResQuantity> requirements;

    /**
     *
     * @param requirements is the list of requirements of the DevelopmentCard
     */
    public ResourceReqLeader(LinkedList<ResQuantity> requirements){
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
    public boolean checkReq(Board board) throws InvalidActionException, ResourcesExpectedException {
        throw new ResourcesExpectedException("The card needs the status of the StrongBox and the Warehouse!");
    }

    /**
     * This method checks if the player has enough resources to activate the Leader Card.
     * An InvalidActionException is thrown if the resources are not enough.
     * @param board represents the board associated to a player
     * @param shelves not used
     * @param quantity not used
     * @param strongbox not used
     * @return true if the check is successful
     * @throws InvalidActionException
     */

    public boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException{

        Map<Resource,Integer> resourceStatus = board.getResourceStatus();
        try {
            for (ResQuantity resQuantity : requirements)
                resQuantity.checkLeader(resourceStatus);
        }
        catch (InvalidActionException e){throw e;}

        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceReqLeader that = (ResourceReqLeader) o;
        return Objects.equals(requirements, that.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirements);
    }
}
