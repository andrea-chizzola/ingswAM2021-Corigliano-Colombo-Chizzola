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
 * this class represents the resource requirements of a LeaderCard
 */
public class ResourceReqLeader implements Requirements{

    /**
     * this attribute is the list of requirements of the LeaderCard
     */
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
     * This method checks if the player has enough resources to activate the Leader Card.
     * @param board represents the board associated to a player
     * @throws InvalidActionException if the requirements of the card are not satisfied by the player's board
     */
    @Override
    public boolean checkReq(Board board) throws InvalidActionException {

        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<ResQuantity> resQuantities = new ArrayList<>();
        checkReq(board, arrayList, arrayList, resQuantities);

        return true;
    }

    /**
     * This method checks if the player has enough resources to activate the Leader Card.
     * @param board represents the board associated to a player
     * @param shelves not used
     * @param quantity not used
     * @param strongbox not used
     * @return true if the check is successful
     * @throws InvalidActionException if the requirements of the card are not satisfied by the player's board
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
