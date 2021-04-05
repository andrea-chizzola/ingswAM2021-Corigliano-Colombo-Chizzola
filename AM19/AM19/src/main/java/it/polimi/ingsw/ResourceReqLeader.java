package it.polimi.ingsw;

import java.util.LinkedList;
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
    @Override
    public boolean checkReq(Board board) {
        return false;
    }

    @Override
    public void buyCard(Board board) {

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
