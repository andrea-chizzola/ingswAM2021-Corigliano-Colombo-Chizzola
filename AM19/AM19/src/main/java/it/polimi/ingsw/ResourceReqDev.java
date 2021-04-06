package it.polimi.ingsw;

import java.util.LinkedList;
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
        ResourceReqDev that = (ResourceReqDev) o;
        return Objects.equals(requirements, that.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirements);
    }
}
